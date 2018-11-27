package com.company.microservice.controller;

import com.company.microservice.converter.SessionConverter;
import com.company.microservice.domain.Mode;
import com.company.microservice.domain.Session;
import com.company.microservice.entity.SessionEntity;
import com.company.microservice.service.SessionService;
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
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService service;

    @Autowired
    private SessionConverter converter;

    @ApiOperation(value = "Create a Session",
            notes = "Requires valid {@code Mode} requestBody as input",
            nickname = "createSession")
    @RequestMapping(method = RequestMethod.POST, path = "")
    public Session create(@Valid @RequestBody Mode mode) {
        SessionEntity entity = service.create(mode);
        return converter.convert(entity);
    }

    @ApiOperation(value = "Update a Session",
            notes = "Requires valid {@code Session} requestBody as input",
            nickname = "updateSession")
    @RequestMapping(method = RequestMethod.PUT, path = "")
    public Session create(@Valid @RequestBody Session session) {
        SessionEntity entity = service.update(session);
        return converter.convert(entity);
    }

    @ApiOperation(value = "Get a Session",
            notes = "Requires valid {@code String} session key as input",
            nickname = "getSession")
    @RequestMapping(method = RequestMethod.GET, path = "/{sessionKey}")
    public Session read(@Valid @PathVariable String sessionKey) {
        SessionEntity entity = service.findByKey(sessionKey);
        return converter.convert(entity);
    }

    @ApiOperation(value = "Get Sessions",
            notes = "Returns a paged response of available Sessions",
            nickname = "getSessions")
    @RequestMapping(method = RequestMethod.GET, path = "/")
    public Page<Session> read() {
        Page<SessionEntity> entities = service.findAll(PageRequest.of(0, ControllerConstants.MAX_PAGE_SIZE));
        return converter.convert(entities);
    }
}
