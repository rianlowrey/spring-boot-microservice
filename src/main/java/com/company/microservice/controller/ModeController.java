package com.company.microservice.controller;

import com.company.microservice.converter.ModeConverter;
import com.company.microservice.domain.Mode;
import com.company.microservice.domain.ModePopularity;
import com.company.microservice.entity.ModeEntity;
import com.company.microservice.service.ModeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/modes")
public class ModeController {
    @Autowired
    private ModeService service;

    @Autowired
    private ModeConverter converter;

    @ApiOperation(value = "Get Modes",
            notes = "Returns a paged response of Modes",
            nickname = "getModes")
    @RequestMapping(method = RequestMethod.GET, path = "")
    public Page<Mode> get() {
        Page<ModeEntity> entities = service.findAll(PageRequest.of(0, ControllerConstants.MAX_PAGE_SIZE));
        return converter.convert(entities);
    }

    @ApiOperation(value = "Get a Mode",
            notes = "Requires valid {@code String} mode name as input",
            nickname = "getMode")
    @RequestMapping(method = RequestMethod.GET, path = "/{modeName}")
    public Mode get(@Valid @PathVariable String modeName) {
        ModeEntity entity = service.findByName(modeName);
        return converter.convert(entity);
    }

    @ApiOperation(value = "Get Popular Modes",
            notes = "Requires valid {@code String} region ISO 3661 alpha2 code as input",
            nickname = "getPopularModes")
    @RequestMapping(method = RequestMethod.GET, path = "/country/{countryAlpha2}")
    public Page<ModePopularity> getPopularModes(@Valid @PathVariable String countryAlpha2) {
        Page<ModePopularity> popularModes = service.getPopularModes(countryAlpha2,
                PageRequest.of(0, ControllerConstants.MAX_PAGE_SIZE));
        return popularModes;
    }
}
