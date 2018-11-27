package com.company.microservice.converter;

import com.company.microservice.domain.Region;
import com.company.microservice.entity.RegionEntity;
import com.google.common.base.Preconditions;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegionConverter implements Converter<RegionEntity, Region> {

    @Override
    public Region convert(RegionEntity entity) {
        Preconditions.checkNotNull(entity);

        return new Region(entity.getName(), entity.getCode());
    }
}
