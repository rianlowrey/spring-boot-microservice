package com.company.microservice.repository;

import com.company.microservice.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "sessions", path = "session")
public interface SessionRepository extends CrudRepository<SessionEntity, Long>, JpaSpecificationExecutor<SessionEntity> {

    SessionEntity findById(@Param("id") long id);

    SessionEntity findByKey(@Param("key") String key);

}
