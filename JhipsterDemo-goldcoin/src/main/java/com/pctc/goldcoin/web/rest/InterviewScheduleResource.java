package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.InterviewScheduleService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.InterviewScheduleDTO;
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
 * REST controller for managing InterviewSchedule.
 */
@RestController
@RequestMapping("/api")
public class InterviewScheduleResource {

    private final Logger log = LoggerFactory.getLogger(InterviewScheduleResource.class);

    private static final String ENTITY_NAME = "interviewSchedule";

    private final InterviewScheduleService interviewScheduleService;

    public InterviewScheduleResource(InterviewScheduleService interviewScheduleService) {
        this.interviewScheduleService = interviewScheduleService;
    }

    /**
     * POST  /interview-schedules : Create a new interviewSchedule.
     *
     * @param interviewScheduleDTO the interviewScheduleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new interviewScheduleDTO, or with status 400 (Bad Request) if the interviewSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/interview-schedules")
    @Timed
    public ResponseEntity<InterviewScheduleDTO> createInterviewSchedule(@RequestBody InterviewScheduleDTO interviewScheduleDTO) throws URISyntaxException {
        log.debug("REST request to save InterviewSchedule : {}", interviewScheduleDTO);
        if (interviewScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new interviewSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InterviewScheduleDTO result = interviewScheduleService.save(interviewScheduleDTO);
        return ResponseEntity.created(new URI("/api/interview-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /interview-schedules : Updates an existing interviewSchedule.
     *
     * @param interviewScheduleDTO the interviewScheduleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated interviewScheduleDTO,
     * or with status 400 (Bad Request) if the interviewScheduleDTO is not valid,
     * or with status 500 (Internal Server Error) if the interviewScheduleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/interview-schedules")
    @Timed
    public ResponseEntity<InterviewScheduleDTO> updateInterviewSchedule(@RequestBody InterviewScheduleDTO interviewScheduleDTO) throws URISyntaxException {
        log.debug("REST request to update InterviewSchedule : {}", interviewScheduleDTO);
        if (interviewScheduleDTO.getId() == null) {
            return createInterviewSchedule(interviewScheduleDTO);
        }
        InterviewScheduleDTO result = interviewScheduleService.save(interviewScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, interviewScheduleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /interview-schedules : get all the interviewSchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of interviewSchedules in body
     */
    @GetMapping("/interview-schedules")
    @Timed
    public ResponseEntity<List<InterviewScheduleDTO>> getAllInterviewSchedules(Pageable pageable) {
        log.debug("REST request to get a page of InterviewSchedules");
        Page<InterviewScheduleDTO> page = interviewScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/interview-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /interview-schedules/:id : get the "id" interviewSchedule.
     *
     * @param id the id of the interviewScheduleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the interviewScheduleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/interview-schedules/{id}")
    @Timed
    public ResponseEntity<InterviewScheduleDTO> getInterviewSchedule(@PathVariable Long id) {
        log.debug("REST request to get InterviewSchedule : {}", id);
        InterviewScheduleDTO interviewScheduleDTO = interviewScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(interviewScheduleDTO));
    }

    /**
     * DELETE  /interview-schedules/:id : delete the "id" interviewSchedule.
     *
     * @param id the id of the interviewScheduleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/interview-schedules/{id}")
    @Timed
    public ResponseEntity<Void> deleteInterviewSchedule(@PathVariable Long id) {
        log.debug("REST request to delete InterviewSchedule : {}", id);
        interviewScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/interview-schedules?query=:query : search for the interviewSchedule corresponding
     * to the query.
     *
     * @param query the query of the interviewSchedule search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/interview-schedules")
    @Timed
    public ResponseEntity<List<InterviewScheduleDTO>> searchInterviewSchedules(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of InterviewSchedules for query {}", query);
        Page<InterviewScheduleDTO> page = interviewScheduleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/interview-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
