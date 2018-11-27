package com.company.microservice.service;

import com.company.microservice.domain.Player;
import com.company.microservice.entity.CountryEntity;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.entity.SessionEntity;
import com.company.microservice.error.EntityAlreadyExistsException;
import com.company.microservice.error.EntityNotFoundException;
import com.company.microservice.repository.PlayerRepository;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PlayerService {

    @Autowired
    private CountryService countryService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PlayerRepository repository;

    @Transactional
    public Page<PlayerEntity> findAll(Pageable pageable) {
        return repository.findAll(null, pageable);
    }

    @Transactional
    public PlayerEntity findByName(String name) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name));

        final PlayerEntity entity = repository.findByName(name);

        if (entity == null) {
            throw new EntityNotFoundException("a player by that identifier does not exist");
        }

        return entity;
    }

    @Transactional
    public PlayerEntity create(Player player) {
        Preconditions.checkNotNull(player);

        final PlayerEntity existingPlayer = repository.findByName(player.getName());

        if (existingPlayer != null) {
            throw new EntityAlreadyExistsException("a player with this name already exists");
        }

        final CountryEntity country = countryService.find(player.getCountry());
        final PlayerEntity entity = new PlayerEntity(player.getName(), country);

        return repository.save(entity);
    }

    @Transactional
    public PlayerEntity join(String name, String sessionKey) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sessionKey));

        final PlayerEntity entity = findByName(name);

        final SessionEntity sessionEntity = sessionService.join(entity, sessionKey);
        entity.setSession(sessionEntity);

        return repository.save(entity);
    }
}
