package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.EducationalProfile;
import com.pctc.goldcoin.repository.EducationalProfileRepository;
import com.pctc.goldcoin.service.EducationalProfileService;
import com.pctc.goldcoin.repository.search.EducationalProfileSearchRepository;
import com.pctc.goldcoin.service.dto.EducationalProfileDTO;
import com.pctc.goldcoin.service.mapper.EducationalProfileMapper;
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
 * Test class for the EducationalProfileResource REST controller.
 *
 * @see EducationalProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class EducationalProfileResourceIntTest {

    private static final String DEFAULT_EDU_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_EDU_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_START_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EDU_START_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_END_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EDU_END_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_UNIVERSITY = "AAAAAAAAAA";
    private static final String UPDATED_EDU_UNIVERSITY = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_COLLAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EDU_COLLAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_EDU_LOCATION = "BBBBBBBBBB";

    @Autowired
    private EducationalProfileRepository educationalProfileRepository;

    @Autowired
    private EducationalProfileMapper educationalProfileMapper;

    @Autowired
    private EducationalProfileService educationalProfileService;

    @Autowired
    private EducationalProfileSearchRepository educationalProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEducationalProfileMockMvc;

    private EducationalProfile educationalProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EducationalProfileResource educationalProfileResource = new EducationalProfileResource(educationalProfileService);
        this.restEducationalProfileMockMvc = MockMvcBuilders.standaloneSetup(educationalProfileResource)
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
    public static EducationalProfile createEntity(EntityManager em) {
        EducationalProfile educationalProfile = new EducationalProfile()
            .eduTitle(DEFAULT_EDU_TITLE)
            .eduStartDate(DEFAULT_EDU_START_DATE)
            .eduEndDate(DEFAULT_EDU_END_DATE)
            .eduUniversity(DEFAULT_EDU_UNIVERSITY)
            .eduCollageName(DEFAULT_EDU_COLLAGE_NAME)
            .eduLocation(DEFAULT_EDU_LOCATION);
        return educationalProfile;
    }

    @Before
    public void initTest() {
        educationalProfileSearchRepository.deleteAll();
        educationalProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createEducationalProfile() throws Exception {
        int databaseSizeBeforeCreate = educationalProfileRepository.findAll().size();

        // Create the EducationalProfile
        EducationalProfileDTO educationalProfileDTO = educationalProfileMapper.toDto(educationalProfile);
        restEducationalProfileMockMvc.perform(post("/api/educational-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationalProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the EducationalProfile in the database
        List<EducationalProfile> educationalProfileList = educationalProfileRepository.findAll();
        assertThat(educationalProfileList).hasSize(databaseSizeBeforeCreate + 1);
        EducationalProfile testEducationalProfile = educationalProfileList.get(educationalProfileList.size() - 1);
        assertThat(testEducationalProfile.getEduTitle()).isEqualTo(DEFAULT_EDU_TITLE);
        assertThat(testEducationalProfile.getEduStartDate()).isEqualTo(DEFAULT_EDU_START_DATE);
        assertThat(testEducationalProfile.getEduEndDate()).isEqualTo(DEFAULT_EDU_END_DATE);
        assertThat(testEducationalProfile.getEduUniversity()).isEqualTo(DEFAULT_EDU_UNIVERSITY);
        assertThat(testEducationalProfile.getEduCollageName()).isEqualTo(DEFAULT_EDU_COLLAGE_NAME);
        assertThat(testEducationalProfile.getEduLocation()).isEqualTo(DEFAULT_EDU_LOCATION);

        // Validate the EducationalProfile in Elasticsearch
        EducationalProfile educationalProfileEs = educationalProfileSearchRepository.findOne(testEducationalProfile.getId());
        assertThat(educationalProfileEs).isEqualToIgnoringGivenFields(testEducationalProfile);
    }

    @Test
    @Transactional
    public void createEducationalProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = educationalProfileRepository.findAll().size();

        // Create the EducationalProfile with an existing ID
        educationalProfile.setId(1L);
        EducationalProfileDTO educationalProfileDTO = educationalProfileMapper.toDto(educationalProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationalProfileMockMvc.perform(post("/api/educational-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationalProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EducationalProfile in the database
        List<EducationalProfile> educationalProfileList = educationalProfileRepository.findAll();
        assertThat(educationalProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEducationalProfiles() throws Exception {
        // Initialize the database
        educationalProfileRepository.saveAndFlush(educationalProfile);

        // Get all the educationalProfileList
        restEducationalProfileMockMvc.perform(get("/api/educational-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationalProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].eduTitle").value(hasItem(DEFAULT_EDU_TITLE.toString())))
            .andExpect(jsonPath("$.[*].eduStartDate").value(hasItem(DEFAULT_EDU_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].eduEndDate").value(hasItem(DEFAULT_EDU_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].eduUniversity").value(hasItem(DEFAULT_EDU_UNIVERSITY.toString())))
            .andExpect(jsonPath("$.[*].eduCollageName").value(hasItem(DEFAULT_EDU_COLLAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].eduLocation").value(hasItem(DEFAULT_EDU_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void getEducationalProfile() throws Exception {
        // Initialize the database
        educationalProfileRepository.saveAndFlush(educationalProfile);

        // Get the educationalProfile
        restEducationalProfileMockMvc.perform(get("/api/educational-profiles/{id}", educationalProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(educationalProfile.getId().intValue()))
            .andExpect(jsonPath("$.eduTitle").value(DEFAULT_EDU_TITLE.toString()))
            .andExpect(jsonPath("$.eduStartDate").value(DEFAULT_EDU_START_DATE.toString()))
            .andExpect(jsonPath("$.eduEndDate").value(DEFAULT_EDU_END_DATE.toString()))
            .andExpect(jsonPath("$.eduUniversity").value(DEFAULT_EDU_UNIVERSITY.toString()))
            .andExpect(jsonPath("$.eduCollageName").value(DEFAULT_EDU_COLLAGE_NAME.toString()))
            .andExpect(jsonPath("$.eduLocation").value(DEFAULT_EDU_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEducationalProfile() throws Exception {
        // Get the educationalProfile
        restEducationalProfileMockMvc.perform(get("/api/educational-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEducationalProfile() throws Exception {
        // Initialize the database
        educationalProfileRepository.saveAndFlush(educationalProfile);
        educationalProfileSearchRepository.save(educationalProfile);
        int databaseSizeBeforeUpdate = educationalProfileRepository.findAll().size();

        // Update the educationalProfile
        EducationalProfile updatedEducationalProfile = educationalProfileRepository.findOne(educationalProfile.getId());
        // Disconnect from session so that the updates on updatedEducationalProfile are not directly saved in db
        em.detach(updatedEducationalProfile);
        updatedEducationalProfile
            .eduTitle(UPDATED_EDU_TITLE)
            .eduStartDate(UPDATED_EDU_START_DATE)
            .eduEndDate(UPDATED_EDU_END_DATE)
            .eduUniversity(UPDATED_EDU_UNIVERSITY)
            .eduCollageName(UPDATED_EDU_COLLAGE_NAME)
            .eduLocation(UPDATED_EDU_LOCATION);
        EducationalProfileDTO educationalProfileDTO = educationalProfileMapper.toDto(updatedEducationalProfile);

        restEducationalProfileMockMvc.perform(put("/api/educational-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationalProfileDTO)))
            .andExpect(status().isOk());

        // Validate the EducationalProfile in the database
        List<EducationalProfile> educationalProfileList = educationalProfileRepository.findAll();
        assertThat(educationalProfileList).hasSize(databaseSizeBeforeUpdate);
        EducationalProfile testEducationalProfile = educationalProfileList.get(educationalProfileList.size() - 1);
        assertThat(testEducationalProfile.getEduTitle()).isEqualTo(UPDATED_EDU_TITLE);
        assertThat(testEducationalProfile.getEduStartDate()).isEqualTo(UPDATED_EDU_START_DATE);
        assertThat(testEducationalProfile.getEduEndDate()).isEqualTo(UPDATED_EDU_END_DATE);
        assertThat(testEducationalProfile.getEduUniversity()).isEqualTo(UPDATED_EDU_UNIVERSITY);
        assertThat(testEducationalProfile.getEduCollageName()).isEqualTo(UPDATED_EDU_COLLAGE_NAME);
        assertThat(testEducationalProfile.getEduLocation()).isEqualTo(UPDATED_EDU_LOCATION);

        // Validate the EducationalProfile in Elasticsearch
        EducationalProfile educationalProfileEs = educationalProfileSearchRepository.findOne(testEducationalProfile.getId());
        assertThat(educationalProfileEs).isEqualToIgnoringGivenFields(testEducationalProfile);
    }

    @Test
    @Transactional
    public void updateNonExistingEducationalProfile() throws Exception {
        int databaseSizeBeforeUpdate = educationalProfileRepository.findAll().size();

        // Create the EducationalProfile
        EducationalProfileDTO educationalProfileDTO = educationalProfileMapper.toDto(educationalProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEducationalProfileMockMvc.perform(put("/api/educational-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(educationalProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the EducationalProfile in the database
        List<EducationalProfile> educationalProfileList = educationalProfileRepository.findAll();
        assertThat(educationalProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEducationalProfile() throws Exception {
        // Initialize the database
        educationalProfileRepository.saveAndFlush(educationalProfile);
        educationalProfileSearchRepository.save(educationalProfile);
        int databaseSizeBeforeDelete = educationalProfileRepository.findAll().size();

        // Get the educationalProfile
        restEducationalProfileMockMvc.perform(delete("/api/educational-profiles/{id}", educationalProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean educationalProfileExistsInEs = educationalProfileSearchRepository.exists(educationalProfile.getId());
        assertThat(educationalProfileExistsInEs).isFalse();

        // Validate the database is empty
        List<EducationalProfile> educationalProfileList = educationalProfileRepository.findAll();
        assertThat(educationalProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEducationalProfile() throws Exception {
        // Initialize the database
        educationalProfileRepository.saveAndFlush(educationalProfile);
        educationalProfileSearchRepository.save(educationalProfile);

        // Search the educationalProfile
        restEducationalProfileMockMvc.perform(get("/api/_search/educational-profiles?query=id:" + educationalProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(educationalProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].eduTitle").value(hasItem(DEFAULT_EDU_TITLE.toString())))
            .andExpect(jsonPath("$.[*].eduStartDate").value(hasItem(DEFAULT_EDU_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].eduEndDate").value(hasItem(DEFAULT_EDU_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].eduUniversity").value(hasItem(DEFAULT_EDU_UNIVERSITY.toString())))
            .andExpect(jsonPath("$.[*].eduCollageName").value(hasItem(DEFAULT_EDU_COLLAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].eduLocation").value(hasItem(DEFAULT_EDU_LOCATION.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationalProfile.class);
        EducationalProfile educationalProfile1 = new EducationalProfile();
        educationalProfile1.setId(1L);
        EducationalProfile educationalProfile2 = new EducationalProfile();
        educationalProfile2.setId(educationalProfile1.getId());
        assertThat(educationalProfile1).isEqualTo(educationalProfile2);
        educationalProfile2.setId(2L);
        assertThat(educationalProfile1).isNotEqualTo(educationalProfile2);
        educationalProfile1.setId(null);
        assertThat(educationalProfile1).isNotEqualTo(educationalProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EducationalProfileDTO.class);
        EducationalProfileDTO educationalProfileDTO1 = new EducationalProfileDTO();
        educationalProfileDTO1.setId(1L);
        EducationalProfileDTO educationalProfileDTO2 = new EducationalProfileDTO();
        assertThat(educationalProfileDTO1).isNotEqualTo(educationalProfileDTO2);
        educationalProfileDTO2.setId(educationalProfileDTO1.getId());
        assertThat(educationalProfileDTO1).isEqualTo(educationalProfileDTO2);
        educationalProfileDTO2.setId(2L);
        assertThat(educationalProfileDTO1).isNotEqualTo(educationalProfileDTO2);
        educationalProfileDTO1.setId(null);
        assertThat(educationalProfileDTO1).isNotEqualTo(educationalProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(educationalProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(educationalProfileMapper.fromId(null)).isNull();
    }
}
