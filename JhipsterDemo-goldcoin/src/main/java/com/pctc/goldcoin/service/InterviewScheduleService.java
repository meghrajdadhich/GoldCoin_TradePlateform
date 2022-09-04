package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.InterviewScheduleDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing InterviewSchedule.
 */
public interface InterviewScheduleService {

    /**
     * Save a interviewSchedule.
     *
     * @param interviewScheduleDTO the entity to save
     * @return the persisted entity
     */
    InterviewScheduleDTO save(InterviewScheduleDTO interviewScheduleDTO);

    /**
     * Get all the interviewSchedules.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InterviewScheduleDTO> findAll(Pageable pageable);

    /**
     * Get the "id" interviewSchedule.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InterviewScheduleDTO findOne(Long id);

    /**
     * Delete the "id" interviewSchedule.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the interviewSchedule corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InterviewScheduleDTO> search(String query, Pageable pageable);
}
