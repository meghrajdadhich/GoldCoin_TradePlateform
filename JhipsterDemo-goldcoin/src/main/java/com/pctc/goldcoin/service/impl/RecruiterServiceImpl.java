package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.RecruiterService;
import com.pctc.goldcoin.domain.Recruiter;
import com.pctc.goldcoin.repository.RecruiterRepository;
import com.pctc.goldcoin.repository.search.RecruiterSearchRepository;
import com.pctc.goldcoin.service.dto.RecruiterDTO;
import com.pctc.goldcoin.service.mapper.RecruiterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Recruiter.
 */
@Service
@Transactional
public class RecruiterServiceImpl implements RecruiterService {

    private final Logger log = LoggerFactory.getLogger(RecruiterServiceImpl.class);

    private final RecruiterRepository recruiterRepository;

    private final RecruiterMapper recruiterMapper;

    private final RecruiterSearchRepository recruiterSearchRepository;

    public RecruiterServiceImpl(RecruiterRepository recruiterRepository, RecruiterMapper recruiterMapper, RecruiterSearchRepository recruiterSearchRepository) {
        this.recruiterRepository = recruiterRepository;
        this.recruiterMapper = recruiterMapper;
        this.recruiterSearchRepository = recruiterSearchRepository;
    }

    /**
     * Save a recruiter.
     *
     * @param recruiterDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RecruiterDTO save(RecruiterDTO recruiterDTO) {
        log.debug("Request to save Recruiter : {}", recruiterDTO);
        Recruiter recruiter = recruiterMapper.toEntity(recruiterDTO);
        recruiter = recruiterRepository.save(recruiter);
        RecruiterDTO result = recruiterMapper.toDto(recruiter);
        recruiterSearchRepository.save(recruiter);
        return result;
    }

    /**
     * Get all the recruiters.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecruiterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recruiters");
        return recruiterRepository.findAll(pageable)
            .map(recruiterMapper::toDto);
    }

    /**
     * Get one recruiter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RecruiterDTO findOne(Long id) {
        log.debug("Request to get Recruiter : {}", id);
        Recruiter recruiter = recruiterRepository.findOne(id);
        return recruiterMapper.toDto(recruiter);
    }

    /**
     * Delete the recruiter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Recruiter : {}", id);
        recruiterRepository.delete(id);
        recruiterSearchRepository.delete(id);
    }

    /**
     * Search for the recruiter corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RecruiterDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Recruiters for query {}", query);
        Page<Recruiter> result = recruiterSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(recruiterMapper::toDto);
    }
}
