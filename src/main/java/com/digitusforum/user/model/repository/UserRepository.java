package com.digitusforum.user.model.repository;


import com.digitusforum.user.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmailAndPasswordAndDeletedIsFalse(String email, String password);
    Optional<User> findByEmailAndDeletedIsFalse(String email);
    Optional<User> findByEmailAndUserIdNotAndDeletedIsFalse(String email, int id);
    Optional<User> findByUserIdAndDeletedIsFalse(int userId);
    List<User> findByDeletedIsFalse();
    void deleteByUserId(int userId);
}
