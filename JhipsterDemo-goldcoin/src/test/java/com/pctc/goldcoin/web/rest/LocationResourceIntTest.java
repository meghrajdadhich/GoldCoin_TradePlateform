package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.Location;
import com.pctc.goldcoin.repository.LocationRepository;
import com.pctc.goldcoin.service.LocationService;
import com.pctc.goldcoin.repository.search.LocationSearchRepository;
import com.pctc.goldcoin.service.dto.LocationDTO;
import com.pctc.goldcoin.service.mapper.LocationMapper;
import com.pctc.goldcoin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pctc.goldcoin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LocationResource REST controller.
 *
 * @see LocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class LocationResourceIntTest {

    private static final Long DEFAULT_LOC_ID = 1L;
    private static final Long UPDATED_LOC_ID = 2L;

    private static final String DEFAULT_LOC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_LOC_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_LOC_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_LOC_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_LOC_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_AREA = "AAAAAAAAAA";
    private static final String UPDATED_LOC_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_CITY = "AAAAAAAAAA";
    private static final String UPDATED_LOC_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_STATE = "AAAAAAAAAA";
    private static final String UPDATED_LOC_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_LOC_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_LOC_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LOC_PIN_CODE = "BBBBBBBBBB";

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Autowired
    private LocationService locationService;

    @Autowired
    private LocationSearchRepository locationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocationMockMvc;

    private Location location;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LocationResource locationResource = new LocationResource(locationService);
        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .locId(DEFAULT_LOC_ID)
            .locType(DEFAULT_LOC_TYPE)
            .locTitle(DEFAULT_LOC_TITLE)
            .locAddress2(DEFAULT_LOC_ADDRESS_2)
            .locAddress1(DEFAULT_LOC_ADDRESS_1)
            .locArea(DEFAULT_LOC_AREA)
            .locCity(DEFAULT_LOC_CITY)
            .locState(DEFAULT_LOC_STATE)
            .locCountry(DEFAULT_LOC_COUNTRY)
            .locPinCode(DEFAULT_LOC_PIN_CODE);
        return location;
    }

    @Before
    public void initTest() {
        locationSearchRepository.deleteAll();
        location = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLocId()).isEqualTo(DEFAULT_LOC_ID);
        assertThat(testLocation.getLocType()).isEqualTo(DEFAULT_LOC_TYPE);
        assertThat(testLocation.getLocTitle()).isEqualTo(DEFAULT_LOC_TITLE);
        assertThat(testLocation.getLocAddress2()).isEqualTo(DEFAULT_LOC_ADDRESS_2);
        assertThat(testLocation.getLocAddress1()).isEqualTo(DEFAULT_LOC_ADDRESS_1);
        assertThat(testLocation.getLocArea()).isEqualTo(DEFAULT_LOC_AREA);
        assertThat(testLocation.getLocCity()).isEqualTo(DEFAULT_LOC_CITY);
        assertThat(testLocation.getLocState()).isEqualTo(DEFAULT_LOC_STATE);
        assertThat(testLocation.getLocCountry()).isEqualTo(DEFAULT_LOC_COUNTRY);
        assertThat(testLocation.getLocPinCode()).isEqualTo(DEFAULT_LOC_PIN_CODE);

        // Validate the Location in Elasticsearch
        Location locationEs = locationSearchRepository.findOne(testLocation.getId());
        assertThat(locationEs).isEqualToIgnoringGivenFields(testLocation);
    }

    @Test
    @Transactional
    public void createLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location with an existing ID
        location.setId(1L);
        LocationDTO locationDTO = locationMapper.toDto(location);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].locId").value(hasItem(DEFAULT_LOC_ID.intValue())))
            .andExpect(jsonPath("$.[*].locType").value(hasItem(DEFAULT_LOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locTitle").value(hasItem(DEFAULT_LOC_TITLE.toString())))
            .andExpect(jsonPath("$.[*].locAddress2").value(hasItem(DEFAULT_LOC_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].locAddress1").value(hasItem(DEFAULT_LOC_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].locArea").value(hasItem(DEFAULT_LOC_AREA.toString())))
            .andExpect(jsonPath("$.[*].locCity").value(hasItem(DEFAULT_LOC_CITY.toString())))
            .andExpect(jsonPath("$.[*].locState").value(hasItem(DEFAULT_LOC_STATE.toString())))
            .andExpect(jsonPath("$.[*].locCountry").value(hasItem(DEFAULT_LOC_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].locPinCode").value(hasItem(DEFAULT_LOC_PIN_CODE.toString())));
    }

    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.locId").value(DEFAULT_LOC_ID.intValue()))
            .andExpect(jsonPath("$.locType").value(DEFAULT_LOC_TYPE.toString()))
            .andExpect(jsonPath("$.locTitle").value(DEFAULT_LOC_TITLE.toString()))
            .andExpect(jsonPath("$.locAddress2").value(DEFAULT_LOC_ADDRESS_2.toString()))
            .andExpect(jsonPath("$.locAddress1").value(DEFAULT_LOC_ADDRESS_1.toString()))
            .andExpect(jsonPath("$.locArea").value(DEFAULT_LOC_AREA.toString()))
            .andExpect(jsonPath("$.locCity").value(DEFAULT_LOC_CITY.toString()))
            .andExpect(jsonPath("$.locState").value(DEFAULT_LOC_STATE.toString()))
            .andExpect(jsonPath("$.locCountry").value(DEFAULT_LOC_COUNTRY.toString()))
            .andExpect(jsonPath("$.locPinCode").value(DEFAULT_LOC_PIN_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findOne(location.getId());
        // Disconnect from session so that the updates on updatedLocation are not directly saved in db
        em.detach(updatedLocation);
        updatedLocation
            .locId(UPDATED_LOC_ID)
            .locType(UPDATED_LOC_TYPE)
            .locTitle(UPDATED_LOC_TITLE)
            .locAddress2(UPDATED_LOC_ADDRESS_2)
            .locAddress1(UPDATED_LOC_ADDRESS_1)
            .locArea(UPDATED_LOC_AREA)
            .locCity(UPDATED_LOC_CITY)
            .locState(UPDATED_LOC_STATE)
            .locCountry(UPDATED_LOC_COUNTRY)
            .locPinCode(UPDATED_LOC_PIN_CODE);
        LocationDTO locationDTO = locationMapper.toDto(updatedLocation);

        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLocId()).isEqualTo(UPDATED_LOC_ID);
        assertThat(testLocation.getLocType()).isEqualTo(UPDATED_LOC_TYPE);
        assertThat(testLocation.getLocTitle()).isEqualTo(UPDATED_LOC_TITLE);
        assertThat(testLocation.getLocAddress2()).isEqualTo(UPDATED_LOC_ADDRESS_2);
        assertThat(testLocation.getLocAddress1()).isEqualTo(UPDATED_LOC_ADDRESS_1);
        assertThat(testLocation.getLocArea()).isEqualTo(UPDATED_LOC_AREA);
        assertThat(testLocation.getLocCity()).isEqualTo(UPDATED_LOC_CITY);
        assertThat(testLocation.getLocState()).isEqualTo(UPDATED_LOC_STATE);
        assertThat(testLocation.getLocCountry()).isEqualTo(UPDATED_LOC_COUNTRY);
        assertThat(testLocation.getLocPinCode()).isEqualTo(UPDATED_LOC_PIN_CODE);

        // Validate the Location in Elasticsearch
        Location locationEs = locationSearchRepository.findOne(testLocation.getId());
        assertThat(locationEs).isEqualToIgnoringGivenFields(testLocation);
    }

    @Test
    @Transactional
    public void updateNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Create the Location
        LocationDTO locationDTO = locationMapper.toDto(location);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(locationDTO)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);
        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Get the location
        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean locationExistsInEs = locationSearchRepository.exists(location.getId());
        assertThat(locationExistsInEs).isFalse();

        // Validate the database is empty
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        locationSearchRepository.save(location);

        // Search the location
        restLocationMockMvc.perform(get("/api/_search/locations?query=id:" + location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].locId").value(hasItem(DEFAULT_LOC_ID.intValue())))
            .andExpect(jsonPath("$.[*].locType").value(hasItem(DEFAULT_LOC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].locTitle").value(hasItem(DEFAULT_LOC_TITLE.toString())))
            .andExpect(jsonPath("$.[*].locAddress2").value(hasItem(DEFAULT_LOC_ADDRESS_2.toString())))
            .andExpect(jsonPath("$.[*].locAddress1").value(hasItem(DEFAULT_LOC_ADDRESS_1.toString())))
            .andExpect(jsonPath("$.[*].locArea").value(hasItem(DEFAULT_LOC_AREA.toString())))
            .andExpect(jsonPath("$.[*].locCity").value(hasItem(DEFAULT_LOC_CITY.toString())))
            .andExpect(jsonPath("$.[*].locState").value(hasItem(DEFAULT_LOC_STATE.toString())))
            .andExpect(jsonPath("$.[*].locCountry").value(hasItem(DEFAULT_LOC_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].locPinCode").value(hasItem(DEFAULT_LOC_PIN_CODE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
        Location location1 = new Location();
        location1.setId(1L);
        Location location2 = new Location();
        location2.setId(location1.getId());
        assertThat(location1).isEqualTo(location2);
        location2.setId(2L);
        assertThat(location1).isNotEqualTo(location2);
        location1.setId(null);
        assertThat(location1).isNotEqualTo(location2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LocationDTO.class);
        LocationDTO locationDTO1 = new LocationDTO();
        locationDTO1.setId(1L);
        LocationDTO locationDTO2 = new LocationDTO();
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO2.setId(locationDTO1.getId());
        assertThat(locationDTO1).isEqualTo(locationDTO2);
        locationDTO2.setId(2L);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
        locationDTO1.setId(null);
        assertThat(locationDTO1).isNotEqualTo(locationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(locationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(locationMapper.fromId(null)).isNull();
    }
}
