package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.ConsultancyProfile;
import com.pctc.goldcoin.repository.ConsultancyProfileRepository;
import com.pctc.goldcoin.service.ConsultancyProfileService;
import com.pctc.goldcoin.repository.search.ConsultancyProfileSearchRepository;
import com.pctc.goldcoin.service.dto.ConsultancyProfileDTO;
import com.pctc.goldcoin.service.mapper.ConsultancyProfileMapper;
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
 * Test class for the ConsultancyProfileResource REST controller.
 *
 * @see ConsultancyProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class ConsultancyProfileResourceIntTest {

    private static final String DEFAULT_CONSULTANCY_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANCY_TITLE = "BBBBBBBBBB";

    @Autowired
    private ConsultancyProfileRepository consultancyProfileRepository;

    @Autowired
    private ConsultancyProfileMapper consultancyProfileMapper;

    @Autowired
    private ConsultancyProfileService consultancyProfileService;

    @Autowired
    private ConsultancyProfileSearchRepository consultancyProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultancyProfileMockMvc;

    private ConsultancyProfile consultancyProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultancyProfileResource consultancyProfileResource = new ConsultancyProfileResource(consultancyProfileService);
        this.restConsultancyProfileMockMvc = MockMvcBuilders.standaloneSetup(consultancyProfileResource)
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
    public static ConsultancyProfile createEntity(EntityManager em) {
        ConsultancyProfile consultancyProfile = new ConsultancyProfile()
            .consultancyTitle(DEFAULT_CONSULTANCY_TITLE);
        return consultancyProfile;
    }

    @Before
    public void initTest() {
        consultancyProfileSearchRepository.deleteAll();
        consultancyProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultancyProfile() throws Exception {
        int databaseSizeBeforeCreate = consultancyProfileRepository.findAll().size();

        // Create the ConsultancyProfile
        ConsultancyProfileDTO consultancyProfileDTO = consultancyProfileMapper.toDto(consultancyProfile);
        restConsultancyProfileMockMvc.perform(post("/api/consultancy-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultancyProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultancyProfile in the database
        List<ConsultancyProfile> consultancyProfileList = consultancyProfileRepository.findAll();
        assertThat(consultancyProfileList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultancyProfile testConsultancyProfile = consultancyProfileList.get(consultancyProfileList.size() - 1);
        assertThat(testConsultancyProfile.getConsultancyTitle()).isEqualTo(DEFAULT_CONSULTANCY_TITLE);

        // Validate the ConsultancyProfile in Elasticsearch
        ConsultancyProfile consultancyProfileEs = consultancyProfileSearchRepository.findOne(testConsultancyProfile.getId());
        assertThat(consultancyProfileEs).isEqualToIgnoringGivenFields(testConsultancyProfile);
    }

    @Test
    @Transactional
    public void createConsultancyProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultancyProfileRepository.findAll().size();

        // Create the ConsultancyProfile with an existing ID
        consultancyProfile.setId(1L);
        ConsultancyProfileDTO consultancyProfileDTO = consultancyProfileMapper.toDto(consultancyProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultancyProfileMockMvc.perform(post("/api/consultancy-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultancyProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultancyProfile in the database
        List<ConsultancyProfile> consultancyProfileList = consultancyProfileRepository.findAll();
        assertThat(consultancyProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultancyProfiles() throws Exception {
        // Initialize the database
        consultancyProfileRepository.saveAndFlush(consultancyProfile);

        // Get all the consultancyProfileList
        restConsultancyProfileMockMvc.perform(get("/api/consultancy-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultancyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultancyTitle").value(hasItem(DEFAULT_CONSULTANCY_TITLE.toString())));
    }

    @Test
    @Transactional
    public void getConsultancyProfile() throws Exception {
        // Initialize the database
        consultancyProfileRepository.saveAndFlush(consultancyProfile);

        // Get the consultancyProfile
        restConsultancyProfileMockMvc.perform(get("/api/consultancy-profiles/{id}", consultancyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultancyProfile.getId().intValue()))
            .andExpect(jsonPath("$.consultancyTitle").value(DEFAULT_CONSULTANCY_TITLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultancyProfile() throws Exception {
        // Get the consultancyProfile
        restConsultancyProfileMockMvc.perform(get("/api/consultancy-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultancyProfile() throws Exception {
        // Initialize the database
        consultancyProfileRepository.saveAndFlush(consultancyProfile);
        consultancyProfileSearchRepository.save(consultancyProfile);
        int databaseSizeBeforeUpdate = consultancyProfileRepository.findAll().size();

        // Update the consultancyProfile
        ConsultancyProfile updatedConsultancyProfile = consultancyProfileRepository.findOne(consultancyProfile.getId());
        // Disconnect from session so that the updates on updatedConsultancyProfile are not directly saved in db
        em.detach(updatedConsultancyProfile);
        updatedConsultancyProfile
            .consultancyTitle(UPDATED_CONSULTANCY_TITLE);
        ConsultancyProfileDTO consultancyProfileDTO = consultancyProfileMapper.toDto(updatedConsultancyProfile);

        restConsultancyProfileMockMvc.perform(put("/api/consultancy-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultancyProfileDTO)))
            .andExpect(status().isOk());

        // Validate the ConsultancyProfile in the database
        List<ConsultancyProfile> consultancyProfileList = consultancyProfileRepository.findAll();
        assertThat(consultancyProfileList).hasSize(databaseSizeBeforeUpdate);
        ConsultancyProfile testConsultancyProfile = consultancyProfileList.get(consultancyProfileList.size() - 1);
        assertThat(testConsultancyProfile.getConsultancyTitle()).isEqualTo(UPDATED_CONSULTANCY_TITLE);

        // Validate the ConsultancyProfile in Elasticsearch
        ConsultancyProfile consultancyProfileEs = consultancyProfileSearchRepository.findOne(testConsultancyProfile.getId());
        assertThat(consultancyProfileEs).isEqualToIgnoringGivenFields(testConsultancyProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultancyProfile() throws Exception {
        int databaseSizeBeforeUpdate = consultancyProfileRepository.findAll().size();

        // Create the ConsultancyProfile
        ConsultancyProfileDTO consultancyProfileDTO = consultancyProfileMapper.toDto(consultancyProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultancyProfileMockMvc.perform(put("/api/consultancy-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultancyProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultancyProfile in the database
        List<ConsultancyProfile> consultancyProfileList = consultancyProfileRepository.findAll();
        assertThat(consultancyProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultancyProfile() throws Exception {
        // Initialize the database
        consultancyProfileRepository.saveAndFlush(consultancyProfile);
        consultancyProfileSearchRepository.save(consultancyProfile);
        int databaseSizeBeforeDelete = consultancyProfileRepository.findAll().size();

        // Get the consultancyProfile
        restConsultancyProfileMockMvc.perform(delete("/api/consultancy-profiles/{id}", consultancyProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean consultancyProfileExistsInEs = consultancyProfileSearchRepository.exists(consultancyProfile.getId());
        assertThat(consultancyProfileExistsInEs).isFalse();

        // Validate the database is empty
        List<ConsultancyProfile> consultancyProfileList = consultancyProfileRepository.findAll();
        assertThat(consultancyProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchConsultancyProfile() throws Exception {
        // Initialize the database
        consultancyProfileRepository.saveAndFlush(consultancyProfile);
        consultancyProfileSearchRepository.save(consultancyProfile);

        // Search the consultancyProfile
        restConsultancyProfileMockMvc.perform(get("/api/_search/consultancy-profiles?query=id:" + consultancyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultancyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultancyTitle").value(hasItem(DEFAULT_CONSULTANCY_TITLE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultancyProfile.class);
        ConsultancyProfile consultancyProfile1 = new ConsultancyProfile();
        consultancyProfile1.setId(1L);
        ConsultancyProfile consultancyProfile2 = new ConsultancyProfile();
        consultancyProfile2.setId(consultancyProfile1.getId());
        assertThat(consultancyProfile1).isEqualTo(consultancyProfile2);
        consultancyProfile2.setId(2L);
        assertThat(consultancyProfile1).isNotEqualTo(consultancyProfile2);
        consultancyProfile1.setId(null);
        assertThat(consultancyProfile1).isNotEqualTo(consultancyProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultancyProfileDTO.class);
        ConsultancyProfileDTO consultancyProfileDTO1 = new ConsultancyProfileDTO();
        consultancyProfileDTO1.setId(1L);
        ConsultancyProfileDTO consultancyProfileDTO2 = new ConsultancyProfileDTO();
        assertThat(consultancyProfileDTO1).isNotEqualTo(consultancyProfileDTO2);
        consultancyProfileDTO2.setId(consultancyProfileDTO1.getId());
        assertThat(consultancyProfileDTO1).isEqualTo(consultancyProfileDTO2);
        consultancyProfileDTO2.setId(2L);
        assertThat(consultancyProfileDTO1).isNotEqualTo(consultancyProfileDTO2);
        consultancyProfileDTO1.setId(null);
        assertThat(consultancyProfileDTO1).isNotEqualTo(consultancyProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultancyProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultancyProfileMapper.fromId(null)).isNull();
    }
}
