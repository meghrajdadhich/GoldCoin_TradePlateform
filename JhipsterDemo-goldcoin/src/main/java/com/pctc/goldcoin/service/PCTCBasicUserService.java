package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.PCTCBasicUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PCTCBasicUser.
 */
public interface PCTCBasicUserService {

    /**
     * Save a pCTCBasicUser.
     *
     * @param pCTCBasicUserDTO the entity to save
     * @return the persisted entity
     */
    PCTCBasicUserDTO save(PCTCBasicUserDTO pCTCBasicUserDTO);

    /**
     * Get all the pCTCBasicUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PCTCBasicUserDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pCTCBasicUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PCTCBasicUserDTO findOne(Long id);

    /**
     * Delete the "id" pCTCBasicUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the pCTCBasicUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PCTCBasicUserDTO> search(String query, Pageable pageable);
}
