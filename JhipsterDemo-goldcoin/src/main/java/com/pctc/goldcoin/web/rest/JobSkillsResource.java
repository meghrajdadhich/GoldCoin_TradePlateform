package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.JobSkillsService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.service.dto.JobSkillsDTO;
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
 * REST controller for managing JobSkills.
 */
@RestController
@RequestMapping("/api")
public class JobSkillsResource {

    private final Logger log = LoggerFactory.getLogger(JobSkillsResource.class);

    private static final String ENTITY_NAME = "jobSkills";

    private final JobSkillsService jobSkillsService;

    public JobSkillsResource(JobSkillsService jobSkillsService) {
        this.jobSkillsService = jobSkillsService;
    }

    /**
     * POST  /job-skills : Create a new jobSkills.
     *
     * @param jobSkillsDTO the jobSkillsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobSkillsDTO, or with status 400 (Bad Request) if the jobSkills has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-skills")
    @Timed
    public ResponseEntity<JobSkillsDTO> createJobSkills(@RequestBody JobSkillsDTO jobSkillsDTO) throws URISyntaxException {
        log.debug("REST request to save JobSkills : {}", jobSkillsDTO);
        if (jobSkillsDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobSkills cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobSkillsDTO result = jobSkillsService.save(jobSkillsDTO);
        return ResponseEntity.created(new URI("/api/job-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-skills : Updates an existing jobSkills.
     *
     * @param jobSkillsDTO the jobSkillsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobSkillsDTO,
     * or with status 400 (Bad Request) if the jobSkillsDTO is not valid,
     * or with status 500 (Internal Server Error) if the jobSkillsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-skills")
    @Timed
    public ResponseEntity<JobSkillsDTO> updateJobSkills(@RequestBody JobSkillsDTO jobSkillsDTO) throws URISyntaxException {
        log.debug("REST request to update JobSkills : {}", jobSkillsDTO);
        if (jobSkillsDTO.getId() == null) {
            return createJobSkills(jobSkillsDTO);
        }
        JobSkillsDTO result = jobSkillsService.save(jobSkillsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, jobSkillsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-skills : get all the jobSkills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobSkills in body
     */
    @GetMapping("/job-skills")
    @Timed
    public List<JobSkillsDTO> getAllJobSkills() {
        log.debug("REST request to get all JobSkills");
        return jobSkillsService.findAll();
        }

    /**
     * GET  /job-skills/:id : get the "id" jobSkills.
     *
     * @param id the id of the jobSkillsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobSkillsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/job-skills/{id}")
    @Timed
    public ResponseEntity<JobSkillsDTO> getJobSkills(@PathVariable Long id) {
        log.debug("REST request to get JobSkills : {}", id);
        JobSkillsDTO jobSkillsDTO = jobSkillsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(jobSkillsDTO));
    }

    /**
     * DELETE  /job-skills/:id : delete the "id" jobSkills.
     *
     * @param id the id of the jobSkillsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-skills/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobSkills(@PathVariable Long id) {
        log.debug("REST request to delete JobSkills : {}", id);
        jobSkillsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/job-skills?query=:query : search for the jobSkills corresponding
     * to the query.
     *
     * @param query the query of the jobSkills search
     * @return the result of the search
     */
    @GetMapping("/_search/job-skills")
    @Timed
    public List<JobSkillsDTO> searchJobSkills(@RequestParam String query) {
        log.debug("REST request to search JobSkills for query {}", query);
        return jobSkillsService.search(query);
    }

}
