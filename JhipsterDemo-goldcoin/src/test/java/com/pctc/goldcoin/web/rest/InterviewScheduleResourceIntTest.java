package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.InterviewSchedule;
import com.pctc.goldcoin.repository.InterviewScheduleRepository;
import com.pctc.goldcoin.service.InterviewScheduleService;
import com.pctc.goldcoin.repository.search.InterviewScheduleSearchRepository;
import com.pctc.goldcoin.service.dto.InterviewScheduleDTO;
import com.pctc.goldcoin.service.mapper.InterviewScheduleMapper;
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
 * Test class for the InterviewScheduleResource REST controller.
 *
 * @see InterviewScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class InterviewScheduleResourceIntTest {

    private static final Long DEFAULT_INTSCH_ID = 1L;
    private static final Long UPDATED_INTSCH_ID = 2L;

    private static final String DEFAULT_INTSCH_TIME = "AAAAAAAAAA";
    private static final String UPDATED_INTSCH_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_INTSCH_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_INTSCH_STATUS = "BBBBBBBBBB";

    @Autowired
    private InterviewScheduleRepository interviewScheduleRepository;

    @Autowired
    private InterviewScheduleMapper interviewScheduleMapper;

    @Autowired
    private InterviewScheduleService interviewScheduleService;

    @Autowired
    private InterviewScheduleSearchRepository interviewScheduleSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInterviewScheduleMockMvc;

    private InterviewSchedule interviewSchedule;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InterviewScheduleResource interviewScheduleResource = new InterviewScheduleResource(interviewScheduleService);
        this.restInterviewScheduleMockMvc = MockMvcBuilders.standaloneSetup(interviewScheduleResource)
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
    public static InterviewSchedule createEntity(EntityManager em) {
        InterviewSchedule interviewSchedule = new InterviewSchedule()
            .intschId(DEFAULT_INTSCH_ID)
            .intschTime(DEFAULT_INTSCH_TIME)
            .intschStatus(DEFAULT_INTSCH_STATUS);
        return interviewSchedule;
    }

    @Before
    public void initTest() {
        interviewScheduleSearchRepository.deleteAll();
        interviewSchedule = createEntity(em);
    }

    @Test
    @Transactional
    public void createInterviewSchedule() throws Exception {
        int databaseSizeBeforeCreate = interviewScheduleRepository.findAll().size();

        // Create the InterviewSchedule
        InterviewScheduleDTO interviewScheduleDTO = interviewScheduleMapper.toDto(interviewSchedule);
        restInterviewScheduleMockMvc.perform(post("/api/interview-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interviewScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the InterviewSchedule in the database
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAll();
        assertThat(interviewScheduleList).hasSize(databaseSizeBeforeCreate + 1);
        InterviewSchedule testInterviewSchedule = interviewScheduleList.get(interviewScheduleList.size() - 1);
        assertThat(testInterviewSchedule.getIntschId()).isEqualTo(DEFAULT_INTSCH_ID);
        assertThat(testInterviewSchedule.getIntschTime()).isEqualTo(DEFAULT_INTSCH_TIME);
        assertThat(testInterviewSchedule.getIntschStatus()).isEqualTo(DEFAULT_INTSCH_STATUS);

        // Validate the InterviewSchedule in Elasticsearch
        InterviewSchedule interviewScheduleEs = interviewScheduleSearchRepository.findOne(testInterviewSchedule.getId());
        assertThat(interviewScheduleEs).isEqualToIgnoringGivenFields(testInterviewSchedule);
    }

    @Test
    @Transactional
    public void createInterviewScheduleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = interviewScheduleRepository.findAll().size();

        // Create the InterviewSchedule with an existing ID
        interviewSchedule.setId(1L);
        InterviewScheduleDTO interviewScheduleDTO = interviewScheduleMapper.toDto(interviewSchedule);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInterviewScheduleMockMvc.perform(post("/api/interview-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interviewScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the InterviewSchedule in the database
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAll();
        assertThat(interviewScheduleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInterviewSchedules() throws Exception {
        // Initialize the database
        interviewScheduleRepository.saveAndFlush(interviewSchedule);

        // Get all the interviewScheduleList
        restInterviewScheduleMockMvc.perform(get("/api/interview-schedules?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].intschId").value(hasItem(DEFAULT_INTSCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].intschTime").value(hasItem(DEFAULT_INTSCH_TIME.toString())))
            .andExpect(jsonPath("$.[*].intschStatus").value(hasItem(DEFAULT_INTSCH_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getInterviewSchedule() throws Exception {
        // Initialize the database
        interviewScheduleRepository.saveAndFlush(interviewSchedule);

        // Get the interviewSchedule
        restInterviewScheduleMockMvc.perform(get("/api/interview-schedules/{id}", interviewSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(interviewSchedule.getId().intValue()))
            .andExpect(jsonPath("$.intschId").value(DEFAULT_INTSCH_ID.intValue()))
            .andExpect(jsonPath("$.intschTime").value(DEFAULT_INTSCH_TIME.toString()))
            .andExpect(jsonPath("$.intschStatus").value(DEFAULT_INTSCH_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInterviewSchedule() throws Exception {
        // Get the interviewSchedule
        restInterviewScheduleMockMvc.perform(get("/api/interview-schedules/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterviewSchedule() throws Exception {
        // Initialize the database
        interviewScheduleRepository.saveAndFlush(interviewSchedule);
        interviewScheduleSearchRepository.save(interviewSchedule);
        int databaseSizeBeforeUpdate = interviewScheduleRepository.findAll().size();

        // Update the interviewSchedule
        InterviewSchedule updatedInterviewSchedule = interviewScheduleRepository.findOne(interviewSchedule.getId());
        // Disconnect from session so that the updates on updatedInterviewSchedule are not directly saved in db
        em.detach(updatedInterviewSchedule);
        updatedInterviewSchedule
            .intschId(UPDATED_INTSCH_ID)
            .intschTime(UPDATED_INTSCH_TIME)
            .intschStatus(UPDATED_INTSCH_STATUS);
        InterviewScheduleDTO interviewScheduleDTO = interviewScheduleMapper.toDto(updatedInterviewSchedule);

        restInterviewScheduleMockMvc.perform(put("/api/interview-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interviewScheduleDTO)))
            .andExpect(status().isOk());

        // Validate the InterviewSchedule in the database
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAll();
        assertThat(interviewScheduleList).hasSize(databaseSizeBeforeUpdate);
        InterviewSchedule testInterviewSchedule = interviewScheduleList.get(interviewScheduleList.size() - 1);
        assertThat(testInterviewSchedule.getIntschId()).isEqualTo(UPDATED_INTSCH_ID);
        assertThat(testInterviewSchedule.getIntschTime()).isEqualTo(UPDATED_INTSCH_TIME);
        assertThat(testInterviewSchedule.getIntschStatus()).isEqualTo(UPDATED_INTSCH_STATUS);

        // Validate the InterviewSchedule in Elasticsearch
        InterviewSchedule interviewScheduleEs = interviewScheduleSearchRepository.findOne(testInterviewSchedule.getId());
        assertThat(interviewScheduleEs).isEqualToIgnoringGivenFields(testInterviewSchedule);
    }

    @Test
    @Transactional
    public void updateNonExistingInterviewSchedule() throws Exception {
        int databaseSizeBeforeUpdate = interviewScheduleRepository.findAll().size();

        // Create the InterviewSchedule
        InterviewScheduleDTO interviewScheduleDTO = interviewScheduleMapper.toDto(interviewSchedule);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInterviewScheduleMockMvc.perform(put("/api/interview-schedules")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(interviewScheduleDTO)))
            .andExpect(status().isCreated());

        // Validate the InterviewSchedule in the database
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAll();
        assertThat(interviewScheduleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInterviewSchedule() throws Exception {
        // Initialize the database
        interviewScheduleRepository.saveAndFlush(interviewSchedule);
        interviewScheduleSearchRepository.save(interviewSchedule);
        int databaseSizeBeforeDelete = interviewScheduleRepository.findAll().size();

        // Get the interviewSchedule
        restInterviewScheduleMockMvc.perform(delete("/api/interview-schedules/{id}", interviewSchedule.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean interviewScheduleExistsInEs = interviewScheduleSearchRepository.exists(interviewSchedule.getId());
        assertThat(interviewScheduleExistsInEs).isFalse();

        // Validate the database is empty
        List<InterviewSchedule> interviewScheduleList = interviewScheduleRepository.findAll();
        assertThat(interviewScheduleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchInterviewSchedule() throws Exception {
        // Initialize the database
        interviewScheduleRepository.saveAndFlush(interviewSchedule);
        interviewScheduleSearchRepository.save(interviewSchedule);

        // Search the interviewSchedule
        restInterviewScheduleMockMvc.perform(get("/api/_search/interview-schedules?query=id:" + interviewSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(interviewSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].intschId").value(hasItem(DEFAULT_INTSCH_ID.intValue())))
            .andExpect(jsonPath("$.[*].intschTime").value(hasItem(DEFAULT_INTSCH_TIME.toString())))
            .andExpect(jsonPath("$.[*].intschStatus").value(hasItem(DEFAULT_INTSCH_STATUS.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewSchedule.class);
        InterviewSchedule interviewSchedule1 = new InterviewSchedule();
        interviewSchedule1.setId(1L);
        InterviewSchedule interviewSchedule2 = new InterviewSchedule();
        interviewSchedule2.setId(interviewSchedule1.getId());
        assertThat(interviewSchedule1).isEqualTo(interviewSchedule2);
        interviewSchedule2.setId(2L);
        assertThat(interviewSchedule1).isNotEqualTo(interviewSchedule2);
        interviewSchedule1.setId(null);
        assertThat(interviewSchedule1).isNotEqualTo(interviewSchedule2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InterviewScheduleDTO.class);
        InterviewScheduleDTO interviewScheduleDTO1 = new InterviewScheduleDTO();
        interviewScheduleDTO1.setId(1L);
        InterviewScheduleDTO interviewScheduleDTO2 = new InterviewScheduleDTO();
        assertThat(interviewScheduleDTO1).isNotEqualTo(interviewScheduleDTO2);
        interviewScheduleDTO2.setId(interviewScheduleDTO1.getId());
        assertThat(interviewScheduleDTO1).isEqualTo(interviewScheduleDTO2);
        interviewScheduleDTO2.setId(2L);
        assertThat(interviewScheduleDTO1).isNotEqualTo(interviewScheduleDTO2);
        interviewScheduleDTO1.setId(null);
        assertThat(interviewScheduleDTO1).isNotEqualTo(interviewScheduleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(interviewScheduleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(interviewScheduleMapper.fromId(null)).isNull();
    }
}
