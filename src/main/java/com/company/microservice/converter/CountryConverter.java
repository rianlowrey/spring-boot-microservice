package com.company.microservice.converter;

import com.company.microservice.domain.Country;
import com.company.microservice.domain.Region;
import com.company.microservice.entity.CountryEntity;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CountryConverter implements Converter<CountryEntity, Country> {

    @Autowired
    private RegionConverter regionConverter;

    @Override
    public Country convert(CountryEntity entity) {
        Preconditions.checkNotNull(entity);

        final Region region = regionConverter.convert(entity.getRegion());

        return new Country(entity.getName(), entity.getCode(), entity.getAlpha2(), entity.getAlpha3(), region);
    }
}
