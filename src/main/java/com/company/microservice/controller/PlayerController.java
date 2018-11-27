package com.company.microservice.controller;

import com.company.microservice.converter.PlayerConverter;
import com.company.microservice.domain.Player;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.service.PlayerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @Autowired
    private PlayerConverter converter;

    @ApiOperation(value = "Create a Player",
            notes = "Requires valid {@code Player} requestBody as input",
            nickname = "createPlayer")
    @RequestMapping(method = RequestMethod.POST, path = "")
    public Player create(@Valid @RequestBody Player player) {
        PlayerEntity entity = service.create(player);
        return converter.convert(entity);
    }

    @ApiOperation(value = "Get a Player",
            notes = "Requires valid {@code String} player name as input",
            nickname = "getPlayer")
    @RequestMapping(method = RequestMethod.GET, path = "/{playerName}")
    public Player read(@PathVariable String playerName) {
        PlayerEntity entity = service.findByName(playerName);
        return converter.convert(entity);
    }

    @ApiOperation(value = "Get Players",
            notes = "Returns a paged response of Players",
            nickname = "getPlayers")
    @RequestMapping(method = RequestMethod.GET, path = "")
    public Page<Player> read() {
        Page<PlayerEntity> entities = service.findAll(PageRequest.of(0, ControllerConstants.MAX_PAGE_SIZE));
        return converter.convert(entities);
    }

    @ApiOperation(value = "Join a Player to an existing session",
            notes = "Requires valid {@code String} session key as input",
            nickname = "createPlayer")
    @RequestMapping(method = RequestMethod.PUT, path = "/{playerName}/{sessionKey}")
    public Player update(@PathVariable String playerName, @PathVariable String sessionKey) {
        PlayerEntity entity = service.join(playerName, sessionKey);
        return converter.convert(entity);
    }
}
