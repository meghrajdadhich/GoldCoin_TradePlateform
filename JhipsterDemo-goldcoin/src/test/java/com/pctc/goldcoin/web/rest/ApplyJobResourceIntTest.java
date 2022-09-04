package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.ApplyJob;
import com.pctc.goldcoin.repository.ApplyJobRepository;
import com.pctc.goldcoin.service.ApplyJobService;
import com.pctc.goldcoin.repository.search.ApplyJobSearchRepository;
import com.pctc.goldcoin.service.dto.ApplyJobDTO;
import com.pctc.goldcoin.service.mapper.ApplyJobMapper;
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
 * Test class for the ApplyJobResource REST controller.
 *
 * @see ApplyJobResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class ApplyJobResourceIntTest {

    private static final Instant DEFAULT_APPLY_JOB_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLY_JOB_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_APPLY_JOB_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPLY_JOB_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ApplyJobRepository applyJobRepository;

    @Autowired
    private ApplyJobMapper applyJobMapper;

    @Autowired
    private ApplyJobService applyJobService;

    @Autowired
    private ApplyJobSearchRepository applyJobSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restApplyJobMockMvc;

    private ApplyJob applyJob;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ApplyJobResource applyJobResource = new ApplyJobResource(applyJobService);
        this.restApplyJobMockMvc = MockMvcBuilders.standaloneSetup(applyJobResource)
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
    public static ApplyJob createEntity(EntityManager em) {
        ApplyJob applyJob = new ApplyJob()
            .applyJobStartDate(DEFAULT_APPLY_JOB_START_DATE)
            .applyJobEndDate(DEFAULT_APPLY_JOB_END_DATE);
        return applyJob;
    }

    @Before
    public void initTest() {
        applyJobSearchRepository.deleteAll();
        applyJob = createEntity(em);
    }

    @Test
    @Transactional
    public void createApplyJob() throws Exception {
        int databaseSizeBeforeCreate = applyJobRepository.findAll().size();

        // Create the ApplyJob
        ApplyJobDTO applyJobDTO = applyJobMapper.toDto(applyJob);
        restApplyJobMockMvc.perform(post("/api/apply-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applyJobDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplyJob in the database
        List<ApplyJob> applyJobList = applyJobRepository.findAll();
        assertThat(applyJobList).hasSize(databaseSizeBeforeCreate + 1);
        ApplyJob testApplyJob = applyJobList.get(applyJobList.size() - 1);
        assertThat(testApplyJob.getApplyJobStartDate()).isEqualTo(DEFAULT_APPLY_JOB_START_DATE);
        assertThat(testApplyJob.getApplyJobEndDate()).isEqualTo(DEFAULT_APPLY_JOB_END_DATE);

        // Validate the ApplyJob in Elasticsearch
        ApplyJob applyJobEs = applyJobSearchRepository.findOne(testApplyJob.getId());
        assertThat(applyJobEs).isEqualToIgnoringGivenFields(testApplyJob);
    }

    @Test
    @Transactional
    public void createApplyJobWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = applyJobRepository.findAll().size();

        // Create the ApplyJob with an existing ID
        applyJob.setId(1L);
        ApplyJobDTO applyJobDTO = applyJobMapper.toDto(applyJob);

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplyJobMockMvc.perform(post("/api/apply-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applyJobDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ApplyJob in the database
        List<ApplyJob> applyJobList = applyJobRepository.findAll();
        assertThat(applyJobList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllApplyJobs() throws Exception {
        // Initialize the database
        applyJobRepository.saveAndFlush(applyJob);

        // Get all the applyJobList
        restApplyJobMockMvc.perform(get("/api/apply-jobs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applyJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].applyJobStartDate").value(hasItem(DEFAULT_APPLY_JOB_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].applyJobEndDate").value(hasItem(DEFAULT_APPLY_JOB_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getApplyJob() throws Exception {
        // Initialize the database
        applyJobRepository.saveAndFlush(applyJob);

        // Get the applyJob
        restApplyJobMockMvc.perform(get("/api/apply-jobs/{id}", applyJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(applyJob.getId().intValue()))
            .andExpect(jsonPath("$.applyJobStartDate").value(DEFAULT_APPLY_JOB_START_DATE.toString()))
            .andExpect(jsonPath("$.applyJobEndDate").value(DEFAULT_APPLY_JOB_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingApplyJob() throws Exception {
        // Get the applyJob
        restApplyJobMockMvc.perform(get("/api/apply-jobs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateApplyJob() throws Exception {
        // Initialize the database
        applyJobRepository.saveAndFlush(applyJob);
        applyJobSearchRepository.save(applyJob);
        int databaseSizeBeforeUpdate = applyJobRepository.findAll().size();

        // Update the applyJob
        ApplyJob updatedApplyJob = applyJobRepository.findOne(applyJob.getId());
        // Disconnect from session so that the updates on updatedApplyJob are not directly saved in db
        em.detach(updatedApplyJob);
        updatedApplyJob
            .applyJobStartDate(UPDATED_APPLY_JOB_START_DATE)
            .applyJobEndDate(UPDATED_APPLY_JOB_END_DATE);
        ApplyJobDTO applyJobDTO = applyJobMapper.toDto(updatedApplyJob);

        restApplyJobMockMvc.perform(put("/api/apply-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applyJobDTO)))
            .andExpect(status().isOk());

        // Validate the ApplyJob in the database
        List<ApplyJob> applyJobList = applyJobRepository.findAll();
        assertThat(applyJobList).hasSize(databaseSizeBeforeUpdate);
        ApplyJob testApplyJob = applyJobList.get(applyJobList.size() - 1);
        assertThat(testApplyJob.getApplyJobStartDate()).isEqualTo(UPDATED_APPLY_JOB_START_DATE);
        assertThat(testApplyJob.getApplyJobEndDate()).isEqualTo(UPDATED_APPLY_JOB_END_DATE);

        // Validate the ApplyJob in Elasticsearch
        ApplyJob applyJobEs = applyJobSearchRepository.findOne(testApplyJob.getId());
        assertThat(applyJobEs).isEqualToIgnoringGivenFields(testApplyJob);
    }

    @Test
    @Transactional
    public void updateNonExistingApplyJob() throws Exception {
        int databaseSizeBeforeUpdate = applyJobRepository.findAll().size();

        // Create the ApplyJob
        ApplyJobDTO applyJobDTO = applyJobMapper.toDto(applyJob);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restApplyJobMockMvc.perform(put("/api/apply-jobs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(applyJobDTO)))
            .andExpect(status().isCreated());

        // Validate the ApplyJob in the database
        List<ApplyJob> applyJobList = applyJobRepository.findAll();
        assertThat(applyJobList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteApplyJob() throws Exception {
        // Initialize the database
        applyJobRepository.saveAndFlush(applyJob);
        applyJobSearchRepository.save(applyJob);
        int databaseSizeBeforeDelete = applyJobRepository.findAll().size();

        // Get the applyJob
        restApplyJobMockMvc.perform(delete("/api/apply-jobs/{id}", applyJob.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean applyJobExistsInEs = applyJobSearchRepository.exists(applyJob.getId());
        assertThat(applyJobExistsInEs).isFalse();

        // Validate the database is empty
        List<ApplyJob> applyJobList = applyJobRepository.findAll();
        assertThat(applyJobList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchApplyJob() throws Exception {
        // Initialize the database
        applyJobRepository.saveAndFlush(applyJob);
        applyJobSearchRepository.save(applyJob);

        // Search the applyJob
        restApplyJobMockMvc.perform(get("/api/_search/apply-jobs?query=id:" + applyJob.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applyJob.getId().intValue())))
            .andExpect(jsonPath("$.[*].applyJobStartDate").value(hasItem(DEFAULT_APPLY_JOB_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].applyJobEndDate").value(hasItem(DEFAULT_APPLY_JOB_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplyJob.class);
        ApplyJob applyJob1 = new ApplyJob();
        applyJob1.setId(1L);
        ApplyJob applyJob2 = new ApplyJob();
        applyJob2.setId(applyJob1.getId());
        assertThat(applyJob1).isEqualTo(applyJob2);
        applyJob2.setId(2L);
        assertThat(applyJob1).isNotEqualTo(applyJob2);
        applyJob1.setId(null);
        assertThat(applyJob1).isNotEqualTo(applyJob2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplyJobDTO.class);
        ApplyJobDTO applyJobDTO1 = new ApplyJobDTO();
        applyJobDTO1.setId(1L);
        ApplyJobDTO applyJobDTO2 = new ApplyJobDTO();
        assertThat(applyJobDTO1).isNotEqualTo(applyJobDTO2);
        applyJobDTO2.setId(applyJobDTO1.getId());
        assertThat(applyJobDTO1).isEqualTo(applyJobDTO2);
        applyJobDTO2.setId(2L);
        assertThat(applyJobDTO1).isNotEqualTo(applyJobDTO2);
        applyJobDTO1.setId(null);
        assertThat(applyJobDTO1).isNotEqualTo(applyJobDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(applyJobMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(applyJobMapper.fromId(null)).isNull();
    }
}
