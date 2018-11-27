package com.company.microservice.service;

import com.company.microservice.converter.PlayerConverter;
import com.company.microservice.domain.KeyGenerator;
import com.company.microservice.domain.Mode;
import com.company.microservice.domain.Player;
import com.company.microservice.domain.Session;
import com.company.microservice.entity.ModeEntity;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.entity.SessionEntity;
import com.company.microservice.error.EntityAlreadyExistsException;
import com.company.microservice.error.EntityNotFoundException;
import com.company.microservice.repository.SessionRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class SessionService {

    @Autowired
    private ModeService modeService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private SessionRepository repository;

    @Autowired
    private PlayerConverter playerConverter;

    @Autowired
    private KeyGenerator keyGenerator;

    @Transactional
    public Page<SessionEntity> findAll(Pageable pageable) {
        return repository.findAll(null, pageable);
    }

    @Transactional
    public SessionEntity findByKey(String sessionKey) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sessionKey));

        final SessionEntity entity = repository.findByKey(sessionKey);

        if (entity == null) {
            throw new EntityNotFoundException("a session by that identifier does not exist");
        }

        return entity;
    }

    @Transactional
    public SessionEntity create(Mode mode) {
        final String key = keyGenerator.generate();

        final SessionEntity existingSession = repository.findByKey(key);

        if (existingSession != null) {
            throw new EntityAlreadyExistsException("a session with this key already exists");
        }

        Preconditions.checkNotNull(mode);

        final ModeEntity modeEntity = modeService.findByName(mode.getName());
        final SessionEntity entity = new SessionEntity(key, modeEntity);

        return repository.save(entity);
    }

    @Transactional
    public SessionEntity update(Session session) {
        Preconditions.checkNotNull(session);

        final SessionEntity existingSession = repository.findByKey(session.getKey());

        if (existingSession == null) {
            throw new EntityNotFoundException("a session by that identifier does not exist");
        }

        Preconditions.checkNotNull(session.getPlayers());

        Set<Player> players = playerConverter.convert(existingSession.getPlayers());
        Set<Player> removedPlayers = Sets.difference(players, session.getPlayers());
        Set<Player> addedPlayers = Sets.difference(session.getPlayers(), players);

        for (Player player : removedPlayers) {
            final PlayerEntity playerEntity = playerService.findByName(player.getName());
            existingSession.removePlayer(playerEntity);
        }

        for (Player player : addedPlayers) {
            final PlayerEntity playerEntity = playerService.findByName(player.getName());
            existingSession.addPlayer(playerEntity);
        }

        return repository.save(existingSession);
    }

    @Transactional
    public SessionEntity join(PlayerEntity playerEntity, String sessionKey) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sessionKey));

        final SessionEntity sessionEntity = findByKey(sessionKey);

        final SessionEntity priorSessionEntity = playerEntity.getSession();

        if (priorSessionEntity != null) {
            priorSessionEntity.removePlayer(playerEntity);
            repository.save(priorSessionEntity);
        }

        sessionEntity.addPlayer(playerEntity);

        return repository.save(sessionEntity);

    }
}
