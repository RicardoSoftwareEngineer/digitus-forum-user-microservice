package com.digitusforum.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	@Autowired
	ChatService userService;

	@RequestMapping(value = "/user/v1/chat")
	public ConversationVO chat(@RequestBody ChatMessageVO user) {
		return userService.chat(user);
	}

	@RequestMapping(value = "/user/v1/conversations")
	public List<ChatSubjectVO> conversations(@RequestBody ChatMessageVO chatMessageVO) {
		return userService.getConversations(chatMessageVO);
	}

	@RequestMapping(value = "/user/v1/conversation")
	public ConversationVO conversation(@RequestBody ChatMessageVO chatMessageVO) {
		return userService.getConversation(chatMessageVO);
	}

}