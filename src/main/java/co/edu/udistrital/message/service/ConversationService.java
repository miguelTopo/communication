package co.edu.udistrital.message.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import co.edu.udistrital.message.enums.ConversationBundle;
import co.edu.udistrital.message.model.Conversation;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.repository.ConversationRepository;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.service.UserService;

@Service
public class ConversationService {

	private final ConversationRepository conversationRepository;
	private final ResponseService responseService;
	private final UserService userService;

	@Autowired
	public ConversationService(@Lazy ConversationRepository conversationRepository, @Lazy ResponseService responseService, UserService userService) {
		this.conversationRepository = conversationRepository;
		this.responseService = responseService;
		this.userService = userService;
	}

	private Conversation findByBasicConversation(String userId1, String userId2) {
		Conversation conversation = this.conversationRepository.findByBasicConversation(Arrays.asList(userId1, userId2));
		if (conversation == null) {
			conversation = new Conversation();
			conversation.setUserIdList(Arrays.asList(userId1, userId2));
		}
		if (CollectionUtils.isEmpty(conversation.getMessageList()))
			conversation.setMessageList(new ArrayList<>(1));
		return conversation;
	}

	public void sendMessage(Message message) {
		try {
			Conversation conversation = findByBasicConversation(message.getSenderUser().getId(), message.getReceiverUser().getId());
			conversation.getMessageList().add(message);
			this.conversationRepository.save(conversation);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Response loadUserToUser(String userId, String contactUserId) {
		Conversation conversation = findByBasicConversation(userId, contactUserId);
		List<User> userList = this.userService.findByIdIn(Arrays.asList(userId, contactUserId));
		if (!CollectionUtils.isEmpty(userList) && userList.size() >= 2) {
			conversation.setUserList(new ArrayList<>(2));
			conversation.getUserList().add(userList.stream().filter(u -> u.getId().equals(userId)).findFirst().orElse(new User()));
			conversation.getUserList().add(userList.stream().filter(u -> u.getId().equals(contactUserId)).findFirst().orElse(new User()));
		}
		return this.responseService.successResponse(ConversationBundle.LOAD, ConversationBundle.SUCCESS_PTP_LOAD, conversation);
	}

	public List<Conversation> findByHomeUserId(String homeUserId) {
		return conversationRepository.findByHomeUserId(homeUserId);
	}

	public List<Conversation> findLastMessageList(String userId) {
		if (StringUtils.isEmpty(userId))
			return Collections.emptyList();
		List<Conversation> cList = this.conversationRepository.findLastMessageList(userId);
		return cList;
	}

	public void saveAll(List<Conversation> conversationList) {
		if (CollectionUtils.isEmpty(conversationList))
			return;
		this.conversationRepository.saveAll(conversationList);
	}
}
