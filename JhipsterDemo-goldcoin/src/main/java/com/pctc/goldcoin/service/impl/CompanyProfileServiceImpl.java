package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.CompanyProfileService;
import com.pctc.goldcoin.domain.CompanyProfile;
import com.pctc.goldcoin.repository.CompanyProfileRepository;
import com.pctc.goldcoin.repository.search.CompanyProfileSearchRepository;
import com.pctc.goldcoin.service.dto.CompanyProfileDTO;
import com.pctc.goldcoin.service.mapper.CompanyProfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing CompanyProfile.
 */
@Service
@Transactional
public class CompanyProfileServiceImpl implements CompanyProfileService {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileServiceImpl.class);

    private final CompanyProfileRepository companyProfileRepository;

    private final CompanyProfileMapper companyProfileMapper;

    private final CompanyProfileSearchRepository companyProfileSearchRepository;

    public CompanyProfileServiceImpl(CompanyProfileRepository companyProfileRepository, CompanyProfileMapper companyProfileMapper, CompanyProfileSearchRepository companyProfileSearchRepository) {
        this.companyProfileRepository = companyProfileRepository;
        this.companyProfileMapper = companyProfileMapper;
        this.companyProfileSearchRepository = companyProfileSearchRepository;
    }

    /**
     * Save a companyProfile.
     *
     * @param companyProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CompanyProfileDTO save(CompanyProfileDTO companyProfileDTO) {
        log.debug("Request to save CompanyProfile : {}", companyProfileDTO);
        CompanyProfile companyProfile = companyProfileMapper.toEntity(companyProfileDTO);
        companyProfile = companyProfileRepository.save(companyProfile);
        CompanyProfileDTO result = companyProfileMapper.toDto(companyProfile);
        companyProfileSearchRepository.save(companyProfile);
        return result;
    }

    /**
     * Get all the companyProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyProfiles");
        return companyProfileRepository.findAll(pageable)
            .map(companyProfileMapper::toDto);
    }

    /**
     * Get one companyProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CompanyProfileDTO findOne(Long id) {
        log.debug("Request to get CompanyProfile : {}", id);
        CompanyProfile companyProfile = companyProfileRepository.findOne(id);
        return companyProfileMapper.toDto(companyProfile);
    }

    /**
     * Delete the companyProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyProfile : {}", id);
        companyProfileRepository.delete(id);
        companyProfileSearchRepository.delete(id);
    }

    /**
     * Search for the companyProfile corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CompanyProfileDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CompanyProfiles for query {}", query);
        Page<CompanyProfile> result = companyProfileSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(companyProfileMapper::toDto);
    }
}
