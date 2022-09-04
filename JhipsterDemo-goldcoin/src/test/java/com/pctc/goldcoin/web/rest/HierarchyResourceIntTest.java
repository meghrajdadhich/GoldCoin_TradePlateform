package com.pctc.goldcoin.web.rest;

import com.pctc.goldcoin.JhipsterDemoApp;

import com.pctc.goldcoin.domain.Hierarchy;
import com.pctc.goldcoin.repository.HierarchyRepository;
import com.pctc.goldcoin.service.HierarchyService;
import com.pctc.goldcoin.repository.search.HierarchySearchRepository;
import com.pctc.goldcoin.service.dto.HierarchyDTO;
import com.pctc.goldcoin.service.mapper.HierarchyMapper;
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
 * Test class for the HierarchyResource REST controller.
 *
 * @see HierarchyResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterDemoApp.class)
public class HierarchyResourceIntTest {

    private static final Instant DEFAULT_REPORT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REPORT_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_REPORT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REPORT_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private HierarchyRepository hierarchyRepository;

    @Autowired
    private HierarchyMapper hierarchyMapper;

    @Autowired
    private HierarchyService hierarchyService;

    @Autowired
    private HierarchySearchRepository hierarchySearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restHierarchyMockMvc;

    private Hierarchy hierarchy;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HierarchyResource hierarchyResource = new HierarchyResource(hierarchyService);
        this.restHierarchyMockMvc = MockMvcBuilders.standaloneSetup(hierarchyResource)
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
    public static Hierarchy createEntity(EntityManager em) {
        Hierarchy hierarchy = new Hierarchy()
            .reportStartDate(DEFAULT_REPORT_START_DATE)
            .reportEndDate(DEFAULT_REPORT_END_DATE);
        return hierarchy;
    }

    @Before
    public void initTest() {
        hierarchySearchRepository.deleteAll();
        hierarchy = createEntity(em);
    }

    @Test
    @Transactional
    public void createHierarchy() throws Exception {
        int databaseSizeBeforeCreate = hierarchyRepository.findAll().size();

        // Create the Hierarchy
        HierarchyDTO hierarchyDTO = hierarchyMapper.toDto(hierarchy);
        restHierarchyMockMvc.perform(post("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchyDTO)))
            .andExpect(status().isCreated());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeCreate + 1);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getReportStartDate()).isEqualTo(DEFAULT_REPORT_START_DATE);
        assertThat(testHierarchy.getReportEndDate()).isEqualTo(DEFAULT_REPORT_END_DATE);

        // Validate the Hierarchy in Elasticsearch
        Hierarchy hierarchyEs = hierarchySearchRepository.findOne(testHierarchy.getId());
        assertThat(hierarchyEs).isEqualToIgnoringGivenFields(testHierarchy);
    }

    @Test
    @Transactional
    public void createHierarchyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hierarchyRepository.findAll().size();

