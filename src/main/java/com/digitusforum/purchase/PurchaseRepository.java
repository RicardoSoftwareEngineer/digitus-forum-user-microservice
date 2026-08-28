package com.digitusforum.purchase;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<PurchaseEntity, String> {
	List<PurchaseEntity> findByUserIdAndDeletedIsFalse(String userId);
	Optional<PurchaseEntity> findByUserIdAndTrainingIdAndDeletedIsFalse(String userId, String trainingId);
	Optional<PurchaseEntity> findByUserIdAndTrainingIdAndStatusAndDeletedIsFalse(String userId, String trainingId,
			String status);
}
