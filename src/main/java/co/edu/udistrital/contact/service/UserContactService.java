package co.edu.udistrital.contact.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;
import co.edu.udistrital.core.util.CoreConst;
import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.message.model.Conversation;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.service.ConversationService;
import co.edu.udistrital.rest.contact.model.UserContactRest;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.user.service.UserService;

@Service
public class UserContactService {

	private UserContactRepository userContactRepository;
	private UserService userService;
	private ConversationService conversationService;

	@Autowired
	public UserContactService(@Lazy UserContactRepository userContactRepository, @Lazy UserService userService,
		@Lazy ConversationService conversationService) {
		this.userContactRepository = userContactRepository;
		this.userService = userService;
		this.conversationService = conversationService;
	}


	public List<UserContact> findAll() {
		List<UserContact> test = this.userContactRepository.findAll();
		return test;
	}

	@Transactional
	public boolean saveAll(List<UserContact> users) {
		if (users == null || users.isEmpty())
			return false;
		this.userContactRepository.saveAll(users);
		return true;
	}

	@Transactional
	public boolean save(UserContact userContact) {
		if (userContact == null)
			return false;
		this.userContactRepository.save(userContact);
		return true;
	}

	private List<String> getMissingMobilePhoneByContactList(String userPhone, List<String> currentMobileList, List<UserContact> userContactList) {
		if (CollectionUtils.isEmpty(userContactList))
			return Collections.emptyList();
		List<String> missingMobileList = new ArrayList<>(1);
		for (UserContact uc : userContactList) {
			if (uc.getUser() != null && !currentMobileList.contains(uc.getUser().getMobilePhone())
				&& !userPhone.equals(uc.getUser().getMobilePhone()))
				missingMobileList.add(uc.getUser().getMobilePhone());
		}
		return missingMobileList;
	}

	public List<UserContactRest> findContactListByUser(User user) {
		// Buscando usuario que solicita listado de cotactos
		User userResponse = this.userService.findByMobilePhoneActive(user.getMobilePhone());
		user.setId(userResponse.getId());

		List<String> currentContactMobileList = null;
		// Se asigna a la variable currentContactMobileList el listado de usuarios que ya pertenecen
		// a la libreta de contactos. De lo contrario continúa en null
		if (!CollectionUtils.isEmpty(userResponse.getUserContactIdList())) {
			userResponse.setUserContactList(findContactListByUserContact(userResponse.getId(), userResponse.getUserContactIdList(), false));
			currentContactMobileList =
				userResponse.getUserContactList().stream().map(UserContact::getUser).map(User::getMobilePhone).collect(Collectors.toList());
		}

		// Se asigna a la variable missingContactList los números telefónicos que estando en la base
		// de datos, no se encuentran registrados para el usuario que solicita el listado de
		// contactos. Esto permite que el usuario siempre tenga actualizados los contactos, que se
		// registren día a día
		List<String> missingContactList = null;
		final List<String> currentMobileList = currentContactMobileList;
		if (CollectionUtils.isEmpty(currentMobileList))
			missingContactList = user.getUserContactList().stream().map(UserContact::getUser).map(User::getMobilePhone)
				.filter(phone -> !phone.equals(userResponse.getMobilePhone())).collect(Collectors.toList());
		else
			missingContactList = getMissingMobilePhoneByContactList(userResponse.getMobilePhone(), currentMobileList, user.getUserContactList());

		// En la variable userToAdd se almacenan los registros de usuarios que existen en la base de
		// datos, pero no existe en la libreta de contactos del usuario, para asociaros, mediante la
		// inserción de objetos tipo userContact
		List<User> userToAdd =
			!CollectionUtils.isEmpty(missingContactList) ? userService.loadByMobilePhoneActiveState(missingContactList) : Collections.emptyList();
		if (!CollectionUtils.isEmpty(userToAdd)) {
			List<UserContact> userContactToAdd = buildUserContactFromUser(userToAdd, user);
			saveAll(userContactToAdd);
			if (CollectionUtils.isEmpty(userResponse.getUserContactIdList()))
				userResponse.setUserContactIdList(new ArrayList<>(userContactToAdd.size()));
			userResponse.getUserContactIdList().addAll(userContactToAdd.stream().map(UserContact::getId).collect(Collectors.toList()));
			userService.save(userResponse);
		}
		return parseToUserContactRest(findContactListByUserContact(userResponse.getId(), userResponse.getUserContactIdList(), true));
	}

	private List<UserContactRest> parseToUserContactRest(List<UserContact> list) {
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyList();
		List<UserContactRest> responseList = new ArrayList<>(list.size());
		list.forEach(uc -> responseList.add(uc.getUserContactRest()));
		return responseList;
	}

	/**
	 * Busca los objetos o entidades de tipo UserContact con toda la información correspondiente a
	 * los contactos del usuario. Dicha búsqueda se realiza por medio de un listado de id's
	 */
	private List<UserContact> findContactListByUserContact(String userId, List<String> userContactIdList, boolean loadLastMessage) {
		List<UserContact> userContactList = userContactRepository.findByIdIn(userContactIdList);
		if (CollectionUtils.isEmpty(userContactList))
			return Collections.emptyList();
		addUserAndLastMessage(userId, userContactList, loadLastMessage);
		return userContactList;
	}

	private void addUserAndLastMessage(String userId, List<UserContact> userContactList, boolean loadLastMessage) {
		List<User> userList = userService.findByIdIn(userContactList.stream().map(UserContact::getUserContactId).collect(Collectors.toList()));
		List<Conversation> conversationList = loadLastMessage ? this.conversationService.findLastMessageList(userId) : Collections.emptyList();
		User contact = null;
		Conversation conversation = null;
		Message message = null;
		for (UserContact uc : userContactList) {
			contact = userList.stream().filter(u -> u.getId().equals(uc.getUserContactId())).findFirst().orElse(new User());
			uc.setUser(contact);
			if (loadLastMessage && !StringUtils.isEmpty(contact.getId()) && !userId.equals(contact.getId())) {
				final String id = contact.getId();
				conversation = conversationList.stream().filter(c -> c.getUserIdList().contains(id)).findFirst().orElse(null);
				if (conversation != null) {
					message = CollectionUtils.isEmpty(conversation.getMessageList()) ? new Message()
						: conversation.getMessageList().get(conversation.getMessageList().size() - 1);
					uc.setLastMessage(message.getMessageBody());
					uc.setLastMessageHour(DateUtil.datedDate(message.getCreationDate()));
				}
			}
		}
	}



	private List<UserContact> buildUserContactFromUser(List<User> userList, User user) {
		if (CollectionUtils.isEmpty(userList))
			return Collections.emptyList();
		List<UserContact> customizeContactList =
			CollectionUtils.isEmpty(user.getUserContactList()) ? Collections.emptyList() : user.getUserContactList();
		List<UserContact> userContactList = new ArrayList<>(userList.size());
		UserContact uc = null;
		Optional<UserContact> optionalUserContact = null;
		for (User u : userList) {
			uc = new UserContact();
			uc.setUserId(user.getId());
			uc.setUserContactId(u.getId());
			optionalUserContact =
				customizeContactList.stream().filter(contact -> contact.getUser().getMobilePhone().equalsIgnoreCase(u.getMobilePhone())).findFirst();
			uc.setCustomName(optionalUserContact.isPresent() ? optionalUserContact.get().getCustomName() : "");
			userContactList.add(uc);
		}
		return userContactList;
	}

}
