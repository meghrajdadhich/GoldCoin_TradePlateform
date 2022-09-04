package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.ApplyJobService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.ApplyJobDTO;
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
 * REST controller for managing ApplyJob.
 */
@RestController
@RequestMapping("/api")
public class ApplyJobResource {

    private final Logger log = LoggerFactory.getLogger(ApplyJobResource.class);

    private static final String ENTITY_NAME = "applyJob";

    private final ApplyJobService applyJobService;

    public ApplyJobResource(ApplyJobService applyJobService) {
        this.applyJobService = applyJobService;
    }

    /**
     * POST  /apply-jobs : Create a new applyJob.
     *
     * @param applyJobDTO the applyJobDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new applyJobDTO, or with status 400 (Bad Request) if the applyJob has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/apply-jobs")
    @Timed
    public ResponseEntity<ApplyJobDTO> createApplyJob(@RequestBody ApplyJobDTO applyJobDTO) throws URISyntaxException {
        log.debug("REST request to save ApplyJob : {}", applyJobDTO);
        if (applyJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new applyJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplyJobDTO result = applyJobService.save(applyJobDTO);
        return ResponseEntity.created(new URI("/api/apply-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /apply-jobs : Updates an existing applyJob.
     *
     * @param applyJobDTO the applyJobDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated applyJobDTO,
     * or with status 400 (Bad Request) if the applyJobDTO is not valid,
     * or with status 500 (Internal Server Error) if the applyJobDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/apply-jobs")
    @Timed
    public ResponseEntity<ApplyJobDTO> updateApplyJob(@RequestBody ApplyJobDTO applyJobDTO) throws URISyntaxException {
        log.debug("REST request to update ApplyJob : {}", applyJobDTO);
        if (applyJobDTO.getId() == null) {
            return createApplyJob(applyJobDTO);
        }
        ApplyJobDTO result = applyJobService.save(applyJobDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, applyJobDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /apply-jobs : get all the applyJobs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of applyJobs in body
     */
    @GetMapping("/apply-jobs")
    @Timed
    public ResponseEntity<List<ApplyJobDTO>> getAllApplyJobs(Pageable pageable) {
        log.debug("REST request to get a page of ApplyJobs");
        Page<ApplyJobDTO> page = applyJobService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/apply-jobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /apply-jobs/:id : get the "id" applyJob.
     *
     * @param id the id of the applyJobDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the applyJobDTO, or with status 404 (Not Found)
     */
    @GetMapping("/apply-jobs/{id}")
    @Timed
    public ResponseEntity<ApplyJobDTO> getApplyJob(@PathVariable Long id) {
        log.debug("REST request to get ApplyJob : {}", id);
        ApplyJobDTO applyJobDTO = applyJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(applyJobDTO));
    }

    /**
     * DELETE  /apply-jobs/:id : delete the "id" applyJob.
     *
     * @param id the id of the applyJobDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/apply-jobs/{id}")
    @Timed
    public ResponseEntity<Void> deleteApplyJob(@PathVariable Long id) {
        log.debug("REST request to delete ApplyJob : {}", id);
        applyJobService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/apply-jobs?query=:query : search for the applyJob corresponding
     * to the query.
     *
     * @param query the query of the applyJob search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/apply-jobs")
    @Timed
    public ResponseEntity<List<ApplyJobDTO>> searchApplyJobs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ApplyJobs for query {}", query);
        Page<ApplyJobDTO> page = applyJobService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/apply-jobs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
