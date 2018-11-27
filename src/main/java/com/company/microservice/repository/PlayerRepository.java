package com.company.microservice.repository;

import com.company.microservice.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends CrudRepository<PlayerEntity, Long>, JpaSpecificationExecutor<PlayerEntity> {

    PlayerEntity findByName(@Param("name") String name);

}
