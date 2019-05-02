package co.edu.udistrital.message.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.message.enums.ConversationBundle;
import co.edu.udistrital.message.model.Conversation;
import co.edu.udistrital.message.service.ConversationService;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.service.ResponseService;

@RestController
@RequestMapping("/conversation")
public class ConversationController {

	private final ConversationService conversationService;
	private final ResponseService responseService;

	@Autowired
	public ConversationController(@Lazy ConversationService conversationService, @Lazy ResponseService responseService) {
		this.conversationService = conversationService;
		this.responseService = responseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/userToUser")
	public ResponseEntity<Response> loadUserToUser(@RequestParam("userId") String userId, @RequestParam("contactUserId") String contactUserId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(contactUserId))
			return ResponseEntity.ok().body(responseService.warnMessage(ConversationBundle.LOAD, ConversationBundle.USER_LIST_NO_FOUND));
		return ResponseEntity.ok().body(this.conversationService.loadUserToUser(userId, contactUserId));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/findLastMessageList")
	public List<Conversation> findLastMessageList(@RequestParam("userId") String userId) {
		if (StringUtils.isEmpty(userId))
			return Collections.emptyList();
		return this.conversationService.findLastMessageList(userId);
	}

}
