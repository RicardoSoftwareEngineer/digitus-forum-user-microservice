package com.digitusforum.subscription;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, String> {
	List<SubscriptionEntity> findByUserIdAndDeletedIsFalse(String userId);
	Optional<SubscriptionEntity> findByUserIdAndGuruIdAndDeletedIsFalse(String userId, String guruId);
}
