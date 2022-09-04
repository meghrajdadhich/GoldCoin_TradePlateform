package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.ApplyJobService;
import com.pctc.goldcoin.domain.ApplyJob;
import com.pctc.goldcoin.repository.ApplyJobRepository;
import com.pctc.goldcoin.repository.search.ApplyJobSearchRepository;
import com.pctc.goldcoin.service.dto.ApplyJobDTO;
import com.pctc.goldcoin.service.mapper.ApplyJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ApplyJob.
 */
@Service
@Transactional
public class ApplyJobServiceImpl implements ApplyJobService {

    private final Logger log = LoggerFactory.getLogger(ApplyJobServiceImpl.class);

    private final ApplyJobRepository applyJobRepository;

    private final ApplyJobMapper applyJobMapper;

    private final ApplyJobSearchRepository applyJobSearchRepository;

    public ApplyJobServiceImpl(ApplyJobRepository applyJobRepository, ApplyJobMapper applyJobMapper, ApplyJobSearchRepository applyJobSearchRepository) {
        this.applyJobRepository = applyJobRepository;
        this.applyJobMapper = applyJobMapper;
        this.applyJobSearchRepository = applyJobSearchRepository;
    }

    /**
     * Save a applyJob.
     *
     * @param applyJobDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ApplyJobDTO save(ApplyJobDTO applyJobDTO) {
        log.debug("Request to save ApplyJob : {}", applyJobDTO);
        ApplyJob applyJob = applyJobMapper.toEntity(applyJobDTO);
        applyJob = applyJobRepository.save(applyJob);
        ApplyJobDTO result = applyJobMapper.toDto(applyJob);
        applyJobSearchRepository.save(applyJob);
        return result;
    }

    /**
     * Get all the applyJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplyJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ApplyJobs");
        return applyJobRepository.findAll(pageable)
            .map(applyJobMapper::toDto);
    }

    /**
     * Get one applyJob by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ApplyJobDTO findOne(Long id) {
        log.debug("Request to get ApplyJob : {}", id);
        ApplyJob applyJob = applyJobRepository.findOneWithEagerRelationships(id);
        return applyJobMapper.toDto(applyJob);
    }

    /**
     * Delete the applyJob by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ApplyJob : {}", id);
        applyJobRepository.delete(id);
        applyJobSearchRepository.delete(id);
    }

    /**
     * Search for the applyJob corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ApplyJobDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ApplyJobs for query {}", query);
        Page<ApplyJob> result = applyJobSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(applyJobMapper::toDto);
    }
}
