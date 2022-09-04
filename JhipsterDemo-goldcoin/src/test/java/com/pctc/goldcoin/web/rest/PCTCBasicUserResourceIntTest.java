package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.PCTCBasicUser;
import com.pctc.goldcoin.repository.PCTCBasicUserRepository;
import com.pctc.goldcoin.service.PCTCBasicUserService;
import com.pctc.goldcoin.repository.search.PCTCBasicUserSearchRepository;
import com.pctc.goldcoin.service.dto.PCTCBasicUserDTO;
import com.pctc.goldcoin.service.mapper.PCTCBasicUserMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.pctc.goldcoin.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PCTCBasicUserResource REST controller.
 *
 * @see PCTCBasicUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class PCTCBasicUserResourceIntTest {

    private static final Instant DEFAULT_DATE_OF_BIRTH = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_BIRTH = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CONTACT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LAND_MARK = "AAAAAAAAAA";
    private static final String UPDATED_LAND_MARK = "BBBBBBBBBB";

    private static final String DEFAULT_SUB_AREA = "AAAAAAAAAA";
    private static final String UPDATED_SUB_AREA = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PARTY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PARTY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_REF_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_REF_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    @Autowired
    private PCTCBasicUserRepository pCTCBasicUserRepository;

    @Autowired
    private PCTCBasicUserMapper pCTCBasicUserMapper;

    @Autowired
    private PCTCBasicUserService pCTCBasicUserService;

    @Autowired
    private PCTCBasicUserSearchRepository pCTCBasicUserSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPCTCBasicUserMockMvc;

    private PCTCBasicUser pCTCBasicUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PCTCBasicUserResource pCTCBasicUserResource = new PCTCBasicUserResource(pCTCBasicUserService);
        this.restPCTCBasicUserMockMvc = MockMvcBuilders.standaloneSetup(pCTCBasicUserResource)
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
    public static PCTCBasicUser createEntity(EntityManager em) {
        PCTCBasicUser pCTCBasicUser = new PCTCBasicUser()
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .contactNumber(DEFAULT_CONTACT_NUMBER)
            .gender(DEFAULT_GENDER)
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .landMark(DEFAULT_LAND_MARK)
            .subArea(DEFAULT_SUB_AREA)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .pinCode(DEFAULT_PIN_CODE)
            .partyType(DEFAULT_PARTY_TYPE)
            .refUserId(DEFAULT_REF_USER_ID)
            .nationality(DEFAULT_NATIONALITY);
        return pCTCBasicUser;
    }

    @Before
    public void initTest() {
        pCTCBasicUserSearchRepository.deleteAll();
        pCTCBasicUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createPCTCBasicUser() throws Exception {
        int databaseSizeBeforeCreate = pCTCBasicUserRepository.findAll().size();

        // Create the PCTCBasicUser
        PCTCBasicUserDTO pCTCBasicUserDTO = pCTCBasicUserMapper.toDto(pCTCBasicUser);
        restPCTCBasicUserMockMvc.perform(post("/api/pctc-basic-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pCTCBasicUserDTO)))
            .andExpect(status().isCreated());

        // Validate the PCTCBasicUser in the database
        List<PCTCBasicUser> pCTCBasicUserList = pCTCBasicUserRepository.findAll();
        assertThat(pCTCBasicUserList).hasSize(databaseSizeBeforeCreate + 1);
        PCTCBasicUser testPCTCBasicUser = pCTCBasicUserList.get(pCTCBasicUserList.size() - 1);
        assertThat(testPCTCBasicUser.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testPCTCBasicUser.getContactNumber()).isEqualTo(DEFAULT_CONTACT_NUMBER);
        assertThat(testPCTCBasicUser.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPCTCBasicUser.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testPCTCBasicUser.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testPCTCBasicUser.getLandMark()).isEqualTo(DEFAULT_LAND_MARK);
        assertThat(testPCTCBasicUser.getSubArea()).isEqualTo(DEFAULT_SUB_AREA);
        assertThat(testPCTCBasicUser.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testPCTCBasicUser.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testPCTCBasicUser.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testPCTCBasicUser.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testPCTCBasicUser.getPartyType()).isEqualTo(DEFAULT_PARTY_TYPE);
        assertThat(testPCTCBasicUser.getRefUserId()).isEqualTo(DEFAULT_REF_USER_ID);
        assertThat(testPCTCBasicUser.getNationality()).isEqualTo(DEFAULT_NATIONALITY);

        // Validate the PCTCBasicUser in Elasticsearch
        PCTCBasicUser pCTCBasicUserEs = pCTCBasicUserSearchRepository.findOne(testPCTCBasicUser.getId());
        assertThat(pCTCBasicUserEs).isEqualToIgnoringGivenFields(testPCTCBasicUser);
    }

    @Test
    @Transactional
    public void createPCTCBasicUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pCTCBasicUserRepository.findAll().size();

        // Create the PCTCBasicUser with an existing ID
        pCTCBasicUser.setId(1L);
        PCTCBasicUserDTO pCTCBasicUserDTO = pCTCBasicUserMapper.toDto(pCTCBasicUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPCTCBasicUserMockMvc.perform(post("/api/pctc-basic-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pCTCBasicUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PCTCBasicUser in the database
        List<PCTCBasicUser> pCTCBasicUserList = pCTCBasicUserRepository.findAll();
        assertThat(pCTCBasicUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPCTCBasicUsers() throws Exception {
        // Initialize the database
        pCTCBasicUserRepository.saveAndFlush(pCTCBasicUser);

        // Get all the pCTCBasicUserList
        restPCTCBasicUserMockMvc.perform(get("/api/pctc-basic-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pCTCBasicUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK.toString())))
            .andExpect(jsonPath("$.[*].subArea").value(hasItem(DEFAULT_SUB_AREA.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE.toString())))
            .andExpect(jsonPath("$.[*].partyType").value(hasItem(DEFAULT_PARTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].refUserId").value(hasItem(DEFAULT_REF_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())));
    }

    @Test
    @Transactional
    public void getPCTCBasicUser() throws Exception {
        // Initialize the database
        pCTCBasicUserRepository.saveAndFlush(pCTCBasicUser);

        // Get the pCTCBasicUser
        restPCTCBasicUserMockMvc.perform(get("/api/pctc-basic-users/{id}", pCTCBasicUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pCTCBasicUser.getId().intValue()))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.contactNumber").value(DEFAULT_CONTACT_NUMBER.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1.toString()))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2.toString()))
            .andExpect(jsonPath("$.landMark").value(DEFAULT_LAND_MARK.toString()))
            .andExpect(jsonPath("$.subArea").value(DEFAULT_SUB_AREA.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE.toString()))
            .andExpect(jsonPath("$.partyType").value(DEFAULT_PARTY_TYPE.toString()))
            .andExpect(jsonPath("$.refUserId").value(DEFAULT_REF_USER_ID.toString()))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPCTCBasicUser() throws Exception {
        // Get the pCTCBasicUser
        restPCTCBasicUserMockMvc.perform(get("/api/pctc-basic-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePCTCBasicUser() throws Exception {
        // Initialize the database
        pCTCBasicUserRepository.saveAndFlush(pCTCBasicUser);
        pCTCBasicUserSearchRepository.save(pCTCBasicUser);
        int databaseSizeBeforeUpdate = pCTCBasicUserRepository.findAll().size();

        // Update the pCTCBasicUser
        PCTCBasicUser updatedPCTCBasicUser = pCTCBasicUserRepository.findOne(pCTCBasicUser.getId());
        // Disconnect from session so that the updates on updatedPCTCBasicUser are not directly saved in db
        em.detach(updatedPCTCBasicUser);
        updatedPCTCBasicUser
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .contactNumber(UPDATED_CONTACT_NUMBER)
            .gender(UPDATED_GENDER)
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .landMark(UPDATED_LAND_MARK)
            .subArea(UPDATED_SUB_AREA)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .pinCode(UPDATED_PIN_CODE)
            .partyType(UPDATED_PARTY_TYPE)
            .refUserId(UPDATED_REF_USER_ID)
            .nationality(UPDATED_NATIONALITY);
        PCTCBasicUserDTO pCTCBasicUserDTO = pCTCBasicUserMapper.toDto(updatedPCTCBasicUser);

        restPCTCBasicUserMockMvc.perform(put("/api/pctc-basic-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pCTCBasicUserDTO)))
            .andExpect(status().isOk());

        // Validate the PCTCBasicUser in the database
        List<PCTCBasicUser> pCTCBasicUserList = pCTCBasicUserRepository.findAll();
        assertThat(pCTCBasicUserList).hasSize(databaseSizeBeforeUpdate);
        PCTCBasicUser testPCTCBasicUser = pCTCBasicUserList.get(pCTCBasicUserList.size() - 1);
        assertThat(testPCTCBasicUser.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testPCTCBasicUser.getContactNumber()).isEqualTo(UPDATED_CONTACT_NUMBER);
        assertThat(testPCTCBasicUser.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPCTCBasicUser.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testPCTCBasicUser.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testPCTCBasicUser.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testPCTCBasicUser.getSubArea()).isEqualTo(UPDATED_SUB_AREA);
        assertThat(testPCTCBasicUser.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testPCTCBasicUser.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testPCTCBasicUser.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testPCTCBasicUser.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testPCTCBasicUser.getPartyType()).isEqualTo(UPDATED_PARTY_TYPE);
        assertThat(testPCTCBasicUser.getRefUserId()).isEqualTo(UPDATED_REF_USER_ID);
        assertThat(testPCTCBasicUser.getNationality()).isEqualTo(UPDATED_NATIONALITY);

        // Validate the PCTCBasicUser in Elasticsearch
        PCTCBasicUser pCTCBasicUserEs = pCTCBasicUserSearchRepository.findOne(testPCTCBasicUser.getId());
        assertThat(pCTCBasicUserEs).isEqualToIgnoringGivenFields(testPCTCBasicUser);
    }

    @Test
    @Transactional
    public void updateNonExistingPCTCBasicUser() throws Exception {
        int databaseSizeBeforeUpdate = pCTCBasicUserRepository.findAll().size();

        // Create the PCTCBasicUser
        PCTCBasicUserDTO pCTCBasicUserDTO = pCTCBasicUserMapper.toDto(pCTCBasicUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPCTCBasicUserMockMvc.perform(put("/api/pctc-basic-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pCTCBasicUserDTO)))
            .andExpect(status().isCreated());

        // Validate the PCTCBasicUser in the database
        List<PCTCBasicUser> pCTCBasicUserList = pCTCBasicUserRepository.findAll();
        assertThat(pCTCBasicUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePCTCBasicUser() throws Exception {
        // Initialize the database
        pCTCBasicUserRepository.saveAndFlush(pCTCBasicUser);
        pCTCBasicUserSearchRepository.save(pCTCBasicUser);
        int databaseSizeBeforeDelete = pCTCBasicUserRepository.findAll().size();

        // Get the pCTCBasicUser
        restPCTCBasicUserMockMvc.perform(delete("/api/pctc-basic-users/{id}", pCTCBasicUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean pCTCBasicUserExistsInEs = pCTCBasicUserSearchRepository.exists(pCTCBasicUser.getId());
        assertThat(pCTCBasicUserExistsInEs).isFalse();

        // Validate the database is empty
        List<PCTCBasicUser> pCTCBasicUserList = pCTCBasicUserRepository.findAll();
        assertThat(pCTCBasicUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchPCTCBasicUser() throws Exception {
        // Initialize the database
        pCTCBasicUserRepository.saveAndFlush(pCTCBasicUser);
        pCTCBasicUserSearchRepository.save(pCTCBasicUser);

        // Search the pCTCBasicUser
        restPCTCBasicUserMockMvc.perform(get("/api/_search/pctc-basic-users?query=id:" + pCTCBasicUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pCTCBasicUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].contactNumber").value(hasItem(DEFAULT_CONTACT_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1.toString())))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2.toString())))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK.toString())))
            .andExpect(jsonPath("$.[*].subArea").value(hasItem(DEFAULT_SUB_AREA.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE.toString())))
            .andExpect(jsonPath("$.[*].partyType").value(hasItem(DEFAULT_PARTY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].refUserId").value(hasItem(DEFAULT_REF_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PCTCBasicUser.class);
        PCTCBasicUser pCTCBasicUser1 = new PCTCBasicUser();
        pCTCBasicUser1.setId(1L);
        PCTCBasicUser pCTCBasicUser2 = new PCTCBasicUser();
        pCTCBasicUser2.setId(pCTCBasicUser1.getId());
        assertThat(pCTCBasicUser1).isEqualTo(pCTCBasicUser2);
        pCTCBasicUser2.setId(2L);
        assertThat(pCTCBasicUser1).isNotEqualTo(pCTCBasicUser2);
        pCTCBasicUser1.setId(null);
        assertThat(pCTCBasicUser1).isNotEqualTo(pCTCBasicUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PCTCBasicUserDTO.class);
        PCTCBasicUserDTO pCTCBasicUserDTO1 = new PCTCBasicUserDTO();
        pCTCBasicUserDTO1.setId(1L);
        PCTCBasicUserDTO pCTCBasicUserDTO2 = new PCTCBasicUserDTO();
        assertThat(pCTCBasicUserDTO1).isNotEqualTo(pCTCBasicUserDTO2);
        pCTCBasicUserDTO2.setId(pCTCBasicUserDTO1.getId());
        assertThat(pCTCBasicUserDTO1).isEqualTo(pCTCBasicUserDTO2);
        pCTCBasicUserDTO2.setId(2L);
        assertThat(pCTCBasicUserDTO1).isNotEqualTo(pCTCBasicUserDTO2);
        pCTCBasicUserDTO1.setId(null);
        assertThat(pCTCBasicUserDTO1).isNotEqualTo(pCTCBasicUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(pCTCBasicUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(pCTCBasicUserMapper.fromId(null)).isNull();
    }
}
