package com.digitusforum.chat;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatSubjectRepository extends CrudRepository<ChatSubjectEntity, String> {
	ChatSubjectEntity findByChatSubjectIdAndDeletedIsFalse(String chatSujectId);
	List<ChatSubjectEntity> findByUserIdAndDeletedIsFalse(String chatSujectId);
    List<ChatSubjectEntity> findByUserIdAndDeletedIsTrue(String chatSujectId);
    void deleteById(String userId);
}
