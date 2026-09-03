package com.digitusforum.user.emailVerification;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EmailVerificationRepository extends CrudRepository<EmailVerificationEntity, String> {
	Optional<EmailVerificationEntity> findByEmailVerificationId(String resetPasswordId);

	Optional<EmailVerificationEntity> findByReadableNumber(Integer readableId);

	EmailVerificationEntity findByEmailAndReadableNumber(String userId, Integer readableId);

	EmailVerificationEntity findByEmail(String email);

	/**
	 * Bulk JPQL delete — returns 0 instead of throwing StaleStateException when the
	 * row was already removed (classic double-click / concurrent confirm race).
	 */
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("delete from EmailVerificationEntity e where e.emailVerificationId = :id")
	int deleteOneById(@Param("id") String id);
}
