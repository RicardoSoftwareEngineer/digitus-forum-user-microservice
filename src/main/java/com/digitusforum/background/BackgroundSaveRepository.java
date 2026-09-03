package com.digitusforum.background;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface BackgroundSaveRepository extends CrudRepository<BackgroundSaveEntity, String> {
	List<BackgroundSaveEntity> findByUserIdAndDeletedIsFalseOrderByCreatedInDesc(String userId);

	Optional<BackgroundSaveEntity> findByIdAndDeletedIsFalse(String id);

	Optional<BackgroundSaveEntity> findByIdAndUserIdAndDeletedIsFalse(String id, String userId);
}
