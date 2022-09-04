package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.CandidateProfileService;
import com.pctc.goldcoin.domain.CandidateProfile;
import com.pctc.goldcoin.repository.CandidateProfileRepository;
import com.pctc.goldcoin.repository.search.CandidateProfileSearchRepository;
import com.pctc.goldcoin.service.dto.CandidateProfileDTO;
import com.pctc.goldcoin.service.mapper.CandidateProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CandidateProfile.
 */
@Service
@Transactional
public class CandidateProfileServiceImpl implements CandidateProfileService {

    private final Logger log = LoggerFactory.getLogger(CandidateProfileServiceImpl.class);

    private final CandidateProfileRepository candidateProfileRepository;

    private final CandidateProfileMapper candidateProfileMapper;

    private final CandidateProfileSearchRepository candidateProfileSearchRepository;

    public CandidateProfileServiceImpl(CandidateProfileRepository candidateProfileRepository, CandidateProfileMapper candidateProfileMapper, CandidateProfileSearchRepository candidateProfileSearchRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.candidateProfileMapper = candidateProfileMapper;
        this.candidateProfileSearchRepository = candidateProfileSearchRepository;
    }

    /**
     * Save a candidateProfile.
     *
     * @param candidateProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CandidateProfileDTO save(CandidateProfileDTO candidateProfileDTO) {
        log.debug("Request to save CandidateProfile : {}", candidateProfileDTO);
        CandidateProfile candidateProfile = candidateProfileMapper.toEntity(candidateProfileDTO);
        candidateProfile = candidateProfileRepository.save(candidateProfile);
        CandidateProfileDTO result = candidateProfileMapper.toDto(candidateProfile);
        candidateProfileSearchRepository.save(candidateProfile);
        return result;
    }

    /**
     * Get all the candidateProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidateProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CandidateProfiles");
        return candidateProfileRepository.findAll(pageable)
            .map(candidateProfileMapper::toDto);
    }

    /**
     * Get one candidateProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CandidateProfileDTO findOne(Long id) {
        log.debug("Request to get CandidateProfile : {}", id);
        CandidateProfile candidateProfile = candidateProfileRepository.findOne(id);
        return candidateProfileMapper.toDto(candidateProfile);
    }

    /**
     * Delete the candidateProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CandidateProfile : {}", id);
        candidateProfileRepository.delete(id);
        candidateProfileSearchRepository.delete(id);
    }

    /**
     * Search for the candidateProfile corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CandidateProfileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CandidateProfiles for query {}", query);
        Page<CandidateProfile> result = candidateProfileSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(candidateProfileMapper::toDto);
    }
}
