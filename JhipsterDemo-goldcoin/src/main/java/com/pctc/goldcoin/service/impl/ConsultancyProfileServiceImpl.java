package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.ConsultancyProfileService;
import com.pctc.goldcoin.domain.ConsultancyProfile;
import com.pctc.goldcoin.repository.ConsultancyProfileRepository;
import com.pctc.goldcoin.repository.search.ConsultancyProfileSearchRepository;
import com.pctc.goldcoin.service.dto.ConsultancyProfileDTO;
import com.pctc.goldcoin.service.mapper.ConsultancyProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ConsultancyProfile.
 */
@Service
@Transactional
public class ConsultancyProfileServiceImpl implements ConsultancyProfileService {

    private final Logger log = LoggerFactory.getLogger(ConsultancyProfileServiceImpl.class);

    private final ConsultancyProfileRepository consultancyProfileRepository;

    private final ConsultancyProfileMapper consultancyProfileMapper;

    private final ConsultancyProfileSearchRepository consultancyProfileSearchRepository;

    public ConsultancyProfileServiceImpl(ConsultancyProfileRepository consultancyProfileRepository, ConsultancyProfileMapper consultancyProfileMapper, ConsultancyProfileSearchRepository consultancyProfileSearchRepository) {
        this.consultancyProfileRepository = consultancyProfileRepository;
        this.consultancyProfileMapper = consultancyProfileMapper;
        this.consultancyProfileSearchRepository = consultancyProfileSearchRepository;
    }

    /**
     * Save a consultancyProfile.
     *
     * @param consultancyProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultancyProfileDTO save(ConsultancyProfileDTO consultancyProfileDTO) {
        log.debug("Request to save ConsultancyProfile : {}", consultancyProfileDTO);
        ConsultancyProfile consultancyProfile = consultancyProfileMapper.toEntity(consultancyProfileDTO);
        consultancyProfile = consultancyProfileRepository.save(consultancyProfile);
        ConsultancyProfileDTO result = consultancyProfileMapper.toDto(consultancyProfile);
        consultancyProfileSearchRepository.save(consultancyProfile);
        return result;
    }

    /**
     * Get all the consultancyProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultancyProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultancyProfiles");
        return consultancyProfileRepository.findAll(pageable)
            .map(consultancyProfileMapper::toDto);
    }

    /**
     * Get one consultancyProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultancyProfileDTO findOne(Long id) {
        log.debug("Request to get ConsultancyProfile : {}", id);
        ConsultancyProfile consultancyProfile = consultancyProfileRepository.findOne(id);
        return consultancyProfileMapper.toDto(consultancyProfile);
    }

    /**
     * Delete the consultancyProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultancyProfile : {}", id);
        consultancyProfileRepository.delete(id);
        consultancyProfileSearchRepository.delete(id);
    }

    /**
     * Search for the consultancyProfile corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultancyProfileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ConsultancyProfiles for query {}", query);
        Page<ConsultancyProfile> result = consultancyProfileSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(consultancyProfileMapper::toDto);
    }
}
