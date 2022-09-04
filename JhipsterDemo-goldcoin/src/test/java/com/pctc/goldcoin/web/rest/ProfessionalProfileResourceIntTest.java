package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.ProfessionalProfile;
import com.pctc.goldcoin.repository.ProfessionalProfileRepository;
import com.pctc.goldcoin.service.ProfessionalProfileService;
import com.pctc.goldcoin.repository.search.ProfessionalProfileSearchRepository;
import com.pctc.goldcoin.service.dto.ProfessionalProfileDTO;
import com.pctc.goldcoin.service.mapper.ProfessionalProfileMapper;
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
 * Test class for the ProfessionalProfileResource REST controller.
 *
 * @see ProfessionalProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class ProfessionalProfileResourceIntTest {

    private static final String DEFAULT_PRO_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_PRO_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRO_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PRO_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRO_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PRO_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PRO_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_PRO_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_PRO_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_PRO_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ProfessionalProfileRepository professionalProfileRepository;

    @Autowired
    private ProfessionalProfileMapper professionalProfileMapper;

    @Autowired
    private ProfessionalProfileService professionalProfileService;

    @Autowired
    private ProfessionalProfileSearchRepository professionalProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProfessionalProfileMockMvc;

    private ProfessionalProfile professionalProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfessionalProfileResource professionalProfileResource = new ProfessionalProfileResource(professionalProfileService);
        this.restProfessionalProfileMockMvc = MockMvcBuilders.standaloneSetup(professionalProfileResource)
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
    public static ProfessionalProfile createEntity(EntityManager em) {
        ProfessionalProfile professionalProfile = new ProfessionalProfile()
            .proTitle(DEFAULT_PRO_TITLE)
            .proCompanyName(DEFAULT_PRO_COMPANY_NAME)
            .proStartDate(DEFAULT_PRO_START_DATE)
            .proEndDate(DEFAULT_PRO_END_DATE)
            .proLocation(DEFAULT_PRO_LOCATION);
        return professionalProfile;
    }

    @Before
    public void initTest() {
        professionalProfileSearchRepository.deleteAll();
        professionalProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfessionalProfile() throws Exception {
        int databaseSizeBeforeCreate = professionalProfileRepository.findAll().size();

        // Create the ProfessionalProfile
        ProfessionalProfileDTO professionalProfileDTO = professionalProfileMapper.toDto(professionalProfile);
        restProfessionalProfileMockMvc.perform(post("/api/professional-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalProfile in the database
        List<ProfessionalProfile> professionalProfileList = professionalProfileRepository.findAll();
        assertThat(professionalProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ProfessionalProfile testProfessionalProfile = professionalProfileList.get(professionalProfileList.size() - 1);
        assertThat(testProfessionalProfile.getProTitle()).isEqualTo(DEFAULT_PRO_TITLE);
        assertThat(testProfessionalProfile.getProCompanyName()).isEqualTo(DEFAULT_PRO_COMPANY_NAME);
        assertThat(testProfessionalProfile.getProStartDate()).isEqualTo(DEFAULT_PRO_START_DATE);
        assertThat(testProfessionalProfile.getProEndDate()).isEqualTo(DEFAULT_PRO_END_DATE);
        assertThat(testProfessionalProfile.getProLocation()).isEqualTo(DEFAULT_PRO_LOCATION);

        // Validate the ProfessionalProfile in Elasticsearch
        ProfessionalProfile professionalProfileEs = professionalProfileSearchRepository.findOne(testProfessionalProfile.getId());
        assertThat(professionalProfileEs).isEqualToIgnoringGivenFields(testProfessionalProfile);
    }

    @Test
    @Transactional
    public void createProfessionalProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = professionalProfileRepository.findAll().size();

        // Create the ProfessionalProfile with an existing ID
        professionalProfile.setId(1L);
        ProfessionalProfileDTO professionalProfileDTO = professionalProfileMapper.toDto(professionalProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessionalProfileMockMvc.perform(post("/api/professional-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProfessionalProfile in the database
        List<ProfessionalProfile> professionalProfileList = professionalProfileRepository.findAll();
        assertThat(professionalProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProfessionalProfiles() throws Exception {
        // Initialize the database
        professionalProfileRepository.saveAndFlush(professionalProfile);

        // Get all the professionalProfileList
        restProfessionalProfileMockMvc.perform(get("/api/professional-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].proTitle").value(hasItem(DEFAULT_PRO_TITLE.toString())))
            .andExpect(jsonPath("$.[*].proCompanyName").value(hasItem(DEFAULT_PRO_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].proStartDate").value(hasItem(DEFAULT_PRO_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].proEndDate").value(hasItem(DEFAULT_PRO_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].proLocation").value(hasItem(DEFAULT_PRO_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getProfessionalProfile() throws Exception {
        // Initialize the database
        professionalProfileRepository.saveAndFlush(professionalProfile);

        // Get the professionalProfile
        restProfessionalProfileMockMvc.perform(get("/api/professional-profiles/{id}", professionalProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(professionalProfile.getId().intValue()))
            .andExpect(jsonPath("$.proTitle").value(DEFAULT_PRO_TITLE.toString()))
            .andExpect(jsonPath("$.proCompanyName").value(DEFAULT_PRO_COMPANY_NAME.toString()))
            .andExpect(jsonPath("$.proStartDate").value(DEFAULT_PRO_START_DATE.toString()))
            .andExpect(jsonPath("$.proEndDate").value(DEFAULT_PRO_END_DATE.toString()))
            .andExpect(jsonPath("$.proLocation").value(DEFAULT_PRO_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfessionalProfile() throws Exception {
        // Get the professionalProfile
        restProfessionalProfileMockMvc.perform(get("/api/professional-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfessionalProfile() throws Exception {
        // Initialize the database
        professionalProfileRepository.saveAndFlush(professionalProfile);
        professionalProfileSearchRepository.save(professionalProfile);
        int databaseSizeBeforeUpdate = professionalProfileRepository.findAll().size();

        // Update the professionalProfile
        ProfessionalProfile updatedProfessionalProfile = professionalProfileRepository.findOne(professionalProfile.getId());
        // Disconnect from session so that the updates on updatedProfessionalProfile are not directly saved in db
        em.detach(updatedProfessionalProfile);
        updatedProfessionalProfile
            .proTitle(UPDATED_PRO_TITLE)
            .proCompanyName(UPDATED_PRO_COMPANY_NAME)
            .proStartDate(UPDATED_PRO_START_DATE)
            .proEndDate(UPDATED_PRO_END_DATE)
            .proLocation(UPDATED_PRO_LOCATION);
        ProfessionalProfileDTO professionalProfileDTO = professionalProfileMapper.toDto(updatedProfessionalProfile);

        restProfessionalProfileMockMvc.perform(put("/api/professional-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileDTO)))
            .andExpect(status().isOk());

        // Validate the ProfessionalProfile in the database
        List<ProfessionalProfile> professionalProfileList = professionalProfileRepository.findAll();
        assertThat(professionalProfileList).hasSize(databaseSizeBeforeUpdate);
        ProfessionalProfile testProfessionalProfile = professionalProfileList.get(professionalProfileList.size() - 1);
        assertThat(testProfessionalProfile.getProTitle()).isEqualTo(UPDATED_PRO_TITLE);
        assertThat(testProfessionalProfile.getProCompanyName()).isEqualTo(UPDATED_PRO_COMPANY_NAME);
        assertThat(testProfessionalProfile.getProStartDate()).isEqualTo(UPDATED_PRO_START_DATE);
        assertThat(testProfessionalProfile.getProEndDate()).isEqualTo(UPDATED_PRO_END_DATE);
        assertThat(testProfessionalProfile.getProLocation()).isEqualTo(UPDATED_PRO_LOCATION);

        // Validate the ProfessionalProfile in Elasticsearch
        ProfessionalProfile professionalProfileEs = professionalProfileSearchRepository.findOne(testProfessionalProfile.getId());
        assertThat(professionalProfileEs).isEqualToIgnoringGivenFields(testProfessionalProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingProfessionalProfile() throws Exception {
        int databaseSizeBeforeUpdate = professionalProfileRepository.findAll().size();

        // Create the ProfessionalProfile
        ProfessionalProfileDTO professionalProfileDTO = professionalProfileMapper.toDto(professionalProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProfessionalProfileMockMvc.perform(put("/api/professional-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(professionalProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the ProfessionalProfile in the database
        List<ProfessionalProfile> professionalProfileList = professionalProfileRepository.findAll();
        assertThat(professionalProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProfessionalProfile() throws Exception {
        // Initialize the database
        professionalProfileRepository.saveAndFlush(professionalProfile);
        professionalProfileSearchRepository.save(professionalProfile);
        int databaseSizeBeforeDelete = professionalProfileRepository.findAll().size();

        // Get the professionalProfile
        restProfessionalProfileMockMvc.perform(delete("/api/professional-profiles/{id}", professionalProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean professionalProfileExistsInEs = professionalProfileSearchRepository.exists(professionalProfile.getId());
        assertThat(professionalProfileExistsInEs).isFalse();

        // Validate the database is empty
        List<ProfessionalProfile> professionalProfileList = professionalProfileRepository.findAll();
        assertThat(professionalProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchProfessionalProfile() throws Exception {
        // Initialize the database
        professionalProfileRepository.saveAndFlush(professionalProfile);
        professionalProfileSearchRepository.save(professionalProfile);

        // Search the professionalProfile
        restProfessionalProfileMockMvc.perform(get("/api/_search/professional-profiles?query=id:" + professionalProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professionalProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].proTitle").value(hasItem(DEFAULT_PRO_TITLE.toString())))
            .andExpect(jsonPath("$.[*].proCompanyName").value(hasItem(DEFAULT_PRO_COMPANY_NAME.toString())))
            .andExpect(jsonPath("$.[*].proStartDate").value(hasItem(DEFAULT_PRO_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].proEndDate").value(hasItem(DEFAULT_PRO_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].proLocation").value(hasItem(DEFAULT_PRO_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalProfile.class);
        ProfessionalProfile professionalProfile1 = new ProfessionalProfile();
        professionalProfile1.setId(1L);
        ProfessionalProfile professionalProfile2 = new ProfessionalProfile();
        professionalProfile2.setId(professionalProfile1.getId());
        assertThat(professionalProfile1).isEqualTo(professionalProfile2);
        professionalProfile2.setId(2L);
        assertThat(professionalProfile1).isNotEqualTo(professionalProfile2);
        professionalProfile1.setId(null);
        assertThat(professionalProfile1).isNotEqualTo(professionalProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfessionalProfileDTO.class);
        ProfessionalProfileDTO professionalProfileDTO1 = new ProfessionalProfileDTO();
        professionalProfileDTO1.setId(1L);
        ProfessionalProfileDTO professionalProfileDTO2 = new ProfessionalProfileDTO();
        assertThat(professionalProfileDTO1).isNotEqualTo(professionalProfileDTO2);
        professionalProfileDTO2.setId(professionalProfileDTO1.getId());
        assertThat(professionalProfileDTO1).isEqualTo(professionalProfileDTO2);
        professionalProfileDTO2.setId(2L);
        assertThat(professionalProfileDTO1).isNotEqualTo(professionalProfileDTO2);
        professionalProfileDTO1.setId(null);
        assertThat(professionalProfileDTO1).isNotEqualTo(professionalProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(professionalProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(professionalProfileMapper.fromId(null)).isNull();
    }
}
