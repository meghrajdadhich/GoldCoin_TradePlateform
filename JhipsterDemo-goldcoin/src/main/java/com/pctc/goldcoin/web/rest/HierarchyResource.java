package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.HierarchyService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.service.dto.HierarchyDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Hierarchy.
 */
@RestController
@RequestMapping("/api")
public class HierarchyResource {

    private final Logger log = LoggerFactory.getLogger(HierarchyResource.class);

    private static final String ENTITY_NAME = "hierarchy";

    private final HierarchyService hierarchyService;

    public HierarchyResource(HierarchyService hierarchyService) {
        this.hierarchyService = hierarchyService;
    }

    /**
     * POST  /hierarchies : Create a new hierarchy.
     *
     * @param hierarchyDTO the hierarchyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new hierarchyDTO, or with status 400 (Bad Request) if the hierarchy has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/hierarchies")
    @Timed
    public ResponseEntity<HierarchyDTO> createHierarchy(@RequestBody HierarchyDTO hierarchyDTO) throws URISyntaxException {
        log.debug("REST request to save Hierarchy : {}", hierarchyDTO);
        if (hierarchyDTO.getId() != null) {
            throw new BadRequestAlertException("A new hierarchy cannot already have an ID", ENTITY_NAME, "idexists");
        }
        HierarchyDTO result = hierarchyService.save(hierarchyDTO);
        return ResponseEntity.created(new URI("/api/hierarchies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hierarchies : Updates an existing hierarchy.
     *
     * @param hierarchyDTO the hierarchyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated hierarchyDTO,
     * or with status 400 (Bad Request) if the hierarchyDTO is not valid,
     * or with status 500 (Internal Server Error) if the hierarchyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/hierarchies")
    @Timed
    public ResponseEntity<HierarchyDTO> updateHierarchy(@RequestBody HierarchyDTO hierarchyDTO) throws URISyntaxException {
        log.debug("REST request to update Hierarchy : {}", hierarchyDTO);
        if (hierarchyDTO.getId() == null) {
            return createHierarchy(hierarchyDTO);
        }
        HierarchyDTO result = hierarchyService.save(hierarchyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, hierarchyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hierarchies : get all the hierarchies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of hierarchies in body
     */
    @GetMapping("/hierarchies")
    @Timed
    public List<HierarchyDTO> getAllHierarchies() {
        log.debug("REST request to get all Hierarchies");
        return hierarchyService.findAll();
        }

    /**
     * GET  /hierarchies/:id : get the "id" hierarchy.
     *
     * @param id the id of the hierarchyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the hierarchyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/hierarchies/{id}")
    @Timed
    public ResponseEntity<HierarchyDTO> getHierarchy(@PathVariable Long id) {
        log.debug("REST request to get Hierarchy : {}", id);
        HierarchyDTO hierarchyDTO = hierarchyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(hierarchyDTO));
    }

    /**
     * DELETE  /hierarchies/:id : delete the "id" hierarchy.
     *
     * @param id the id of the hierarchyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/hierarchies/{id}")
    @Timed
    public ResponseEntity<Void> deleteHierarchy(@PathVariable Long id) {
        log.debug("REST request to delete Hierarchy : {}", id);
        hierarchyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/hierarchies?query=:query : search for the hierarchy corresponding
     * to the query.
     *
     * @param query the query of the hierarchy search
     * @return the result of the search
     */
    @GetMapping("/_search/hierarchies")
    @Timed
    public List<HierarchyDTO> searchHierarchies(@RequestParam String query) {
        log.debug("REST request to search Hierarchies for query {}", query);
        return hierarchyService.search(query);
    }

}
