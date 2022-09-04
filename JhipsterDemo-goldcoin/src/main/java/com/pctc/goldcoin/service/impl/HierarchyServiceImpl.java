package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.HierarchyService;
import com.pctc.goldcoin.domain.Hierarchy;
import com.pctc.goldcoin.repository.HierarchyRepository;
import com.pctc.goldcoin.repository.search.HierarchySearchRepository;
import com.pctc.goldcoin.service.dto.HierarchyDTO;
import com.pctc.goldcoin.service.mapper.HierarchyMapper;
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
 * Service Implementation for managing Hierarchy.
 */
@Service
@Transactional
public class HierarchyServiceImpl implements HierarchyService {

    private final Logger log = LoggerFactory.getLogger(HierarchyServiceImpl.class);

    private final HierarchyRepository hierarchyRepository;

    private final HierarchyMapper hierarchyMapper;

    private final HierarchySearchRepository hierarchySearchRepository;

    public HierarchyServiceImpl(HierarchyRepository hierarchyRepository, HierarchyMapper hierarchyMapper, HierarchySearchRepository hierarchySearchRepository) {
        this.hierarchyRepository = hierarchyRepository;
        this.hierarchyMapper = hierarchyMapper;
        this.hierarchySearchRepository = hierarchySearchRepository;
    }

    /**
     * Save a hierarchy.
     *
     * @param hierarchyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HierarchyDTO save(HierarchyDTO hierarchyDTO) {
        log.debug("Request to save Hierarchy : {}", hierarchyDTO);
        Hierarchy hierarchy = hierarchyMapper.toEntity(hierarchyDTO);
        hierarchy = hierarchyRepository.save(hierarchy);
        HierarchyDTO result = hierarchyMapper.toDto(hierarchy);
        hierarchySearchRepository.save(hierarchy);
        return result;
    }

    /**
     * Get all the hierarchies.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HierarchyDTO> findAll() {
        log.debug("Request to get all Hierarchies");
        return hierarchyRepository.findAll().stream()
            .map(hierarchyMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one hierarchy by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HierarchyDTO findOne(Long id) {
        log.debug("Request to get Hierarchy : {}", id);
        Hierarchy hierarchy = hierarchyRepository.findOne(id);
        return hierarchyMapper.toDto(hierarchy);
    }

    /**
     * Delete the hierarchy by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Hierarchy : {}", id);
        hierarchyRepository.delete(id);
        hierarchySearchRepository.delete(id);
    }

    /**
     * Search for the hierarchy corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HierarchyDTO> search(String query) {
        log.debug("Request to search Hierarchies for query {}", query);
        return StreamSupport
            .stream(hierarchySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(hierarchyMapper::toDto)
            .collect(Collectors.toList());
    }
}
