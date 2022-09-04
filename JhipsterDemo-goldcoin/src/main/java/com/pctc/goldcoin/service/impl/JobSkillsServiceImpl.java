package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.JobSkillsService;
import com.pctc.goldcoin.domain.JobSkills;
import com.pctc.goldcoin.repository.JobSkillsRepository;
import com.pctc.goldcoin.repository.search.JobSkillsSearchRepository;
import com.pctc.goldcoin.service.dto.JobSkillsDTO;
import com.pctc.goldcoin.service.mapper.JobSkillsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing JobSkills.
 */
@Service
@Transactional
public class JobSkillsServiceImpl implements JobSkillsService {

    private final Logger log = LoggerFactory.getLogger(JobSkillsServiceImpl.class);

    private final JobSkillsRepository jobSkillsRepository;

    private final JobSkillsMapper jobSkillsMapper;

    private final JobSkillsSearchRepository jobSkillsSearchRepository;

    public JobSkillsServiceImpl(JobSkillsRepository jobSkillsRepository, JobSkillsMapper jobSkillsMapper, JobSkillsSearchRepository jobSkillsSearchRepository) {
        this.jobSkillsRepository = jobSkillsRepository;
        this.jobSkillsMapper = jobSkillsMapper;
        this.jobSkillsSearchRepository = jobSkillsSearchRepository;
    }

    /**
     * Save a jobSkills.
     *
     * @param jobSkillsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JobSkillsDTO save(JobSkillsDTO jobSkillsDTO) {
        log.debug("Request to save JobSkills : {}", jobSkillsDTO);
        JobSkills jobSkills = jobSkillsMapper.toEntity(jobSkillsDTO);
        jobSkills = jobSkillsRepository.save(jobSkills);
        JobSkillsDTO result = jobSkillsMapper.toDto(jobSkills);
        jobSkillsSearchRepository.save(jobSkills);
        return result;
    }

    /**
     * Get all the jobSkills.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobSkillsDTO> findAll() {
        log.debug("Request to get all JobSkills");
        return jobSkillsRepository.findAll().stream()
            .map(jobSkillsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one jobSkills by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JobSkillsDTO findOne(Long id) {
        log.debug("Request to get JobSkills : {}", id);
        JobSkills jobSkills = jobSkillsRepository.findOne(id);
        return jobSkillsMapper.toDto(jobSkills);
    }

    /**
     * Delete the jobSkills by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobSkills : {}", id);
        jobSkillsRepository.delete(id);
        jobSkillsSearchRepository.delete(id);
    }

    /**
     * Search for the jobSkills corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<JobSkillsDTO> search(String query) {
        log.debug("Request to search JobSkills for query {}", query);
        return StreamSupport
            .stream(jobSkillsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(jobSkillsMapper::toDto)
            .collect(Collectors.toList());
    }
}
