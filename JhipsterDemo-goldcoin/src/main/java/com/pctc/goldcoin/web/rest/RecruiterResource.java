package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.RecruiterService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.RecruiterDTO;
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
 * REST controller for managing Recruiter.
 */
@RestController
@RequestMapping("/api")
public class RecruiterResource {

    private final Logger log = LoggerFactory.getLogger(RecruiterResource.class);

    private static final String ENTITY_NAME = "recruiter";

    private final RecruiterService recruiterService;

    public RecruiterResource(RecruiterService recruiterService) {
        this.recruiterService = recruiterService;
    }

    /**
     * POST  /recruiters : Create a new recruiter.
     *
     * @param recruiterDTO the recruiterDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new recruiterDTO, or with status 400 (Bad Request) if the recruiter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/recruiters")
    @Timed
    public ResponseEntity<RecruiterDTO> createRecruiter(@RequestBody RecruiterDTO recruiterDTO) throws URISyntaxException {
        log.debug("REST request to save Recruiter : {}", recruiterDTO);
        if (recruiterDTO.getId() != null) {
            throw new BadRequestAlertException("A new recruiter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecruiterDTO result = recruiterService.save(recruiterDTO);
        return ResponseEntity.created(new URI("/api/recruiters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /recruiters : Updates an existing recruiter.
     *
     * @param recruiterDTO the recruiterDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated recruiterDTO,
     * or with status 400 (Bad Request) if the recruiterDTO is not valid,
     * or with status 500 (Internal Server Error) if the recruiterDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/recruiters")
    @Timed
    public ResponseEntity<RecruiterDTO> updateRecruiter(@RequestBody RecruiterDTO recruiterDTO) throws URISyntaxException {
        log.debug("REST request to update Recruiter : {}", recruiterDTO);
        if (recruiterDTO.getId() == null) {
            return createRecruiter(recruiterDTO);
        }
        RecruiterDTO result = recruiterService.save(recruiterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, recruiterDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /recruiters : get all the recruiters.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of recruiters in body
     */
    @GetMapping("/recruiters")
    @Timed
    public ResponseEntity<List<RecruiterDTO>> getAllRecruiters(Pageable pageable) {
        log.debug("REST request to get a page of Recruiters");
        Page<RecruiterDTO> page = recruiterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/recruiters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /recruiters/:id : get the "id" recruiter.
     *
     * @param id the id of the recruiterDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the recruiterDTO, or with status 404 (Not Found)
     */
    @GetMapping("/recruiters/{id}")
    @Timed
    public ResponseEntity<RecruiterDTO> getRecruiter(@PathVariable Long id) {
        log.debug("REST request to get Recruiter : {}", id);
        RecruiterDTO recruiterDTO = recruiterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(recruiterDTO));
    }

    /**
     * DELETE  /recruiters/:id : delete the "id" recruiter.
     *
     * @param id the id of the recruiterDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/recruiters/{id}")
    @Timed
    public ResponseEntity<Void> deleteRecruiter(@PathVariable Long id) {
        log.debug("REST request to delete Recruiter : {}", id);
        recruiterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/recruiters?query=:query : search for the recruiter corresponding
     * to the query.
     *
     * @param query the query of the recruiter search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/recruiters")
    @Timed
    public ResponseEntity<List<RecruiterDTO>> searchRecruiters(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Recruiters for query {}", query);
        Page<RecruiterDTO> page = recruiterService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/recruiters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
