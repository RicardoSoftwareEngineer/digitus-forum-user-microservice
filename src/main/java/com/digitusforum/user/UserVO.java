package com.digitusforum.user;

public class UserVO {
	private String id;
	private String userName;
	private String userType;
	private String email;
	private String password;
	private String tokenType;
	private String grantType;
	private String token;
	private boolean deleted;

	public UserVO() {
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	/*
	 * public UserVO(String userEmail, String userPassword) { this.email =
	 * userEmail; this.password = userPassword; }
	 * 
	 * public UserVO(int userId, String userName, String userEmail, String
	 * userPassword) { this.userId = userId; this.userName = userName; this.email =
	 * userEmail; this.password = userPassword; }
	 */

}
