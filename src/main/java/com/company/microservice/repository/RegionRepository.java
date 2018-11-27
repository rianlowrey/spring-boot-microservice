package com.company.microservice.repository;

import com.company.microservice.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "regions", path = "region")
public interface RegionRepository extends CrudRepository<RegionEntity, Long>, JpaSpecificationExecutor<RegionEntity> {
}
