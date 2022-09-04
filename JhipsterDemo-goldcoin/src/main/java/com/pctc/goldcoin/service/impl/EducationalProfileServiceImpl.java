package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.EducationalProfileService;
import com.pctc.goldcoin.domain.EducationalProfile;
import com.pctc.goldcoin.repository.EducationalProfileRepository;
import com.pctc.goldcoin.repository.search.EducationalProfileSearchRepository;
import com.pctc.goldcoin.service.dto.EducationalProfileDTO;
import com.pctc.goldcoin.service.mapper.EducationalProfileMapper;
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
 * Service Implementation for managing EducationalProfile.
 */
@Service
@Transactional
public class EducationalProfileServiceImpl implements EducationalProfileService {

    private final Logger log = LoggerFactory.getLogger(EducationalProfileServiceImpl.class);

    private final EducationalProfileRepository educationalProfileRepository;

    private final EducationalProfileMapper educationalProfileMapper;

    private final EducationalProfileSearchRepository educationalProfileSearchRepository;

    public EducationalProfileServiceImpl(EducationalProfileRepository educationalProfileRepository, EducationalProfileMapper educationalProfileMapper, EducationalProfileSearchRepository educationalProfileSearchRepository) {
        this.educationalProfileRepository = educationalProfileRepository;
        this.educationalProfileMapper = educationalProfileMapper;
        this.educationalProfileSearchRepository = educationalProfileSearchRepository;
    }

    /**
     * Save a educationalProfile.
     *
     * @param educationalProfileDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EducationalProfileDTO save(EducationalProfileDTO educationalProfileDTO) {
        log.debug("Request to save EducationalProfile : {}", educationalProfileDTO);
        EducationalProfile educationalProfile = educationalProfileMapper.toEntity(educationalProfileDTO);
        educationalProfile = educationalProfileRepository.save(educationalProfile);
        EducationalProfileDTO result = educationalProfileMapper.toDto(educationalProfile);
        educationalProfileSearchRepository.save(educationalProfile);
        return result;
    }

    /**
     * Get all the educationalProfiles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EducationalProfileDTO> findAll() {
        log.debug("Request to get all EducationalProfiles");
        return educationalProfileRepository.findAll().stream()
            .map(educationalProfileMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one educationalProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EducationalProfileDTO findOne(Long id) {
        log.debug("Request to get EducationalProfile : {}", id);
        EducationalProfile educationalProfile = educationalProfileRepository.findOne(id);
        return educationalProfileMapper.toDto(educationalProfile);
    }

    /**
     * Delete the educationalProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EducationalProfile : {}", id);
        educationalProfileRepository.delete(id);
        educationalProfileSearchRepository.delete(id);
    }

    /**
     * Search for the educationalProfile corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EducationalProfileDTO> search(String query) {
        log.debug("Request to search EducationalProfiles for query {}", query);
        return StreamSupport
            .stream(educationalProfileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(educationalProfileMapper::toDto)
            .collect(Collectors.toList());
    }
}
