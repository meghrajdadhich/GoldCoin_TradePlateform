package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.CandidateProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CandidateProfile.
 */
public interface CandidateProfileService {

    /**
     * Save a candidateProfile.
     *
     * @param candidateProfileDTO the entity to save
     * @return the persisted entity
     */
    CandidateProfileDTO save(CandidateProfileDTO candidateProfileDTO);

    /**
     * Get all the candidateProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CandidateProfileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" candidateProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CandidateProfileDTO findOne(Long id);

    /**
     * Delete the "id" candidateProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the candidateProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CandidateProfileDTO> search(String query, Pageable pageable);
}
