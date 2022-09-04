package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.EducationalProfileDTO;
import java.util.List;

/**
 * Service Interface for managing EducationalProfile.
 */
public interface EducationalProfileService {

    /**
     * Save a educationalProfile.
     *
     * @param educationalProfileDTO the entity to save
     * @return the persisted entity
     */
    EducationalProfileDTO save(EducationalProfileDTO educationalProfileDTO);

    /**
     * Get all the educationalProfiles.
     *
     * @return the list of entities
     */
    List<EducationalProfileDTO> findAll();

    /**
     * Get the "id" educationalProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EducationalProfileDTO findOne(Long id);

    /**
     * Delete the "id" educationalProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the educationalProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EducationalProfileDTO> search(String query);
}
