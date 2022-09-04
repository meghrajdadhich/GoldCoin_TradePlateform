package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.ProfessionalProfileService;
import com.pctc.goldcoin.domain.ProfessionalProfile;
import com.pctc.goldcoin.repository.ProfessionalProfileRepository;
import com.pctc.goldcoin.repository.search.ProfessionalProfileSearchRepository;
import com.pctc.goldcoin.service.dto.ProfessionalProfileDTO;
import com.pctc.goldcoin.service.mapper.ProfessionalProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProfessionalProfile.
 */
@Service
@Transactional
public class ProfessionalProfileServiceImpl implements ProfessionalProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfessionalProfileServiceImpl.class);

    private final ProfessionalProfileRepository professionalProfileRepository;

    private final ProfessionalProfileMapper professionalProfileMapper;

    private final ProfessionalProfileSearchRepository professionalProfileSearchRepository;

    public ProfessionalProfileServiceImpl(ProfessionalProfileRepository professionalProfileRepository, ProfessionalProfileMapper professionalProfileMapper, ProfessionalProfileSearchRepository professionalProfileSearchRepository) {
        this.professionalProfileRepository = professionalProfileRepository;
        this.professionalProfileMapper = professionalProfileMapper;
        this.professionalProfileSearchRepository = professionalProfileSearchRepository;
    }

    /**
     * Save a professionalProfile.
     *
     * @param professionalProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfessionalProfileDTO save(ProfessionalProfileDTO professionalProfileDTO) {
        log.debug("Request to save ProfessionalProfile : {}", professionalProfileDTO);
        ProfessionalProfile professionalProfile = professionalProfileMapper.toEntity(professionalProfileDTO);
        professionalProfile = professionalProfileRepository.save(professionalProfile);
        ProfessionalProfileDTO result = professionalProfileMapper.toDto(professionalProfile);
        professionalProfileSearchRepository.save(professionalProfile);
        return result;
    }

    /**
     * Get all the professionalProfiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalProfileDTO> findAll() {
        log.debug("Request to get all ProfessionalProfiles");
        return professionalProfileRepository.findAll().stream()
            .map(professionalProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one professionalProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProfessionalProfileDTO findOne(Long id) {
        log.debug("Request to get ProfessionalProfile : {}", id);
        ProfessionalProfile professionalProfile = professionalProfileRepository.findOne(id);
        return professionalProfileMapper.toDto(professionalProfile);
    }

    /**
     * Delete the professionalProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProfessionalProfile : {}", id);
        professionalProfileRepository.delete(id);
        professionalProfileSearchRepository.delete(id);
    }

    /**
     * Search for the professionalProfile corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalProfileDTO> search(String query) {
        log.debug("Request to search ProfessionalProfiles for query {}", query);
        return StreamSupport
            .stream(professionalProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(professionalProfileMapper::toDto)
            .collect(Collectors.toList());
    }
}
