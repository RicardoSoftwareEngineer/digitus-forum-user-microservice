package com.digitusforum.user.emailVerification;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface EmailVerificationRepository extends CrudRepository<EmailVerificationEntity, String> {
	Optional<EmailVerificationEntity> findByEmailVerificationId(String resetPasswordId);

	Optional<EmailVerificationEntity> findByReadableNumber(Integer readableId);

	EmailVerificationEntity findByEmailAndReadableNumber(String userId, Integer readableId);

	EmailVerificationEntity findByEmail(String email);
	
	void deleteById(String id);
}
