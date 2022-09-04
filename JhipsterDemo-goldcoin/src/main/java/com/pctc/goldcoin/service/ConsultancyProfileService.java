package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.ConsultancyProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ConsultancyProfile.
 */
public interface ConsultancyProfileService {

    /**
     * Save a consultancyProfile.
     *
     * @param consultancyProfileDTO the entity to save
     * @return the persisted entity
     */
    ConsultancyProfileDTO save(ConsultancyProfileDTO consultancyProfileDTO);

    /**
     * Get all the consultancyProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConsultancyProfileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" consultancyProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsultancyProfileDTO findOne(Long id);

    /**
     * Delete the "id" consultancyProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the consultancyProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConsultancyProfileDTO> search(String query, Pageable pageable);
}
