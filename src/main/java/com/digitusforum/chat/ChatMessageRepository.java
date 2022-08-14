package com.digitusforum.chat;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends CrudRepository<ChatMessageEntity, String> {
	List<ChatMessageEntity> findByChatSubjectIdOrderByPosition(String chatSubjectId);
    void deleteById(String userId);
}
