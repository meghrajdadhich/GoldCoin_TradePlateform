package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.InterviewScheduleService;
import com.pctc.goldcoin.domain.InterviewSchedule;
import com.pctc.goldcoin.repository.InterviewScheduleRepository;
import com.pctc.goldcoin.repository.search.InterviewScheduleSearchRepository;
import com.pctc.goldcoin.service.dto.InterviewScheduleDTO;
import com.pctc.goldcoin.service.mapper.InterviewScheduleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing InterviewSchedule.
 */
@Service
@Transactional
public class InterviewScheduleServiceImpl implements InterviewScheduleService {

    private final Logger log = LoggerFactory.getLogger(InterviewScheduleServiceImpl.class);

    private final InterviewScheduleRepository interviewScheduleRepository;

    private final InterviewScheduleMapper interviewScheduleMapper;

    private final InterviewScheduleSearchRepository interviewScheduleSearchRepository;

    public InterviewScheduleServiceImpl(InterviewScheduleRepository interviewScheduleRepository, InterviewScheduleMapper interviewScheduleMapper, InterviewScheduleSearchRepository interviewScheduleSearchRepository) {
        this.interviewScheduleRepository = interviewScheduleRepository;
        this.interviewScheduleMapper = interviewScheduleMapper;
        this.interviewScheduleSearchRepository = interviewScheduleSearchRepository;
    }

    /**
     * Save a interviewSchedule.
     *
     * @param interviewScheduleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InterviewScheduleDTO save(InterviewScheduleDTO interviewScheduleDTO) {
        log.debug("Request to save InterviewSchedule : {}", interviewScheduleDTO);
        InterviewSchedule interviewSchedule = interviewScheduleMapper.toEntity(interviewScheduleDTO);
        interviewSchedule = interviewScheduleRepository.save(interviewSchedule);
        InterviewScheduleDTO result = interviewScheduleMapper.toDto(interviewSchedule);
        interviewScheduleSearchRepository.save(interviewSchedule);
        return result;
    }

    /**
     * Get all the interviewSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InterviewScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InterviewSchedules");
        return interviewScheduleRepository.findAll(pageable)
            .map(interviewScheduleMapper::toDto);
    }

    /**
     * Get one interviewSchedule by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InterviewScheduleDTO findOne(Long id) {
        log.debug("Request to get InterviewSchedule : {}", id);
        InterviewSchedule interviewSchedule = interviewScheduleRepository.findOne(id);
        return interviewScheduleMapper.toDto(interviewSchedule);
    }

    /**
     * Delete the interviewSchedule by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InterviewSchedule : {}", id);
        interviewScheduleRepository.delete(id);
        interviewScheduleSearchRepository.delete(id);
    }

    /**
     * Search for the interviewSchedule corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InterviewScheduleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of InterviewSchedules for query {}", query);
        Page<InterviewSchedule> result = interviewScheduleSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(interviewScheduleMapper::toDto);
    }
}
