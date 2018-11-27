package com.company.microservice.repository;

import com.company.microservice.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "countries", path = "country")
public interface CountryRepository extends CrudRepository<CountryEntity, Long>, JpaSpecificationExecutor<CountryEntity> {
}
