package com.digitusforum.user.emailVerification;

import java.time.ZonedDateTime;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "EmailVerification")
public class EmailVerificationEntity {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	private String emailVerificationId;
	private String email;
	private Integer readableNumber;
	private ZonedDateTime lastEmailSent;

	public EmailVerificationEntity() {
	}

	public EmailVerificationEntity(String email, EmailVerificationRepository emailVerificationRepository) {
		this.email = email;
		this.readableNumber = generateReadableNumber(email, emailVerificationRepository);
		this.lastEmailSent = ZonedDateTime.now();
	}

	private int generateReadableNumber(String userId, EmailVerificationRepository emailVerificationRepository) {
		int readableNumber = ThreadLocalRandom.current().nextInt(100000, 999999);
		while (emailVerificationRepository.findByReadableNumber(readableNumber).isPresent())
			readableNumber = ThreadLocalRandom.current().nextInt(100000, 999999);
		return readableNumber;
	}

	public ZonedDateTime getLastEmailSent() {
		return lastEmailSent;
	}

	public void setLastEmailSent(ZonedDateTime lastEmailSent) {
		this.lastEmailSent = lastEmailSent;
	}

	public String getEmailVerificationId() {
		return emailVerificationId;
	}

	public void setEmailVerificationId(String emailVerificationId) {
		this.emailVerificationId = emailVerificationId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getReadableNumber() {
		return readableNumber;
	}

	public void setReadableNumber(Integer readableNumber) {
		this.readableNumber = readableNumber;
	}

}
