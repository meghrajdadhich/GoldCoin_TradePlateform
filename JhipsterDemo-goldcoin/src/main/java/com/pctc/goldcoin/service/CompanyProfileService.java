package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.CompanyProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CompanyProfile.
 */
public interface CompanyProfileService {

    /**
     * Save a companyProfile.
     *
     * @param companyProfileDTO the entity to save
     * @return the persisted entity
     */
    CompanyProfileDTO save(CompanyProfileDTO companyProfileDTO);

    /**
     * Get all the companyProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompanyProfileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyProfile.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CompanyProfileDTO findOne(Long id);

    /**
     * Delete the "id" companyProfile.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the companyProfile corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CompanyProfileDTO> search(String query, Pageable pageable);
}
