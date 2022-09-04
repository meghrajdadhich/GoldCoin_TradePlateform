package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.Recruiter;
import com.pctc.goldcoin.repository.RecruiterRepository;
import com.pctc.goldcoin.service.RecruiterService;
import com.pctc.goldcoin.repository.search.RecruiterSearchRepository;
import com.pctc.goldcoin.service.dto.RecruiterDTO;
import com.pctc.goldcoin.service.mapper.RecruiterMapper;
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
 * Test class for the RecruiterResource REST controller.
 *
 * @see RecruiterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class RecruiterResourceIntTest {

    private static final Instant DEFAULT_RECRUITER_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECRUITER_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RECRUITER_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RECRUITER_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private RecruiterMapper recruiterMapper;

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private RecruiterSearchRepository recruiterSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecruiterMockMvc;

    private Recruiter recruiter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecruiterResource recruiterResource = new RecruiterResource(recruiterService);
        this.restRecruiterMockMvc = MockMvcBuilders.standaloneSetup(recruiterResource)
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
    public static Recruiter createEntity(EntityManager em) {
        Recruiter recruiter = new Recruiter()
            .recruiterStartDate(DEFAULT_RECRUITER_START_DATE)
            .recruiterEndDate(DEFAULT_RECRUITER_END_DATE);
        return recruiter;
    }

    @Before
    public void initTest() {
        recruiterSearchRepository.deleteAll();
        recruiter = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecruiter() throws Exception {
        int databaseSizeBeforeCreate = recruiterRepository.findAll().size();

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);
        restRecruiterMockMvc.perform(post("/api/recruiters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recruiterDTO)))
            .andExpect(status().isCreated());

        // Validate the Recruiter in the database
        List<Recruiter> recruiterList = recruiterRepository.findAll();
        assertThat(recruiterList).hasSize(databaseSizeBeforeCreate + 1);
        Recruiter testRecruiter = recruiterList.get(recruiterList.size() - 1);
        assertThat(testRecruiter.getRecruiterStartDate()).isEqualTo(DEFAULT_RECRUITER_START_DATE);
        assertThat(testRecruiter.getRecruiterEndDate()).isEqualTo(DEFAULT_RECRUITER_END_DATE);

        // Validate the Recruiter in Elasticsearch
        Recruiter recruiterEs = recruiterSearchRepository.findOne(testRecruiter.getId());
        assertThat(recruiterEs).isEqualToIgnoringGivenFields(testRecruiter);
    }

    @Test
    @Transactional
    public void createRecruiterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recruiterRepository.findAll().size();

        // Create the Recruiter with an existing ID
        recruiter.setId(1L);
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecruiterMockMvc.perform(post("/api/recruiters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recruiterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Recruiter in the database
        List<Recruiter> recruiterList = recruiterRepository.findAll();
        assertThat(recruiterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecruiters() throws Exception {
        // Initialize the database
        recruiterRepository.saveAndFlush(recruiter);

        // Get all the recruiterList
        restRecruiterMockMvc.perform(get("/api/recruiters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recruiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].recruiterStartDate").value(hasItem(DEFAULT_RECRUITER_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].recruiterEndDate").value(hasItem(DEFAULT_RECRUITER_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getRecruiter() throws Exception {
        // Initialize the database
        recruiterRepository.saveAndFlush(recruiter);

        // Get the recruiter
        restRecruiterMockMvc.perform(get("/api/recruiters/{id}", recruiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recruiter.getId().intValue()))
            .andExpect(jsonPath("$.recruiterStartDate").value(DEFAULT_RECRUITER_START_DATE.toString()))
            .andExpect(jsonPath("$.recruiterEndDate").value(DEFAULT_RECRUITER_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRecruiter() throws Exception {
        // Get the recruiter
        restRecruiterMockMvc.perform(get("/api/recruiters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecruiter() throws Exception {
        // Initialize the database
        recruiterRepository.saveAndFlush(recruiter);
        recruiterSearchRepository.save(recruiter);
        int databaseSizeBeforeUpdate = recruiterRepository.findAll().size();

        // Update the recruiter
        Recruiter updatedRecruiter = recruiterRepository.findOne(recruiter.getId());
        // Disconnect from session so that the updates on updatedRecruiter are not directly saved in db
        em.detach(updatedRecruiter);
        updatedRecruiter
            .recruiterStartDate(UPDATED_RECRUITER_START_DATE)
            .recruiterEndDate(UPDATED_RECRUITER_END_DATE);
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(updatedRecruiter);

        restRecruiterMockMvc.perform(put("/api/recruiters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recruiterDTO)))
            .andExpect(status().isOk());

        // Validate the Recruiter in the database
        List<Recruiter> recruiterList = recruiterRepository.findAll();
        assertThat(recruiterList).hasSize(databaseSizeBeforeUpdate);
        Recruiter testRecruiter = recruiterList.get(recruiterList.size() - 1);
        assertThat(testRecruiter.getRecruiterStartDate()).isEqualTo(UPDATED_RECRUITER_START_DATE);
        assertThat(testRecruiter.getRecruiterEndDate()).isEqualTo(UPDATED_RECRUITER_END_DATE);

        // Validate the Recruiter in Elasticsearch
        Recruiter recruiterEs = recruiterSearchRepository.findOne(testRecruiter.getId());
        assertThat(recruiterEs).isEqualToIgnoringGivenFields(testRecruiter);
    }

    @Test
    @Transactional
    public void updateNonExistingRecruiter() throws Exception {
        int databaseSizeBeforeUpdate = recruiterRepository.findAll().size();

        // Create the Recruiter
        RecruiterDTO recruiterDTO = recruiterMapper.toDto(recruiter);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecruiterMockMvc.perform(put("/api/recruiters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recruiterDTO)))
            .andExpect(status().isCreated());

        // Validate the Recruiter in the database
        List<Recruiter> recruiterList = recruiterRepository.findAll();
        assertThat(recruiterList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRecruiter() throws Exception {
        // Initialize the database
        recruiterRepository.saveAndFlush(recruiter);
        recruiterSearchRepository.save(recruiter);
        int databaseSizeBeforeDelete = recruiterRepository.findAll().size();

        // Get the recruiter
        restRecruiterMockMvc.perform(delete("/api/recruiters/{id}", recruiter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean recruiterExistsInEs = recruiterSearchRepository.exists(recruiter.getId());
        assertThat(recruiterExistsInEs).isFalse();

        // Validate the database is empty
        List<Recruiter> recruiterList = recruiterRepository.findAll();
        assertThat(recruiterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchRecruiter() throws Exception {
        // Initialize the database
        recruiterRepository.saveAndFlush(recruiter);
        recruiterSearchRepository.save(recruiter);

        // Search the recruiter
        restRecruiterMockMvc.perform(get("/api/_search/recruiters?query=id:" + recruiter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recruiter.getId().intValue())))
            .andExpect(jsonPath("$.[*].recruiterStartDate").value(hasItem(DEFAULT_RECRUITER_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].recruiterEndDate").value(hasItem(DEFAULT_RECRUITER_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recruiter.class);
        Recruiter recruiter1 = new Recruiter();
        recruiter1.setId(1L);
        Recruiter recruiter2 = new Recruiter();
        recruiter2.setId(recruiter1.getId());
        assertThat(recruiter1).isEqualTo(recruiter2);
        recruiter2.setId(2L);
        assertThat(recruiter1).isNotEqualTo(recruiter2);
        recruiter1.setId(null);
        assertThat(recruiter1).isNotEqualTo(recruiter2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecruiterDTO.class);
        RecruiterDTO recruiterDTO1 = new RecruiterDTO();
        recruiterDTO1.setId(1L);
        RecruiterDTO recruiterDTO2 = new RecruiterDTO();
        assertThat(recruiterDTO1).isNotEqualTo(recruiterDTO2);
        recruiterDTO2.setId(recruiterDTO1.getId());
        assertThat(recruiterDTO1).isEqualTo(recruiterDTO2);
        recruiterDTO2.setId(2L);
        assertThat(recruiterDTO1).isNotEqualTo(recruiterDTO2);
        recruiterDTO1.setId(null);
        assertThat(recruiterDTO1).isNotEqualTo(recruiterDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(recruiterMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(recruiterMapper.fromId(null)).isNull();
    }
}
