package com.company.microservice.converter;

import com.company.microservice.domain.Mode;
import com.company.microservice.domain.Player;
import com.company.microservice.domain.Session;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.entity.SessionEntity;
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

@Component
public class SessionConverter implements Converter<SessionEntity, Session> {

    @Autowired
    private PlayerConverter playerConverter;

    @Autowired
    private ModeConverter modeConverter;

    @Override
    public Session convert(SessionEntity entity) {
        Preconditions.checkNotNull(entity);

        final Mode mode = modeConverter.convert(entity.getMode());

        ImmutableSet.Builder<Player> players = ImmutableSet.builder();

        for (PlayerEntity playerEntity : entity.getPlayers()) {
            players.add(playerConverter.convert(playerEntity));
        }

        return new Session(entity.getKey(), mode, players.build());
    }

    public Page<Session> convert(Page<SessionEntity> entities) {
        Preconditions.checkNotNull(entities);

        final Pageable pageable = entities.getPageable();

        ImmutableList.Builder<Session> builder = new ImmutableList.Builder<>();

        for (SessionEntity entity : entities) {
            builder.add(convert(entity));
        }

        final List<Session> sessions = builder.build();

        return new PageImpl<Session>(sessions, pageable, sessions.size());
    }
}
