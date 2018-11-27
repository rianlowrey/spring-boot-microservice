package com.company.microservice;

import com.company.microservice.converter.CountryConverter;
import com.company.microservice.converter.ModeConverter;
import com.company.microservice.converter.PlayerConverter;
import com.company.microservice.converter.RegionConverter;
import com.company.microservice.converter.SessionConverter;
import com.company.microservice.domain.Country;
import com.company.microservice.domain.Mode;
import com.company.microservice.domain.Player;
import com.company.microservice.domain.Region;
import com.company.microservice.domain.Session;
import com.company.microservice.entity.CountryEntity;
import com.company.microservice.entity.ModeEntity;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.entity.RegionEntity;
import com.company.microservice.entity.SessionEntity;
import com.company.microservice.repository.CountryRepository;
import com.company.microservice.repository.ModeRepository;
import com.company.microservice.repository.PlayerRepository;
import com.company.microservice.repository.RegionRepository;
import com.company.microservice.repository.SessionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.Assert.notNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModeControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ModeRepository modeRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RegionConverter regionConverter;

    @Autowired
    private CountryConverter countryConverter;

    @Autowired
    private ModeConverter modeConverter;

    @Autowired
    private SessionConverter sessionConverter;

    @Autowired
    private PlayerConverter playerConverter;

    private RegionEntity regionEntity;
    private CountryEntity countryEntity;
    private ModeEntity modeEntity;
    private SessionEntity sessionEntity;
    private PlayerEntity playerEntity;

    private Region region;
    private Country country;
    private Mode mode;
    private Session session;
    private Player player;

    private static final String REGION_NAME = "Americas";
    private static final String REGION_CODE = "019";
    private static final String COUNTRY_NAME = "United States of America";
    private static final String COUNTRY_CODE = "840";
    private static final String COUNTRY_ALPHA2 = "US";
    private static final String COUNTRY_ALPHA3 = "USA";
    private static final String MODE_NAME = "MODENAME";
    private static final String SESSION_KEY = "SESSIONKEY";
    private static final String PLAYER_NAME = "PLAYERNAME";
    private static final String PLAYER_COUNTRY_ALPHA2 = COUNTRY_ALPHA2;

    @Before
    public void setup() throws Exception {
        playerRepository.deleteAll();
        sessionRepository.deleteAll();
        modeRepository.deleteAll();
        countryRepository.deleteAll();
        regionRepository.deleteAll();

        regionEntity = regionRepository.save(new RegionEntity(REGION_NAME, REGION_CODE));
        region = regionConverter.convert(regionEntity);

        countryEntity = countryRepository.save(new CountryEntity(COUNTRY_NAME, COUNTRY_CODE, COUNTRY_ALPHA2, COUNTRY_ALPHA3, regionEntity));
        country = countryConverter.convert(countryEntity);
    }

    public void createMode() {
        modeEntity = modeRepository.save(new ModeEntity(MODE_NAME));
        mode = modeConverter.convert(modeEntity);
    }

    public void postCreateAndJoinSession() {
        sessionEntity = sessionRepository.save(new SessionEntity(SESSION_KEY, modeEntity));
        session = sessionConverter.convert(sessionEntity);

        playerEntity = playerRepository.save(new PlayerEntity(PLAYER_NAME, countryEntity));
        player = playerConverter.convert(playerEntity);

        sessionEntity.addPlayer(playerEntity);
        sessionEntity = sessionRepository.save(sessionEntity);
        playerEntity.setSession(sessionEntity);
        playerEntity = playerRepository.save(playerEntity);

        assertThat(playerEntity.getSession(), is(notNullValue()));
        assertThat(playerEntity.getSession(), is(sessionEntity));
    }

    @Test
    public void shouldNotCreateEntity() throws Exception {
        mode = new Mode(MODE_NAME);

        mockMvc.perform(
                post("/modes")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(mode)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldRetrieveEntity() throws Exception {
        createMode();

        mockMvc.perform(
                get("/modes/{modeName}", mode.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(mode.getName()));
    }

    @Test
    public void shouldQueryEntities() throws Exception {
        createMode();

        mockMvc.perform(
                get("/modes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value(mode.getName()));
    }

    @Test
    public void shouldQueryCustomEntities() throws Exception {
        createMode();
        postCreateAndJoinSession();

        mockMvc.perform(
                get("/modes/country/{countryAlpha2}", country.getAlpha2()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[?(@.mode == '%s' && @.popularity == 1)]", mode.getName())
                        .exists());
    }

    @Test
    public void shouldNotUpdateEntity() throws Exception {
        createMode();

        Mode updatedMode = new Mode("NEWMODENAME");

        mockMvc.perform(
                put("/modes/{modeName}", mode.getName())
                        .content(objectMapper.writeValueAsString(updatedMode)))
                .andExpect(status().is4xxClientError());
    }
}