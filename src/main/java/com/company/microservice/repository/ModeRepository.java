package com.company.microservice.repository;

import com.company.microservice.entity.ModeEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "modes", path = "mode")
public interface ModeRepository extends ModeRepositoryCustom, CrudRepository<ModeEntity, Long>, JpaSpecificationExecutor<ModeEntity> {

    ModeEntity findByName(@Param("name") String name);

}
