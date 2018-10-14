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

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.user.service.UserService;

@Service
public class UserContactService {

	private UserContactRepository userContactRepository;
	private UserService userService;

	@Autowired
	public UserContactService(@Lazy UserContactRepository userContactRepository, @Lazy UserService userService) {
		this.userContactRepository = userContactRepository;
		this.userService = userService;
	}

	// public List<UserContact> findByUserId(String userContactId) {
	// List<UserContact> test =this.userContactRepository.findByUserId(userContactId);
	// return test ;
	// }

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

	public List<UserContact> findContactListByUser(User user) {
		User userResponse = this.userService.findByMobilePhoneActive(user.getMobilePhone());
		List<String> currentContactMobileList = null;
		if (!CollectionUtils.isEmpty(userResponse.getUserContactIdList())) {
			userResponse.setUserContactList(findContactListByUserContact(userResponse.getUserContactIdList()));
			currentContactMobileList =
				userResponse.getUserContactList().stream().map(UserContact::getUser).map(User::getMobilePhone).collect(Collectors.toList());
		}

		List<String> missingContactList = null;
		final List<String> currentMobileList = currentContactMobileList;
		if (CollectionUtils.isEmpty(currentMobileList))
			missingContactList = user.getUserContactList().stream().map(UserContact::getUser).map(User::getMobilePhone).collect(Collectors.toList());
		else
			missingContactList =
				user.getUserContactList().stream().filter(uc -> uc.getUser() != null && !currentMobileList.contains(uc.getUser().getMobilePhone()))
					.map(UserContact::getUser).map(User::getMobilePhone).collect(Collectors.toList());

		List<User> userToAdd =
			!CollectionUtils.isEmpty(missingContactList) ? userService.loadByMobilePhoneActiveState(missingContactList) : Collections.emptyList();
		if (!CollectionUtils.isEmpty(userToAdd)) {
			List<UserContact> userContactToAdd = buildUserContactFromUser(userToAdd, user.getUserContactList());
			saveAll(userContactToAdd);
			if (CollectionUtils.isEmpty(userResponse.getUserContactIdList()))
				userResponse.setUserContactIdList(new ArrayList<>(userContactToAdd.size()));
			userResponse.getUserContactIdList().addAll(userContactToAdd.stream().map(UserContact::getId).collect(Collectors.toList()));
			userService.save(userResponse);
		}
		return findContactListByUserContact(userResponse.getUserContactIdList());
	}

	private List<UserContact> findContactListByUserContact(List<String> userContactIdList) {
		List<UserContact> userContactList = userContactRepository.findByIdIn(userContactIdList);
		if (CollectionUtils.isEmpty(userContactList))
			return Collections.emptyList();
		List<User> userList = userService.findByIdIn(userContactList.stream().map(UserContact::getUserId).collect(Collectors.toList()));
		userContactList.forEach(uc -> uc.setUser(userList.stream().filter(u -> u.getId().equals(uc.getUserId())).findFirst().orElse(new User())));
		return userContactList;
	}

	private List<UserContact> buildUserContactFromUser(List<User> userList, List<UserContact> customizeContactList) {
		if (CollectionUtils.isEmpty(userList))
			return Collections.emptyList();
		List<UserContact> userContactList = new ArrayList<>(userList.size());
		UserContact uc = null;
		Optional<UserContact> optionalUserContact = null;
		for (User u : userList) {
			uc = new UserContact();
			uc.setUserId(u.getId());
			optionalUserContact =
				customizeContactList.stream().filter(contact -> contact.getUser().getMobilePhone().equalsIgnoreCase(u.getMobilePhone())).findFirst();
			uc.setCustomName(optionalUserContact.isPresent() ? optionalUserContact.get().getCustomName() : "");
			userContactList.add(uc);
		}
		return userContactList;
	}

}
