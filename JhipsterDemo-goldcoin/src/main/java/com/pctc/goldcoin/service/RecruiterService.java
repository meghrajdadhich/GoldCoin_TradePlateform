package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.RecruiterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Recruiter.
 */
public interface RecruiterService {

    /**
     * Save a recruiter.
     *
     * @param recruiterDTO the entity to save
     * @return the persisted entity
     */
    RecruiterDTO save(RecruiterDTO recruiterDTO);

    /**
     * Get all the recruiters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecruiterDTO> findAll(Pageable pageable);

    /**
     * Get the "id" recruiter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RecruiterDTO findOne(Long id);

    /**
     * Delete the "id" recruiter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the recruiter corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RecruiterDTO> search(String query, Pageable pageable);
}
