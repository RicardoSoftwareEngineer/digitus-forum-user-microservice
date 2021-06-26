package com.digitusforum.user.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
public class User {
	@Id
	private String userId;
	private String name;
	private String login;
	private String password;
	private String phone;
	private String email;
	private Integer readableId;
	private ZonedDateTime createdIn;
	private ZonedDateTime updatedIn;
	private String updatedByUserId;
	private boolean deleted;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getReadableId() {
		return readableId;
	}

	public void setReadableId(Integer readableId) {
		this.readableId = readableId;
	}

	public ZonedDateTime getCreatedIn() {
		return createdIn;
	}

	public void setCreatedIn(ZonedDateTime createdIn) {
		this.createdIn = createdIn;
	}

	public ZonedDateTime getUpdatedIn() {
		return updatedIn;
	}

	public void setUpdatedIn(ZonedDateTime updatedIn) {
		this.updatedIn = updatedIn;
	}

	public String getUpdatedByUserId() {
		return updatedByUserId;
	}

	public void setUpdatedByUserId(String updatedByUserId) {
		this.updatedByUserId = updatedByUserId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
