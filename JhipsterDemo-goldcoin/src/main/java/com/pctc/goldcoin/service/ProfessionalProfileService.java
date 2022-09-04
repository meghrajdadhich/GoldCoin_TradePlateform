package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.ProfessionalProfileDTO;
import java.util.List;

/**
 * Service Interface for managing ProfessionalProfile.
 */
public interface ProfessionalProfileService {

    /**
     * Save a professionalProfile.
     *
     * @param professionalProfileDTO the entity to save
     * @return the persisted entity
     */
    ProfessionalProfileDTO save(ProfessionalProfileDTO professionalProfileDTO);

    /**
     * Get all the professionalProfiles.
     *
     * @return the list of entities
     */
    List<ProfessionalProfileDTO> findAll();

    /**
     * Get the "id" professionalProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProfessionalProfileDTO findOne(Long id);

    /**
     * Delete the "id" professionalProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the professionalProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ProfessionalProfileDTO> search(String query);
}
