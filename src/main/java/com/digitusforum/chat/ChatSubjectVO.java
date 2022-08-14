package com.digitusforum.chat;

public class ChatSubjectVO {
	private String chatSubjectId;
	private String chatMessageId;
	private String name;
	private String message;
	private String status;

	public ChatSubjectVO() {
	}

	public String getChatSubjectId() {
		return chatSubjectId;
	}

	public void setChatSubjectId(String chatSubjectId) {
		this.chatSubjectId = chatSubjectId;
	}

	public String getChatMessageId() {
		return chatMessageId;
	}

	public void setChatMessageId(String chatMessageId) {
		this.chatMessageId = chatMessageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



}
