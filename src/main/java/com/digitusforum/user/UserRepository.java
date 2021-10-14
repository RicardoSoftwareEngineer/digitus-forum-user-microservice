package com.digitusforum.user;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    Optional<UserEntity> findByEmailAndPasswordAndDeletedIsFalse(String email, String password);
    Optional<UserEntity> findByEmailAndDeletedIsFalse(String email);
    Optional<UserEntity> findByEmailAndIdNotAndDeletedIsFalse(String email, String id);
    Optional<UserEntity> findByIdAndDeletedIsFalse(String userId);
    List<UserEntity> findByDeletedIsFalse();
    void deleteById(String userId);
}
