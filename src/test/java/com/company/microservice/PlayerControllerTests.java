package com.company.microservice;

import com.company.microservice.converter.CountryConverter;
import com.company.microservice.converter.PlayerConverter;
import com.company.microservice.converter.RegionConverter;
import com.company.microservice.domain.Country;
import com.company.microservice.domain.Player;
import com.company.microservice.domain.Region;
import com.company.microservice.entity.CountryEntity;
import com.company.microservice.entity.PlayerEntity;
import com.company.microservice.entity.RegionEntity;
import com.company.microservice.repository.CountryRepository;
import com.company.microservice.repository.PlayerRepository;
import com.company.microservice.repository.RegionRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private RegionConverter regionConverter;

    @Autowired
    private CountryConverter countryConverter;

    @Autowired
    private PlayerConverter playerConverter;

    private RegionEntity regionEntity;
    private CountryEntity countryEntity;
    private PlayerEntity playerEntity;

    private Region region;
    private Country country;
    private Player player;

    private static final String REGION_NAME = "Americas";
    private static final String REGION_CODE = "019";
    private static final String COUNTRY_NAME = "United States of America";
    private static final String COUNTRY_CODE = "840";
    private static final String COUNTRY_ALPHA2 = "US";
    private static final String COUNTRY_ALPHA3 = "USA";
    private static final String PLAYER_NAME = "PLAYERNAME";
    private static final String PLAYER_COUNTRY_ALPHA2 = COUNTRY_ALPHA2;

    @Before
    public void setup() throws Exception {
        playerRepository.deleteAll();
        countryRepository.deleteAll();
        regionRepository.deleteAll();

        regionEntity = regionRepository.save(new RegionEntity(REGION_NAME, REGION_CODE));
        region = regionConverter.convert(regionEntity);

        countryEntity = countryRepository.save(new CountryEntity(COUNTRY_NAME, COUNTRY_CODE, COUNTRY_ALPHA2, COUNTRY_ALPHA3, regionEntity));
        country = countryConverter.convert(countryEntity);
    }

    public void createPlayer() {
        playerEntity = playerRepository.save(new PlayerEntity(PLAYER_NAME, countryEntity));
        player = playerConverter.convert(playerEntity);
    }

    @Test
    public void shouldCreateEntity() throws Exception {
        player = new Player(PLAYER_NAME, country);

        mockMvc.perform(
                post("/players")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(player)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldRetrieveEntity() throws Exception {
        createPlayer();

        mockMvc.perform(
                get("/players/{playerName}", player.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name")
                        .value(PLAYER_NAME));
    }

    @Test
    public void shouldQueryEntities() throws Exception {
        createPlayer();

        mockMvc.perform(
                get("/players"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name")
                        .value(player.getName()));
    }

    @Test
    public void shouldNotUpdateEntity() throws Exception {
        createPlayer();

        Player updatedPlayer = new Player("NEWPLAYERNAME", player.getCountry());

        mockMvc.perform(
                put("/players/{playerName}", player.getName())
                        .content(objectMapper.writeValueAsString(updatedPlayer)))
                .andExpect(status().is4xxClientError());
    }
}