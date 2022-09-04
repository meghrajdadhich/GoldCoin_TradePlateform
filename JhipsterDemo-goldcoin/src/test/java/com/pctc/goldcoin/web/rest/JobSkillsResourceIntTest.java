package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.JobSkills;
import com.pctc.goldcoin.repository.JobSkillsRepository;
import com.pctc.goldcoin.service.JobSkillsService;
import com.pctc.goldcoin.repository.search.JobSkillsSearchRepository;
import com.pctc.goldcoin.service.dto.JobSkillsDTO;
import com.pctc.goldcoin.service.mapper.JobSkillsMapper;
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
 * Test class for the JobSkillsResource REST controller.
 *
 * @see JobSkillsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class JobSkillsResourceIntTest {

    private static final String DEFAULT_JOBSKILL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_JOBSKILL_NAME = "BBBBBBBBBB";

    @Autowired
    private JobSkillsRepository jobSkillsRepository;

    @Autowired
    private JobSkillsMapper jobSkillsMapper;

    @Autowired
    private JobSkillsService jobSkillsService;

    @Autowired
    private JobSkillsSearchRepository jobSkillsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restJobSkillsMockMvc;

    private JobSkills jobSkills;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final JobSkillsResource jobSkillsResource = new JobSkillsResource(jobSkillsService);
        this.restJobSkillsMockMvc = MockMvcBuilders.standaloneSetup(jobSkillsResource)
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
    public static JobSkills createEntity(EntityManager em) {
        JobSkills jobSkills = new JobSkills()
            .jobskillName(DEFAULT_JOBSKILL_NAME);
        return jobSkills;
    }

    @Before
    public void initTest() {
        jobSkillsSearchRepository.deleteAll();
        jobSkills = createEntity(em);
    }

    @Test
    @Transactional
    public void createJobSkills() throws Exception {
        int databaseSizeBeforeCreate = jobSkillsRepository.findAll().size();

        // Create the JobSkills
        JobSkillsDTO jobSkillsDTO = jobSkillsMapper.toDto(jobSkills);
        restJobSkillsMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkillsDTO)))
            .andExpect(status().isCreated());

        // Validate the JobSkills in the database
        List<JobSkills> jobSkillsList = jobSkillsRepository.findAll();
        assertThat(jobSkillsList).hasSize(databaseSizeBeforeCreate + 1);
        JobSkills testJobSkills = jobSkillsList.get(jobSkillsList.size() - 1);
        assertThat(testJobSkills.getJobskillName()).isEqualTo(DEFAULT_JOBSKILL_NAME);

        // Validate the JobSkills in Elasticsearch
        JobSkills jobSkillsEs = jobSkillsSearchRepository.findOne(testJobSkills.getId());
        assertThat(jobSkillsEs).isEqualToIgnoringGivenFields(testJobSkills);
    }

    @Test
    @Transactional
    public void createJobSkillsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = jobSkillsRepository.findAll().size();

        // Create the JobSkills with an existing ID
        jobSkills.setId(1L);
        JobSkillsDTO jobSkillsDTO = jobSkillsMapper.toDto(jobSkills);

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobSkillsMockMvc.perform(post("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkillsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the JobSkills in the database
        List<JobSkills> jobSkillsList = jobSkillsRepository.findAll();
        assertThat(jobSkillsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllJobSkills() throws Exception {
        // Initialize the database
        jobSkillsRepository.saveAndFlush(jobSkills);

        // Get all the jobSkillsList
        restJobSkillsMockMvc.perform(get("/api/job-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSkills.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobskillName").value(hasItem(DEFAULT_JOBSKILL_NAME.toString())));
    }

    @Test
    @Transactional
    public void getJobSkills() throws Exception {
        // Initialize the database
        jobSkillsRepository.saveAndFlush(jobSkills);

        // Get the jobSkills
        restJobSkillsMockMvc.perform(get("/api/job-skills/{id}", jobSkills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(jobSkills.getId().intValue()))
            .andExpect(jsonPath("$.jobskillName").value(DEFAULT_JOBSKILL_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingJobSkills() throws Exception {
        // Get the jobSkills
        restJobSkillsMockMvc.perform(get("/api/job-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateJobSkills() throws Exception {
        // Initialize the database
        jobSkillsRepository.saveAndFlush(jobSkills);
        jobSkillsSearchRepository.save(jobSkills);
        int databaseSizeBeforeUpdate = jobSkillsRepository.findAll().size();

        // Update the jobSkills
        JobSkills updatedJobSkills = jobSkillsRepository.findOne(jobSkills.getId());
        // Disconnect from session so that the updates on updatedJobSkills are not directly saved in db
        em.detach(updatedJobSkills);
        updatedJobSkills
            .jobskillName(UPDATED_JOBSKILL_NAME);
        JobSkillsDTO jobSkillsDTO = jobSkillsMapper.toDto(updatedJobSkills);

        restJobSkillsMockMvc.perform(put("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkillsDTO)))
            .andExpect(status().isOk());

        // Validate the JobSkills in the database
        List<JobSkills> jobSkillsList = jobSkillsRepository.findAll();
        assertThat(jobSkillsList).hasSize(databaseSizeBeforeUpdate);
        JobSkills testJobSkills = jobSkillsList.get(jobSkillsList.size() - 1);
        assertThat(testJobSkills.getJobskillName()).isEqualTo(UPDATED_JOBSKILL_NAME);

        // Validate the JobSkills in Elasticsearch
        JobSkills jobSkillsEs = jobSkillsSearchRepository.findOne(testJobSkills.getId());
        assertThat(jobSkillsEs).isEqualToIgnoringGivenFields(testJobSkills);
    }

    @Test
    @Transactional
    public void updateNonExistingJobSkills() throws Exception {
        int databaseSizeBeforeUpdate = jobSkillsRepository.findAll().size();

        // Create the JobSkills
        JobSkillsDTO jobSkillsDTO = jobSkillsMapper.toDto(jobSkills);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restJobSkillsMockMvc.perform(put("/api/job-skills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(jobSkillsDTO)))
            .andExpect(status().isCreated());

        // Validate the JobSkills in the database
        List<JobSkills> jobSkillsList = jobSkillsRepository.findAll();
        assertThat(jobSkillsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteJobSkills() throws Exception {
        // Initialize the database
        jobSkillsRepository.saveAndFlush(jobSkills);
        jobSkillsSearchRepository.save(jobSkills);
        int databaseSizeBeforeDelete = jobSkillsRepository.findAll().size();

        // Get the jobSkills
        restJobSkillsMockMvc.perform(delete("/api/job-skills/{id}", jobSkills.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean jobSkillsExistsInEs = jobSkillsSearchRepository.exists(jobSkills.getId());
        assertThat(jobSkillsExistsInEs).isFalse();

        // Validate the database is empty
        List<JobSkills> jobSkillsList = jobSkillsRepository.findAll();
        assertThat(jobSkillsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchJobSkills() throws Exception {
        // Initialize the database
        jobSkillsRepository.saveAndFlush(jobSkills);
        jobSkillsSearchRepository.save(jobSkills);

        // Search the jobSkills
        restJobSkillsMockMvc.perform(get("/api/_search/job-skills?query=id:" + jobSkills.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobSkills.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobskillName").value(hasItem(DEFAULT_JOBSKILL_NAME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSkills.class);
        JobSkills jobSkills1 = new JobSkills();
        jobSkills1.setId(1L);
        JobSkills jobSkills2 = new JobSkills();
        jobSkills2.setId(jobSkills1.getId());
        assertThat(jobSkills1).isEqualTo(jobSkills2);
        jobSkills2.setId(2L);
        assertThat(jobSkills1).isNotEqualTo(jobSkills2);
        jobSkills1.setId(null);
        assertThat(jobSkills1).isNotEqualTo(jobSkills2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobSkillsDTO.class);
        JobSkillsDTO jobSkillsDTO1 = new JobSkillsDTO();
        jobSkillsDTO1.setId(1L);
        JobSkillsDTO jobSkillsDTO2 = new JobSkillsDTO();
        assertThat(jobSkillsDTO1).isNotEqualTo(jobSkillsDTO2);
        jobSkillsDTO2.setId(jobSkillsDTO1.getId());
        assertThat(jobSkillsDTO1).isEqualTo(jobSkillsDTO2);
        jobSkillsDTO2.setId(2L);
        assertThat(jobSkillsDTO1).isNotEqualTo(jobSkillsDTO2);
        jobSkillsDTO1.setId(null);
        assertThat(jobSkillsDTO1).isNotEqualTo(jobSkillsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(jobSkillsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(jobSkillsMapper.fromId(null)).isNull();
    }
}