        // Create the Hierarchy with an existing ID
        hierarchy.setId(1L);
        HierarchyDTO hierarchyDTO = hierarchyMapper.toDto(hierarchy);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHierarchyMockMvc.perform(post("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHierarchies() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        // Get all the hierarchyList
        restHierarchyMockMvc.perform(get("/api/hierarchies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchy.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportStartDate").value(hasItem(DEFAULT_REPORT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].reportEndDate").value(hasItem(DEFAULT_REPORT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void getHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);

        // Get the hierarchy
        restHierarchyMockMvc.perform(get("/api/hierarchies/{id}", hierarchy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hierarchy.getId().intValue()))
            .andExpect(jsonPath("$.reportStartDate").value(DEFAULT_REPORT_START_DATE.toString()))
            .andExpect(jsonPath("$.reportEndDate").value(DEFAULT_REPORT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHierarchy() throws Exception {
        // Get the hierarchy
        restHierarchyMockMvc.perform(get("/api/hierarchies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);
        hierarchySearchRepository.save(hierarchy);
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Update the hierarchy
        Hierarchy updatedHierarchy = hierarchyRepository.findOne(hierarchy.getId());
        // Disconnect from session so that the updates on updatedHierarchy are not directly saved in db
        em.detach(updatedHierarchy);
        updatedHierarchy
            .reportStartDate(UPDATED_REPORT_START_DATE)
            .reportEndDate(UPDATED_REPORT_END_DATE);
        HierarchyDTO hierarchyDTO = hierarchyMapper.toDto(updatedHierarchy);

        restHierarchyMockMvc.perform(put("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchyDTO)))
            .andExpect(status().isOk());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate);
        Hierarchy testHierarchy = hierarchyList.get(hierarchyList.size() - 1);
        assertThat(testHierarchy.getReportStartDate()).isEqualTo(UPDATED_REPORT_START_DATE);
        assertThat(testHierarchy.getReportEndDate()).isEqualTo(UPDATED_REPORT_END_DATE);

        // Validate the Hierarchy in Elasticsearch
        Hierarchy hierarchyEs = hierarchySearchRepository.findOne(testHierarchy.getId());
        assertThat(hierarchyEs).isEqualToIgnoringGivenFields(testHierarchy);
    }

    @Test
    @Transactional
    public void updateNonExistingHierarchy() throws Exception {
        int databaseSizeBeforeUpdate = hierarchyRepository.findAll().size();

        // Create the Hierarchy
        HierarchyDTO hierarchyDTO = hierarchyMapper.toDto(hierarchy);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restHierarchyMockMvc.perform(put("/api/hierarchies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hierarchyDTO)))
            .andExpect(status().isCreated());

        // Validate the Hierarchy in the database
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);
        hierarchySearchRepository.save(hierarchy);
        int databaseSizeBeforeDelete = hierarchyRepository.findAll().size();

        // Get the hierarchy
        restHierarchyMockMvc.perform(delete("/api/hierarchies/{id}", hierarchy.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean hierarchyExistsInEs = hierarchySearchRepository.exists(hierarchy.getId());
        assertThat(hierarchyExistsInEs).isFalse();

        // Validate the database is empty
        List<Hierarchy> hierarchyList = hierarchyRepository.findAll();
        assertThat(hierarchyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchHierarchy() throws Exception {
        // Initialize the database
        hierarchyRepository.saveAndFlush(hierarchy);
        hierarchySearchRepository.save(hierarchy);

        // Search the hierarchy
        restHierarchyMockMvc.perform(get("/api/_search/hierarchies?query=id:" + hierarchy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hierarchy.getId().intValue())))
            .andExpect(jsonPath("$.[*].reportStartDate").value(hasItem(DEFAULT_REPORT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].reportEndDate").value(hasItem(DEFAULT_REPORT_END_DATE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hierarchy.class);
        Hierarchy hierarchy1 = new Hierarchy();
        hierarchy1.setId(1L);
        Hierarchy hierarchy2 = new Hierarchy();
        hierarchy2.setId(hierarchy1.getId());
        assertThat(hierarchy1).isEqualTo(hierarchy2);
        hierarchy2.setId(2L);
        assertThat(hierarchy1).isNotEqualTo(hierarchy2);
        hierarchy1.setId(null);
        assertThat(hierarchy1).isNotEqualTo(hierarchy2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HierarchyDTO.class);
        HierarchyDTO hierarchyDTO1 = new HierarchyDTO();
        hierarchyDTO1.setId(1L);
        HierarchyDTO hierarchyDTO2 = new HierarchyDTO();
        assertThat(hierarchyDTO1).isNotEqualTo(hierarchyDTO2);
        hierarchyDTO2.setId(hierarchyDTO1.getId());
        assertThat(hierarchyDTO1).isEqualTo(hierarchyDTO2);
        hierarchyDTO2.setId(2L);
        assertThat(hierarchyDTO1).isNotEqualTo(hierarchyDTO2);
        hierarchyDTO1.setId(null);
        assertThat(hierarchyDTO1).isNotEqualTo(hierarchyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(hierarchyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(hierarchyMapper.fromId(null)).isNull();
    }
}
