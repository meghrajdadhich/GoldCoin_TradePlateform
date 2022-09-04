package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.CandidateProfileService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.CandidateProfileDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing CandidateProfile.
 */
@RestController
@RequestMapping("/api")
public class CandidateProfileResource {

    private final Logger log = LoggerFactory.getLogger(CandidateProfileResource.class);

    private static final String ENTITY_NAME = "candidateProfile";

    private final CandidateProfileService candidateProfileService;

    public CandidateProfileResource(CandidateProfileService candidateProfileService) {
        this.candidateProfileService = candidateProfileService;
    }

    /**
     * POST  /candidate-profiles : Create a new candidateProfile.
     *
     * @param candidateProfileDTO the candidateProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new candidateProfileDTO, or with status 400 (Bad Request) if the candidateProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/candidate-profiles")
    @Timed
    public ResponseEntity<CandidateProfileDTO> createCandidateProfile(@RequestBody CandidateProfileDTO candidateProfileDTO) throws URISyntaxException {
        log.debug("REST request to save CandidateProfile : {}", candidateProfileDTO);
        if (candidateProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new candidateProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CandidateProfileDTO result = candidateProfileService.save(candidateProfileDTO);
        return ResponseEntity.created(new URI("/api/candidate-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /candidate-profiles : Updates an existing candidateProfile.
     *
     * @param candidateProfileDTO the candidateProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated candidateProfileDTO,
     * or with status 400 (Bad Request) if the candidateProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the candidateProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/candidate-profiles")
    @Timed
    public ResponseEntity<CandidateProfileDTO> updateCandidateProfile(@RequestBody CandidateProfileDTO candidateProfileDTO) throws URISyntaxException {
        log.debug("REST request to update CandidateProfile : {}", candidateProfileDTO);
        if (candidateProfileDTO.getId() == null) {
            return createCandidateProfile(candidateProfileDTO);
        }
        CandidateProfileDTO result = candidateProfileService.save(candidateProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, candidateProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /candidate-profiles : get all the candidateProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of candidateProfiles in body
     */
    @GetMapping("/candidate-profiles")
    @Timed
    public ResponseEntity<List<CandidateProfileDTO>> getAllCandidateProfiles(Pageable pageable) {
        log.debug("REST request to get a page of CandidateProfiles");
        Page<CandidateProfileDTO> page = candidateProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/candidate-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /candidate-profiles/:id : get the "id" candidateProfile.
     *
     * @param id the id of the candidateProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the candidateProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/candidate-profiles/{id}")
    @Timed
    public ResponseEntity<CandidateProfileDTO> getCandidateProfile(@PathVariable Long id) {
        log.debug("REST request to get CandidateProfile : {}", id);
        CandidateProfileDTO candidateProfileDTO = candidateProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(candidateProfileDTO));
    }

    /**
     * DELETE  /candidate-profiles/:id : delete the "id" candidateProfile.
     *
     * @param id the id of the candidateProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/candidate-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteCandidateProfile(@PathVariable Long id) {
        log.debug("REST request to delete CandidateProfile : {}", id);
        candidateProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/candidate-profiles?query=:query : search for the candidateProfile corresponding
     * to the query.
     *
     * @param query the query of the candidateProfile search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/candidate-profiles")
    @Timed
    public ResponseEntity<List<CandidateProfileDTO>> searchCandidateProfiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CandidateProfiles for query {}", query);
        Page<CandidateProfileDTO> page = candidateProfileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/candidate-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
