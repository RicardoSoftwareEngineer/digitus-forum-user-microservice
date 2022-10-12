package com.digitusforum.chat;


import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface FactDadosRepository extends CrudRepository<FactDadosEntity, String> {
	//Optional<FactDadosEntity> findByFactDadosId(String factDadosId);
    //void deleteById(String userId);
}
