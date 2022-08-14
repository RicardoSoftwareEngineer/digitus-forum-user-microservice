package com.digitusforum.chat;

import java.util.ArrayList;
import java.util.List;

public class ConversationVO {
	private String subjectId;
	private List<ChatMessageVO> conversation = new ArrayList<>();

	public ConversationVO() {
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public List<ChatMessageVO> getConversation() {
		return conversation;
	}

	public void setConversation(List<ChatMessageVO> conversation) {
		this.conversation = conversation;
	}

}
