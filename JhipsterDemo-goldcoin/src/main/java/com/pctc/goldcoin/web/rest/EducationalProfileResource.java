package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.EducationalProfileService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.service.dto.EducationalProfileDTO;
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
 * REST controller for managing EducationalProfile.
 */
@RestController
@RequestMapping("/api")
public class EducationalProfileResource {

    private final Logger log = LoggerFactory.getLogger(EducationalProfileResource.class);

    private static final String ENTITY_NAME = "educationalProfile";

    private final EducationalProfileService educationalProfileService;

    public EducationalProfileResource(EducationalProfileService educationalProfileService) {
        this.educationalProfileService = educationalProfileService;
    }

    /**
     * POST  /educational-profiles : Create a new educationalProfile.
     *
     * @param educationalProfileDTO the educationalProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new educationalProfileDTO, or with status 400 (Bad Request) if the educationalProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/educational-profiles")
    @Timed
    public ResponseEntity<EducationalProfileDTO> createEducationalProfile(@RequestBody EducationalProfileDTO educationalProfileDTO) throws URISyntaxException {
        log.debug("REST request to save EducationalProfile : {}", educationalProfileDTO);
        if (educationalProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new educationalProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EducationalProfileDTO result = educationalProfileService.save(educationalProfileDTO);
        return ResponseEntity.created(new URI("/api/educational-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /educational-profiles : Updates an existing educationalProfile.
     *
     * @param educationalProfileDTO the educationalProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated educationalProfileDTO,
     * or with status 400 (Bad Request) if the educationalProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the educationalProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/educational-profiles")
    @Timed
    public ResponseEntity<EducationalProfileDTO> updateEducationalProfile(@RequestBody EducationalProfileDTO educationalProfileDTO) throws URISyntaxException {
        log.debug("REST request to update EducationalProfile : {}", educationalProfileDTO);
        if (educationalProfileDTO.getId() == null) {
            return createEducationalProfile(educationalProfileDTO);
        }
        EducationalProfileDTO result = educationalProfileService.save(educationalProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, educationalProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /educational-profiles : get all the educationalProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of educationalProfiles in body
     */
    @GetMapping("/educational-profiles")
    @Timed
    public List<EducationalProfileDTO> getAllEducationalProfiles() {
        log.debug("REST request to get all EducationalProfiles");
        return educationalProfileService.findAll();
        }

    /**
     * GET  /educational-profiles/:id : get the "id" educationalProfile.
     *
     * @param id the id of the educationalProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the educationalProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/educational-profiles/{id}")
    @Timed
    public ResponseEntity<EducationalProfileDTO> getEducationalProfile(@PathVariable Long id) {
        log.debug("REST request to get EducationalProfile : {}", id);
        EducationalProfileDTO educationalProfileDTO = educationalProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(educationalProfileDTO));
    }

    /**
     * DELETE  /educational-profiles/:id : delete the "id" educationalProfile.
     *
     * @param id the id of the educationalProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/educational-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteEducationalProfile(@PathVariable Long id) {
        log.debug("REST request to delete EducationalProfile : {}", id);
        educationalProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/educational-profiles?query=:query : search for the educationalProfile corresponding
     * to the query.
     *
     * @param query the query of the educationalProfile search
     * @return the result of the search
     */
    @GetMapping("/_search/educational-profiles")
    @Timed
    public List<EducationalProfileDTO> searchEducationalProfiles(@RequestParam String query) {
        log.debug("REST request to search EducationalProfiles for query {}", query);
        return educationalProfileService.search(query);
    }

}
