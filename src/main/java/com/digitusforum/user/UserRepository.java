package com.digitusforum.user;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmailAndPasswordAndDeletedIsFalse(String email, String password);
    Optional<UserEntity> findByEmailAndDeletedIsFalse(String email);
    Optional<UserEntity> findByEmailAndUserIdNotAndDeletedIsFalse(String email, int id);
    Optional<UserEntity> findByUserIdAndDeletedIsFalse(int userId);
    List<UserEntity> findByDeletedIsFalse();
    void deleteByUserId(int userId);
}
