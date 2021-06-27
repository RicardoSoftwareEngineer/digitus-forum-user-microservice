package com.digitusforum.user.model.repository;


import com.digitusforum.user.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByEmailAndPasswordAndDeletedIsFalse(String email, String password);
    Optional<User> findByEmailAndDeletedIsFalse(String email);
    Optional<User> findByUserIdAndDeletedIsFalse(String userId);
    void deleteByUserId(String userId);
}
