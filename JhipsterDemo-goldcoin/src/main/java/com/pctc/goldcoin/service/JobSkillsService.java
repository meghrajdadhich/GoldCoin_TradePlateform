package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.JobSkillsDTO;
import java.util.List;

/**
 * Service Interface for managing JobSkills.
 */
public interface JobSkillsService {

    /**
     * Save a jobSkills.
     *
     * @param jobSkillsDTO the entity to save
     * @return the persisted entity
     */
    JobSkillsDTO save(JobSkillsDTO jobSkillsDTO);

    /**
     * Get all the jobSkills.
     *
     * @return the list of entities
     */
    List<JobSkillsDTO> findAll();

    /**
     * Get the "id" jobSkills.
     *
     * @param id the id of the entity
     * @return the entity
     */
    JobSkillsDTO findOne(Long id);

    /**
     * Delete the "id" jobSkills.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the jobSkills corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<JobSkillsDTO> search(String query);
}
