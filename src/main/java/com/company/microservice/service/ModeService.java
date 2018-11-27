package com.company.microservice.service;

import com.company.microservice.domain.ModePopularity;
import com.company.microservice.entity.CountryEntity;
import com.company.microservice.entity.ModeEntity;
import com.company.microservice.entity.RegionEntity;
import com.company.microservice.error.EntityNotFoundException;
import com.company.microservice.repository.ModeRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ModeService {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ModeRepository repository;

    @Transactional
    public Page<ModeEntity> findAll(Pageable pageable) {
        return repository.findAll(null, pageable);
    }

    @Transactional
    public ModeEntity findByName(String modeName) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(modeName));

        final ModeEntity entity = repository.findByName(modeName);

        if (entity == null) {
            throw new EntityNotFoundException("a mode by that identifier does not exist");
        }

        return entity;
    }

    @Transactional
    public Page<ModePopularity> getPopularModes(String countryAlpha2, Pageable pageable) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(countryAlpha2));

        final CountryEntity countryEntity = countryService.findByAlpha2(countryAlpha2);

        final RegionEntity regionEntity = countryEntity.getRegion();

        return repository.getModePopularityByRegion(regionEntity, pageable);
    }
}
