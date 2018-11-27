package com.company.microservice.converter;

import com.company.microservice.domain.Mode;
import com.company.microservice.entity.ModeEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModeConverter implements Converter<ModeEntity, Mode> {

    @Override
    public Mode convert(ModeEntity entity) {
        Preconditions.checkNotNull(entity);

        return new Mode(entity.getName());
    }

    public Page<Mode> convert(Page<ModeEntity> entities) {
        Preconditions.checkNotNull(entities);

        final Pageable pageable = entities.getPageable();

        ImmutableList.Builder<Mode> builder = new ImmutableList.Builder<>();

        for (ModeEntity entity : entities) {
            builder.add(convert(entity));
        }

        final List<Mode> modes = builder.build();

        return new PageImpl<Mode>(modes, pageable, modes.size());
    }
}
