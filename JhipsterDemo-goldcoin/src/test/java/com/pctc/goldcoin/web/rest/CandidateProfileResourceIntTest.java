package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.CandidateProfile;
import com.pctc.goldcoin.repository.CandidateProfileRepository;
import com.pctc.goldcoin.service.CandidateProfileService;
import com.pctc.goldcoin.repository.search.CandidateProfileSearchRepository;
import com.pctc.goldcoin.service.dto.CandidateProfileDTO;
import com.pctc.goldcoin.service.mapper.CandidateProfileMapper;
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
 * Test class for the CandidateProfileResource REST controller.
 *
 * @see CandidateProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class CandidateProfileResourceIntTest {

    private static final String DEFAULT_CANDIDATE_ID = "AAAAAAAAAA";
    private static final String UPDATED_CANDIDATE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    @Autowired
    private CandidateProfileRepository candidateProfileRepository;

    @Autowired
    private CandidateProfileMapper candidateProfileMapper;

    @Autowired
    private CandidateProfileService candidateProfileService;

    @Autowired
    private CandidateProfileSearchRepository candidateProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCandidateProfileMockMvc;

    private CandidateProfile candidateProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CandidateProfileResource candidateProfileResource = new CandidateProfileResource(candidateProfileService);
        this.restCandidateProfileMockMvc = MockMvcBuilders.standaloneSetup(candidateProfileResource)
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
    public static CandidateProfile createEntity(EntityManager em) {
        CandidateProfile candidateProfile = new CandidateProfile()
            .candidateId(DEFAULT_CANDIDATE_ID)
            .userId(DEFAULT_USER_ID);
        return candidateProfile;
    }

    @Before
    public void initTest() {
        candidateProfileSearchRepository.deleteAll();
        candidateProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createCandidateProfile() throws Exception {
        int databaseSizeBeforeCreate = candidateProfileRepository.findAll().size();

        // Create the CandidateProfile
        CandidateProfileDTO candidateProfileDTO = candidateProfileMapper.toDto(candidateProfile);
        restCandidateProfileMockMvc.perform(post("/api/candidate-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the CandidateProfile in the database
        List<CandidateProfile> candidateProfileList = candidateProfileRepository.findAll();
        assertThat(candidateProfileList).hasSize(databaseSizeBeforeCreate + 1);
        CandidateProfile testCandidateProfile = candidateProfileList.get(candidateProfileList.size() - 1);
        assertThat(testCandidateProfile.getCandidateId()).isEqualTo(DEFAULT_CANDIDATE_ID);
        assertThat(testCandidateProfile.getUserId()).isEqualTo(DEFAULT_USER_ID);

        // Validate the CandidateProfile in Elasticsearch
        CandidateProfile candidateProfileEs = candidateProfileSearchRepository.findOne(testCandidateProfile.getId());
        assertThat(candidateProfileEs).isEqualToIgnoringGivenFields(testCandidateProfile);
    }

    @Test
    @Transactional
    public void createCandidateProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = candidateProfileRepository.findAll().size();

        // Create the CandidateProfile with an existing ID
        candidateProfile.setId(1L);
        CandidateProfileDTO candidateProfileDTO = candidateProfileMapper.toDto(candidateProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCandidateProfileMockMvc.perform(post("/api/candidate-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CandidateProfile in the database
        List<CandidateProfile> candidateProfileList = candidateProfileRepository.findAll();
        assertThat(candidateProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCandidateProfiles() throws Exception {
        // Initialize the database
        candidateProfileRepository.saveAndFlush(candidateProfile);

        // Get all the candidateProfileList
        restCandidateProfileMockMvc.perform(get("/api/candidate-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void getCandidateProfile() throws Exception {
        // Initialize the database
        candidateProfileRepository.saveAndFlush(candidateProfile);

        // Get the candidateProfile
        restCandidateProfileMockMvc.perform(get("/api/candidate-profiles/{id}", candidateProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(candidateProfile.getId().intValue()))
            .andExpect(jsonPath("$.candidateId").value(DEFAULT_CANDIDATE_ID.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCandidateProfile() throws Exception {
        // Get the candidateProfile
        restCandidateProfileMockMvc.perform(get("/api/candidate-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCandidateProfile() throws Exception {
        // Initialize the database
        candidateProfileRepository.saveAndFlush(candidateProfile);
        candidateProfileSearchRepository.save(candidateProfile);
        int databaseSizeBeforeUpdate = candidateProfileRepository.findAll().size();

        // Update the candidateProfile
        CandidateProfile updatedCandidateProfile = candidateProfileRepository.findOne(candidateProfile.getId());
        // Disconnect from session so that the updates on updatedCandidateProfile are not directly saved in db
        em.detach(updatedCandidateProfile);
        updatedCandidateProfile
            .candidateId(UPDATED_CANDIDATE_ID)
            .userId(UPDATED_USER_ID);
        CandidateProfileDTO candidateProfileDTO = candidateProfileMapper.toDto(updatedCandidateProfile);

        restCandidateProfileMockMvc.perform(put("/api/candidate-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateProfileDTO)))
            .andExpect(status().isOk());

        // Validate the CandidateProfile in the database
        List<CandidateProfile> candidateProfileList = candidateProfileRepository.findAll();
        assertThat(candidateProfileList).hasSize(databaseSizeBeforeUpdate);
        CandidateProfile testCandidateProfile = candidateProfileList.get(candidateProfileList.size() - 1);
        assertThat(testCandidateProfile.getCandidateId()).isEqualTo(UPDATED_CANDIDATE_ID);
        assertThat(testCandidateProfile.getUserId()).isEqualTo(UPDATED_USER_ID);

        // Validate the CandidateProfile in Elasticsearch
        CandidateProfile candidateProfileEs = candidateProfileSearchRepository.findOne(testCandidateProfile.getId());
        assertThat(candidateProfileEs).isEqualToIgnoringGivenFields(testCandidateProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingCandidateProfile() throws Exception {
        int databaseSizeBeforeUpdate = candidateProfileRepository.findAll().size();

        // Create the CandidateProfile
        CandidateProfileDTO candidateProfileDTO = candidateProfileMapper.toDto(candidateProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCandidateProfileMockMvc.perform(put("/api/candidate-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(candidateProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the CandidateProfile in the database
        List<CandidateProfile> candidateProfileList = candidateProfileRepository.findAll();
        assertThat(candidateProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCandidateProfile() throws Exception {
        // Initialize the database
        candidateProfileRepository.saveAndFlush(candidateProfile);
        candidateProfileSearchRepository.save(candidateProfile);
        int databaseSizeBeforeDelete = candidateProfileRepository.findAll().size();

        // Get the candidateProfile
        restCandidateProfileMockMvc.perform(delete("/api/candidate-profiles/{id}", candidateProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean candidateProfileExistsInEs = candidateProfileSearchRepository.exists(candidateProfile.getId());
        assertThat(candidateProfileExistsInEs).isFalse();

        // Validate the database is empty
        List<CandidateProfile> candidateProfileList = candidateProfileRepository.findAll();
        assertThat(candidateProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCandidateProfile() throws Exception {
        // Initialize the database
        candidateProfileRepository.saveAndFlush(candidateProfile);
        candidateProfileSearchRepository.save(candidateProfile);

        // Search the candidateProfile
        restCandidateProfileMockMvc.perform(get("/api/_search/candidate-profiles?query=id:" + candidateProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(candidateProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].candidateId").value(hasItem(DEFAULT_CANDIDATE_ID.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateProfile.class);
        CandidateProfile candidateProfile1 = new CandidateProfile();
        candidateProfile1.setId(1L);
        CandidateProfile candidateProfile2 = new CandidateProfile();
        candidateProfile2.setId(candidateProfile1.getId());
        assertThat(candidateProfile1).isEqualTo(candidateProfile2);
        candidateProfile2.setId(2L);
        assertThat(candidateProfile1).isNotEqualTo(candidateProfile2);
        candidateProfile1.setId(null);
        assertThat(candidateProfile1).isNotEqualTo(candidateProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CandidateProfileDTO.class);
        CandidateProfileDTO candidateProfileDTO1 = new CandidateProfileDTO();
        candidateProfileDTO1.setId(1L);
        CandidateProfileDTO candidateProfileDTO2 = new CandidateProfileDTO();
        assertThat(candidateProfileDTO1).isNotEqualTo(candidateProfileDTO2);
        candidateProfileDTO2.setId(candidateProfileDTO1.getId());
        assertThat(candidateProfileDTO1).isEqualTo(candidateProfileDTO2);
        candidateProfileDTO2.setId(2L);
        assertThat(candidateProfileDTO1).isNotEqualTo(candidateProfileDTO2);
        candidateProfileDTO1.setId(null);
        assertThat(candidateProfileDTO1).isNotEqualTo(candidateProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(candidateProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(candidateProfileMapper.fromId(null)).isNull();
    }
}
