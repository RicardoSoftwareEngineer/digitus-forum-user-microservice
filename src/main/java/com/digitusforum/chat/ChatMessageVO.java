package com.digitusforum.chat;

public class ChatMessageVO {
	private String chatSubjectId;
	private String chatSubjectName;
	private String userId;
	private String userName;
	private String userType;
	private String userEmail;
	private String chatMessageId;
	private String name;
	private String message;
	private String alignment;
	private int position;
	private int from;
	private int to;

	public ChatMessageVO() {
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getAlignment() {
		return alignment;
	}

	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getChatSubjectName() {
		return chatSubjectName;
	}

	public void setChatSubjectName(String chatSubjectName) {
		this.chatSubjectName = chatSubjectName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}




}
