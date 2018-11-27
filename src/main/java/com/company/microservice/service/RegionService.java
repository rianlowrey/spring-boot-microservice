package com.company.microservice.service;

import com.company.microservice.entity.RegionEntity;
import com.company.microservice.error.EntityNotFoundException;
import com.company.microservice.repository.RegionRepository;
import com.company.microservice.repository.specification.RegionSpecificationFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository repository;

    @Transactional
    public Page<RegionEntity> findAll(Pageable pageable) {
        return repository.findAll(null, pageable);
    }

    @Transactional
    public RegionEntity findByCode(String code) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(code));

        final Optional<RegionEntity> entity = repository.findOne(RegionSpecificationFactory.regionByCode(code));

        if (entity.isEmpty()) {
            throw new EntityNotFoundException("a region by that identifier does not exist");
        }

        return entity.get();
    }
}
