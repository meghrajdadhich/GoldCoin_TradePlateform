package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.Attendance;
import com.pctc.goldcoin.repository.AttendanceRepository;
import com.pctc.goldcoin.service.AttendanceService;
import com.pctc.goldcoin.repository.search.AttendanceSearchRepository;
import com.pctc.goldcoin.service.dto.AttendanceDTO;
import com.pctc.goldcoin.service.mapper.AttendanceMapper;
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
 * Test class for the AttendanceResource REST controller.
 *
 * @see AttendanceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class AttendanceResourceIntTest {

    private static final String DEFAULT_ATTANDANCE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ATTANDANCE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_ATTANDANCE_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_ATTANDANCE_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_ATTANDANCE_END_TIME = "AAAAAAAAAA";
    private static final String UPDATED_ATTANDANCE_END_TIME = "BBBBBBBBBB";

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendanceMapper attendanceMapper;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private AttendanceSearchRepository attendanceSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAttendanceMockMvc;

    private Attendance attendance;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AttendanceResource attendanceResource = new AttendanceResource(attendanceService);
        this.restAttendanceMockMvc = MockMvcBuilders.standaloneSetup(attendanceResource)
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
    public static Attendance createEntity(EntityManager em) {
        Attendance attendance = new Attendance()
            .attandanceDate(DEFAULT_ATTANDANCE_DATE)
            .attandanceStartTime(DEFAULT_ATTANDANCE_START_TIME)
            .attandanceEndTime(DEFAULT_ATTANDANCE_END_TIME);
        return attendance;
    }

    @Before
    public void initTest() {
        attendanceSearchRepository.deleteAll();
        attendance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendance() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getAttandanceDate()).isEqualTo(DEFAULT_ATTANDANCE_DATE);
        assertThat(testAttendance.getAttandanceStartTime()).isEqualTo(DEFAULT_ATTANDANCE_START_TIME);
        assertThat(testAttendance.getAttandanceEndTime()).isEqualTo(DEFAULT_ATTANDANCE_END_TIME);

        // Validate the Attendance in Elasticsearch
        Attendance attendanceEs = attendanceSearchRepository.findOne(testAttendance.getId());
        assertThat(attendanceEs).isEqualToIgnoringGivenFields(testAttendance);
    }

    @Test
    @Transactional
    public void createAttendanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendanceRepository.findAll().size();

        // Create the Attendance with an existing ID
        attendance.setId(1L);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendanceMockMvc.perform(post("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAttendances() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get all the attendanceList
        restAttendanceMockMvc.perform(get("/api/attendances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].attandanceDate").value(hasItem(DEFAULT_ATTANDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].attandanceStartTime").value(hasItem(DEFAULT_ATTANDANCE_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].attandanceEndTime").value(hasItem(DEFAULT_ATTANDANCE_END_TIME.toString())));
    }

    @Test
    @Transactional
    public void getAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);

        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(attendance.getId().intValue()))
            .andExpect(jsonPath("$.attandanceDate").value(DEFAULT_ATTANDANCE_DATE.toString()))
            .andExpect(jsonPath("$.attandanceStartTime").value(DEFAULT_ATTANDANCE_START_TIME.toString()))
            .andExpect(jsonPath("$.attandanceEndTime").value(DEFAULT_ATTANDANCE_END_TIME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAttendance() throws Exception {
        // Get the attendance
        restAttendanceMockMvc.perform(get("/api/attendances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        attendanceSearchRepository.save(attendance);
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Update the attendance
        Attendance updatedAttendance = attendanceRepository.findOne(attendance.getId());
        // Disconnect from session so that the updates on updatedAttendance are not directly saved in db
        em.detach(updatedAttendance);
        updatedAttendance
            .attandanceDate(UPDATED_ATTANDANCE_DATE)
            .attandanceStartTime(UPDATED_ATTANDANCE_START_TIME)
            .attandanceEndTime(UPDATED_ATTANDANCE_END_TIME);
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(updatedAttendance);

        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isOk());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate);
        Attendance testAttendance = attendanceList.get(attendanceList.size() - 1);
        assertThat(testAttendance.getAttandanceDate()).isEqualTo(UPDATED_ATTANDANCE_DATE);
        assertThat(testAttendance.getAttandanceStartTime()).isEqualTo(UPDATED_ATTANDANCE_START_TIME);
        assertThat(testAttendance.getAttandanceEndTime()).isEqualTo(UPDATED_ATTANDANCE_END_TIME);

        // Validate the Attendance in Elasticsearch
        Attendance attendanceEs = attendanceSearchRepository.findOne(testAttendance.getId());
        assertThat(attendanceEs).isEqualToIgnoringGivenFields(testAttendance);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendance() throws Exception {
        int databaseSizeBeforeUpdate = attendanceRepository.findAll().size();

        // Create the Attendance
        AttendanceDTO attendanceDTO = attendanceMapper.toDto(attendance);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAttendanceMockMvc.perform(put("/api/attendances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(attendanceDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendance in the database
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        attendanceSearchRepository.save(attendance);
        int databaseSizeBeforeDelete = attendanceRepository.findAll().size();

        // Get the attendance
        restAttendanceMockMvc.perform(delete("/api/attendances/{id}", attendance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean attendanceExistsInEs = attendanceSearchRepository.exists(attendance.getId());
        assertThat(attendanceExistsInEs).isFalse();

        // Validate the database is empty
        List<Attendance> attendanceList = attendanceRepository.findAll();
        assertThat(attendanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAttendance() throws Exception {
        // Initialize the database
        attendanceRepository.saveAndFlush(attendance);
        attendanceSearchRepository.save(attendance);

        // Search the attendance
        restAttendanceMockMvc.perform(get("/api/_search/attendances?query=id:" + attendance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendance.getId().intValue())))
            .andExpect(jsonPath("$.[*].attandanceDate").value(hasItem(DEFAULT_ATTANDANCE_DATE.toString())))
            .andExpect(jsonPath("$.[*].attandanceStartTime").value(hasItem(DEFAULT_ATTANDANCE_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].attandanceEndTime").value(hasItem(DEFAULT_ATTANDANCE_END_TIME.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Attendance.class);
        Attendance attendance1 = new Attendance();
        attendance1.setId(1L);
        Attendance attendance2 = new Attendance();
        attendance2.setId(attendance1.getId());
        assertThat(attendance1).isEqualTo(attendance2);
        attendance2.setId(2L);
        assertThat(attendance1).isNotEqualTo(attendance2);
        attendance1.setId(null);
        assertThat(attendance1).isNotEqualTo(attendance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendanceDTO.class);
        AttendanceDTO attendanceDTO1 = new AttendanceDTO();
        attendanceDTO1.setId(1L);
        AttendanceDTO attendanceDTO2 = new AttendanceDTO();
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
        attendanceDTO2.setId(attendanceDTO1.getId());
        assertThat(attendanceDTO1).isEqualTo(attendanceDTO2);
        attendanceDTO2.setId(2L);
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
        attendanceDTO1.setId(null);
        assertThat(attendanceDTO1).isNotEqualTo(attendanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(attendanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(attendanceMapper.fromId(null)).isNull();
    }
}
