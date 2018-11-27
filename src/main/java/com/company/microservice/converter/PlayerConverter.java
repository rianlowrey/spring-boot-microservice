package com.company.microservice.converter;

import com.company.microservice.domain.Country;
import com.company.microservice.domain.Player;
import com.company.microservice.entity.PlayerEntity;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class PlayerConverter implements Converter<PlayerEntity, Player> {

    @Autowired
    private CountryConverter countryConverter;

    @Override
    public Player convert(PlayerEntity entity) {
        Preconditions.checkNotNull(entity);

        final Country country = countryConverter.convert(entity.getCountry());

        return new Player(entity.getName(), country);
    }

    public Set<Player> convert(Set<PlayerEntity> entities) {
        ImmutableSet.Builder<Player> builder = new ImmutableSet.Builder<>();

        for (PlayerEntity entity : entities) {
            builder.add(convert(entity));
        }

        return builder.build();
    }

    public Page<Player> convert(Page<PlayerEntity> entities) {
        Preconditions.checkNotNull(entities);

        final Pageable pageable = entities.getPageable();

        ImmutableList.Builder<Player> builder = new ImmutableList.Builder<>();

        for (PlayerEntity entity : entities) {
            builder.add(convert(entity));
        }

        final List<Player> players = builder.build();

        return new PageImpl<Player>(players, pageable, players.size());
    }
}
