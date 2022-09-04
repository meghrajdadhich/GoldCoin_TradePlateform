package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.HierarchyDTO;
import java.util.List;

/**
 * Service Interface for managing Hierarchy.
 */
public interface HierarchyService {

    /**
     * Save a hierarchy.
     *
     * @param hierarchyDTO the entity to save
     * @return the persisted entity
     */
    HierarchyDTO save(HierarchyDTO hierarchyDTO);

    /**
     * Get all the hierarchies.
     *
     * @return the list of entities
     */
    List<HierarchyDTO> findAll();

    /**
     * Get the "id" hierarchy.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HierarchyDTO findOne(Long id);

    /**
     * Delete the "id" hierarchy.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the hierarchy corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HierarchyDTO> search(String query);
}
