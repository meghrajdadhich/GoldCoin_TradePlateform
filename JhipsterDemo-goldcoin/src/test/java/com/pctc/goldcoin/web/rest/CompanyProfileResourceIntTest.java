package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.CompanyProfile;
import com.pctc.goldcoin.repository.CompanyProfileRepository;
import com.pctc.goldcoin.service.CompanyProfileService;
import com.pctc.goldcoin.repository.search.CompanyProfileSearchRepository;
import com.pctc.goldcoin.service.dto.CompanyProfileDTO;
import com.pctc.goldcoin.service.mapper.CompanyProfileMapper;
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
 * Test class for the CompanyProfileResource REST controller.
 *
 * @see CompanyProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class CompanyProfileResourceIntTest {

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_CONTACT_PERSON_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_CONTACT_PERSON_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_UPDATE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_UPDATE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_LOCATION = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_STATUS = "BBBBBBBBBB";

    @Autowired
    private CompanyProfileRepository companyProfileRepository;

    @Autowired
    private CompanyProfileMapper companyProfileMapper;

    @Autowired
    private CompanyProfileService companyProfileService;

    @Autowired
    private CompanyProfileSearchRepository companyProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCompanyProfileMockMvc;

    private CompanyProfile companyProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompanyProfileResource companyProfileResource = new CompanyProfileResource(companyProfileService);
        this.restCompanyProfileMockMvc = MockMvcBuilders.standaloneSetup(companyProfileResource)
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
    public static CompanyProfile createEntity(EntityManager em) {
        CompanyProfile companyProfile = new CompanyProfile()
            .clientName(DEFAULT_CLIENT_NAME)
            .clientContactPersonName(DEFAULT_CLIENT_CONTACT_PERSON_NAME)
            .clientUpdateDate(DEFAULT_CLIENT_UPDATE_DATE)
            .clientLocation(DEFAULT_CLIENT_LOCATION)
            .clientStatus(DEFAULT_CLIENT_STATUS);
        return companyProfile;
    }

    @Before
    public void initTest() {
        companyProfileSearchRepository.deleteAll();
        companyProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompanyProfile() throws Exception {
        int databaseSizeBeforeCreate = companyProfileRepository.findAll().size();

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);
        restCompanyProfileMockMvc.perform(post("/api/company-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testCompanyProfile.getClientContactPersonName()).isEqualTo(DEFAULT_CLIENT_CONTACT_PERSON_NAME);
        assertThat(testCompanyProfile.getClientUpdateDate()).isEqualTo(DEFAULT_CLIENT_UPDATE_DATE);
        assertThat(testCompanyProfile.getClientLocation()).isEqualTo(DEFAULT_CLIENT_LOCATION);
        assertThat(testCompanyProfile.getClientStatus()).isEqualTo(DEFAULT_CLIENT_STATUS);

        // Validate the CompanyProfile in Elasticsearch
        CompanyProfile companyProfileEs = companyProfileSearchRepository.findOne(testCompanyProfile.getId());
        assertThat(companyProfileEs).isEqualToIgnoringGivenFields(testCompanyProfile);
    }

    @Test
    @Transactional
    public void createCompanyProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = companyProfileRepository.findAll().size();

        // Create the CompanyProfile with an existing ID
        companyProfile.setId(1L);
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyProfileMockMvc.perform(post("/api/company-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCompanyProfiles() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get all the companyProfileList
        restCompanyProfileMockMvc.perform(get("/api/company-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientContactPersonName").value(hasItem(DEFAULT_CLIENT_CONTACT_PERSON_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientUpdateDate").value(hasItem(DEFAULT_CLIENT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].clientLocation").value(hasItem(DEFAULT_CLIENT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].clientStatus").value(hasItem(DEFAULT_CLIENT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);

        // Get the companyProfile
        restCompanyProfileMockMvc.perform(get("/api/company-profiles/{id}", companyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(companyProfile.getId().intValue()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME.toString()))
            .andExpect(jsonPath("$.clientContactPersonName").value(DEFAULT_CLIENT_CONTACT_PERSON_NAME.toString()))
            .andExpect(jsonPath("$.clientUpdateDate").value(DEFAULT_CLIENT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.clientLocation").value(DEFAULT_CLIENT_LOCATION.toString()))
            .andExpect(jsonPath("$.clientStatus").value(DEFAULT_CLIENT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCompanyProfile() throws Exception {
        // Get the companyProfile
        restCompanyProfileMockMvc.perform(get("/api/company-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);
        companyProfileSearchRepository.save(companyProfile);
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Update the companyProfile
        CompanyProfile updatedCompanyProfile = companyProfileRepository.findOne(companyProfile.getId());
        // Disconnect from session so that the updates on updatedCompanyProfile are not directly saved in db
        em.detach(updatedCompanyProfile);
        updatedCompanyProfile
            .clientName(UPDATED_CLIENT_NAME)
            .clientContactPersonName(UPDATED_CLIENT_CONTACT_PERSON_NAME)
            .clientUpdateDate(UPDATED_CLIENT_UPDATE_DATE)
            .clientLocation(UPDATED_CLIENT_LOCATION)
            .clientStatus(UPDATED_CLIENT_STATUS);
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(updatedCompanyProfile);

        restCompanyProfileMockMvc.perform(put("/api/company-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO)))
            .andExpect(status().isOk());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate);
        CompanyProfile testCompanyProfile = companyProfileList.get(companyProfileList.size() - 1);
        assertThat(testCompanyProfile.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testCompanyProfile.getClientContactPersonName()).isEqualTo(UPDATED_CLIENT_CONTACT_PERSON_NAME);
        assertThat(testCompanyProfile.getClientUpdateDate()).isEqualTo(UPDATED_CLIENT_UPDATE_DATE);
        assertThat(testCompanyProfile.getClientLocation()).isEqualTo(UPDATED_CLIENT_LOCATION);
        assertThat(testCompanyProfile.getClientStatus()).isEqualTo(UPDATED_CLIENT_STATUS);

        // Validate the CompanyProfile in Elasticsearch
        CompanyProfile companyProfileEs = companyProfileSearchRepository.findOne(testCompanyProfile.getId());
        assertThat(companyProfileEs).isEqualToIgnoringGivenFields(testCompanyProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingCompanyProfile() throws Exception {
        int databaseSizeBeforeUpdate = companyProfileRepository.findAll().size();

        // Create the CompanyProfile
        CompanyProfileDTO companyProfileDTO = companyProfileMapper.toDto(companyProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCompanyProfileMockMvc.perform(put("/api/company-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(companyProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the CompanyProfile in the database
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);
        companyProfileSearchRepository.save(companyProfile);
        int databaseSizeBeforeDelete = companyProfileRepository.findAll().size();

        // Get the companyProfile
        restCompanyProfileMockMvc.perform(delete("/api/company-profiles/{id}", companyProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean companyProfileExistsInEs = companyProfileSearchRepository.exists(companyProfile.getId());
        assertThat(companyProfileExistsInEs).isFalse();

        // Validate the database is empty
        List<CompanyProfile> companyProfileList = companyProfileRepository.findAll();
        assertThat(companyProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCompanyProfile() throws Exception {
        // Initialize the database
        companyProfileRepository.saveAndFlush(companyProfile);
        companyProfileSearchRepository.save(companyProfile);

        // Search the companyProfile
        restCompanyProfileMockMvc.perform(get("/api/_search/company-profiles?query=id:" + companyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientContactPersonName").value(hasItem(DEFAULT_CLIENT_CONTACT_PERSON_NAME.toString())))
            .andExpect(jsonPath("$.[*].clientUpdateDate").value(hasItem(DEFAULT_CLIENT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].clientLocation").value(hasItem(DEFAULT_CLIENT_LOCATION.toString())))
            .andExpect(jsonPath("$.[*].clientStatus").value(hasItem(DEFAULT_CLIENT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyProfile.class);
        CompanyProfile companyProfile1 = new CompanyProfile();
        companyProfile1.setId(1L);
        CompanyProfile companyProfile2 = new CompanyProfile();
        companyProfile2.setId(companyProfile1.getId());
        assertThat(companyProfile1).isEqualTo(companyProfile2);
        companyProfile2.setId(2L);
        assertThat(companyProfile1).isNotEqualTo(companyProfile2);
        companyProfile1.setId(null);
        assertThat(companyProfile1).isNotEqualTo(companyProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyProfileDTO.class);
        CompanyProfileDTO companyProfileDTO1 = new CompanyProfileDTO();
        companyProfileDTO1.setId(1L);
        CompanyProfileDTO companyProfileDTO2 = new CompanyProfileDTO();
        assertThat(companyProfileDTO1).isNotEqualTo(companyProfileDTO2);
        companyProfileDTO2.setId(companyProfileDTO1.getId());
        assertThat(companyProfileDTO1).isEqualTo(companyProfileDTO2);
        companyProfileDTO2.setId(2L);
        assertThat(companyProfileDTO1).isNotEqualTo(companyProfileDTO2);
        companyProfileDTO1.setId(null);
        assertThat(companyProfileDTO1).isNotEqualTo(companyProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(companyProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(companyProfileMapper.fromId(null)).isNull();
    }
}
