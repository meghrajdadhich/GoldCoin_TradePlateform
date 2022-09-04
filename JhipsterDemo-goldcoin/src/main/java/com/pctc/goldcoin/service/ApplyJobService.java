package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.ApplyJobDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ApplyJob.
 */
public interface ApplyJobService {

    /**
     * Save a applyJob.
     *
     * @param applyJobDTO the entity to save
     * @return the persisted entity
     */
    ApplyJobDTO save(ApplyJobDTO applyJobDTO);

    /**
     * Get all the applyJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplyJobDTO> findAll(Pageable pageable);

    /**
     * Get the "id" applyJob.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ApplyJobDTO findOne(Long id);

    /**
     * Delete the "id" applyJob.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the applyJob corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ApplyJobDTO> search(String query, Pageable pageable);
}
