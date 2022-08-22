package com.digitusforum.chat;

import java.time.ZonedDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.digitusforum.user.util.M;

@Service
public class ChatService {

	@Autowired
	ChatMessageRepository chatMessageRepository;

	@Autowired
	ChatSubjectRepository chatSubjectRepository;

	public ConversationVO chat(ChatMessageVO chatMessageVO) {
		if (StringUtils.isBlank(chatMessageVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_ID);
		if (StringUtils.isBlank(chatMessageVO.getUserEmail()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_EMAIL);
		if (StringUtils.isBlank(chatMessageVO.getUserType()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_TYPE);
		if (StringUtils.isBlank(chatMessageVO.getMessage()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_MESSAGE);
		if (StringUtils.isBlank(chatMessageVO.getUserName()))
			chatMessageVO.setUserName(chatMessageVO.getUserEmail().substring(0, 7));

		ChatSubjectEntity subject = null;
		if (StringUtils.isBlank(chatMessageVO.getChatSubjectId())) {
			if (StringUtils.isBlank(chatMessageVO.getName()))
				chatMessageVO.setName("---");

			subject = new ModelMapper().map(chatMessageVO, ChatSubjectEntity.class);
			subject.setLastUpdated(ZonedDateTime.now());
			subject = chatSubjectRepository.save(subject);
		} else {
			subject = chatSubjectRepository.findByChatSubjectIdAndDeletedIsFalse(chatMessageVO.getChatSubjectId());
			if (subject == null)
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_SUBJECT_NOT_FOUND);
		}
		chatMessageVO.setChatSubjectId(subject.getChatSubjectId());
		ChatMessageEntity chat = new ChatMessageEntity();
		chat.setMessage(chatMessageVO.getMessage());
		chat.setUserId(chatMessageVO.getUserId());
		chat.setUserName(chatMessageVO.getUserName());
		chat.setChatSubjectId(chatMessageVO.getChatSubjectId());
		chat.setUserEmail(chatMessageVO.getUserEmail());
		chat.setUserType(chatMessageVO.getUserType());
		List<ChatMessageEntity> conversation = chatMessageRepository
				.findByChatSubjectIdOrderByPosition(chatMessageVO.getChatSubjectId());
		chat.setPosition(conversation.size());
		chat = chatMessageRepository.save(chat);
		// i know this is not performati but i will keep here to wait until get enough
		// messages to become slow
		// and test how much the database index speed it up //TODO
		return getConversation(chatMessageVO.getChatSubjectId());
	}

	public ConversationVO getConversation(ChatMessageVO chatMessageVO) {
		if (StringUtils.isBlank(chatMessageVO.getChatSubjectId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_SUBJECT_ID);

		return getConversation(chatMessageVO.getChatSubjectId());
	}

	public ConversationVO getConversation(String subjectId) {
		ChatSubjectEntity subject = chatSubjectRepository.findByChatSubjectIdAndDeletedIsFalse(subjectId);
		if (subject == null)
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_SUBJECT_NOT_FOUND);

		List<ChatMessageEntity> chats = chatMessageRepository.findByChatSubjectIdOrderByPosition(subjectId);
		List<ChatMessageVO> chatsVO = new ModelMapper().map(chats, List.class);

		ConversationVO conversation = new ConversationVO();
		conversation.setSubjectId(subject.getChatSubjectId());
		conversation.setConversation(chatsVO);
		conversation.setSubjectName(subject.getName());
		return conversation;
	}

	public List<ChatSubjectVO> getConversations(ChatMessageVO chatMessageVO) {
		if (StringUtils.isBlank(chatMessageVO.getUserId()))
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, M.CHAT_MISSING_USER_ID);
		List<ChatSubjectEntity> conversationsEntity = chatSubjectRepository
				.findByUserIdAndDeletedIsFalse(chatMessageVO.getUserId());
		List<ChatSubjectVO> conversationsVO = new ModelMapper().map(conversationsEntity, List.class);
		return conversationsVO;
	}

}
