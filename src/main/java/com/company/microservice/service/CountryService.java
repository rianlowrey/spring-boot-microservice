package com.company.microservice.service;

import com.company.microservice.domain.Country;
import com.company.microservice.entity.CountryEntity;
import com.company.microservice.error.EntityNotFoundException;
import com.company.microservice.repository.CountryRepository;
import com.company.microservice.repository.specification.CountrySpecificationFactory;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository repository;

    @Transactional
    public Page<CountryEntity> findAll(Pageable pageable) {
        return repository.findAll(null, pageable);
    }

    @Transactional
    public CountryEntity find(Country country) {
        Preconditions.checkNotNull(country);

        final Optional<CountryEntity> entity = repository.findOne(CountrySpecificationFactory.countryByAlpha2(country));

        if (entity.isEmpty()) {
            throw new EntityNotFoundException("a country by that identifier does not exist");
        }

        return entity.get();
    }

    @Transactional
    public CountryEntity findByAlpha2(String regionAlpha2) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(regionAlpha2));

        final Optional<CountryEntity> entity = repository.findOne(CountrySpecificationFactory.countryByAlpha2(regionAlpha2));

        if (entity.isEmpty()) {
            throw new EntityNotFoundException("a country by that identifier does not exist");
        }

        return entity.get();
    }
}
