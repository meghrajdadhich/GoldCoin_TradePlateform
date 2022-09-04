package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.PCTCBasicUserService;
import com.pctc.goldcoin.domain.PCTCBasicUser;
import com.pctc.goldcoin.repository.PCTCBasicUserRepository;
import com.pctc.goldcoin.repository.search.PCTCBasicUserSearchRepository;
import com.pctc.goldcoin.service.dto.PCTCBasicUserDTO;
import com.pctc.goldcoin.service.mapper.PCTCBasicUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PCTCBasicUser.
 */
@Service
@Transactional
public class PCTCBasicUserServiceImpl implements PCTCBasicUserService {

    private final Logger log = LoggerFactory.getLogger(PCTCBasicUserServiceImpl.class);

    private final PCTCBasicUserRepository pCTCBasicUserRepository;

    private final PCTCBasicUserMapper pCTCBasicUserMapper;

    private final PCTCBasicUserSearchRepository pCTCBasicUserSearchRepository;

    public PCTCBasicUserServiceImpl(PCTCBasicUserRepository pCTCBasicUserRepository, PCTCBasicUserMapper pCTCBasicUserMapper, PCTCBasicUserSearchRepository pCTCBasicUserSearchRepository) {
        this.pCTCBasicUserRepository = pCTCBasicUserRepository;
        this.pCTCBasicUserMapper = pCTCBasicUserMapper;
        this.pCTCBasicUserSearchRepository = pCTCBasicUserSearchRepository;
    }

    /**
     * Save a pCTCBasicUser.
     *
     * @param pCTCBasicUserDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PCTCBasicUserDTO save(PCTCBasicUserDTO pCTCBasicUserDTO) {
        log.debug("Request to save PCTCBasicUser : {}", pCTCBasicUserDTO);
        PCTCBasicUser pCTCBasicUser = pCTCBasicUserMapper.toEntity(pCTCBasicUserDTO);
        pCTCBasicUser = pCTCBasicUserRepository.save(pCTCBasicUser);
        PCTCBasicUserDTO result = pCTCBasicUserMapper.toDto(pCTCBasicUser);
        pCTCBasicUserSearchRepository.save(pCTCBasicUser);
        return result;
    }

    /**
     * Get all the pCTCBasicUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PCTCBasicUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PCTCBasicUsers");
        return pCTCBasicUserRepository.findAll(pageable)
            .map(pCTCBasicUserMapper::toDto);
    }

    /**
     * Get one pCTCBasicUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PCTCBasicUserDTO findOne(Long id) {
        log.debug("Request to get PCTCBasicUser : {}", id);
        PCTCBasicUser pCTCBasicUser = pCTCBasicUserRepository.findOne(id);
        return pCTCBasicUserMapper.toDto(pCTCBasicUser);
    }

    /**
     * Delete the pCTCBasicUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PCTCBasicUser : {}", id);
        pCTCBasicUserRepository.delete(id);
        pCTCBasicUserSearchRepository.delete(id);
    }

    /**
     * Search for the pCTCBasicUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PCTCBasicUserDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PCTCBasicUsers for query {}", query);
        Page<PCTCBasicUser> result = pCTCBasicUserSearchRepository.search(queryStringQuery(query), pageable);
        return result.map(pCTCBasicUserMapper::toDto);
    }
}
