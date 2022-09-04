package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.ConsultancyProfileService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.ConsultancyProfileDTO;
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
 * REST controller for managing ConsultancyProfile.
 */
@RestController
@RequestMapping("/api")
public class ConsultancyProfileResource {

    private final Logger log = LoggerFactory.getLogger(ConsultancyProfileResource.class);

    private static final String ENTITY_NAME = "consultancyProfile";

    private final ConsultancyProfileService consultancyProfileService;

    public ConsultancyProfileResource(ConsultancyProfileService consultancyProfileService) {
        this.consultancyProfileService = consultancyProfileService;
    }

    /**
     * POST  /consultancy-profiles : Create a new consultancyProfile.
     *
     * @param consultancyProfileDTO the consultancyProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultancyProfileDTO, or with status 400 (Bad Request) if the consultancyProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultancy-profiles")
    @Timed
    public ResponseEntity<ConsultancyProfileDTO> createConsultancyProfile(@RequestBody ConsultancyProfileDTO consultancyProfileDTO) throws URISyntaxException {
        log.debug("REST request to save ConsultancyProfile : {}", consultancyProfileDTO);
        if (consultancyProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultancyProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultancyProfileDTO result = consultancyProfileService.save(consultancyProfileDTO);
        return ResponseEntity.created(new URI("/api/consultancy-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultancy-profiles : Updates an existing consultancyProfile.
     *
     * @param consultancyProfileDTO the consultancyProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultancyProfileDTO,
     * or with status 400 (Bad Request) if the consultancyProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultancyProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultancy-profiles")
    @Timed
    public ResponseEntity<ConsultancyProfileDTO> updateConsultancyProfile(@RequestBody ConsultancyProfileDTO consultancyProfileDTO) throws URISyntaxException {
        log.debug("REST request to update ConsultancyProfile : {}", consultancyProfileDTO);
        if (consultancyProfileDTO.getId() == null) {
            return createConsultancyProfile(consultancyProfileDTO);
        }
        ConsultancyProfileDTO result = consultancyProfileService.save(consultancyProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultancyProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultancy-profiles : get all the consultancyProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultancyProfiles in body
     */
    @GetMapping("/consultancy-profiles")
    @Timed
    public ResponseEntity<List<ConsultancyProfileDTO>> getAllConsultancyProfiles(Pageable pageable) {
        log.debug("REST request to get a page of ConsultancyProfiles");
        Page<ConsultancyProfileDTO> page = consultancyProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultancy-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultancy-profiles/:id : get the "id" consultancyProfile.
     *
     * @param id the id of the consultancyProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultancyProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consultancy-profiles/{id}")
    @Timed
    public ResponseEntity<ConsultancyProfileDTO> getConsultancyProfile(@PathVariable Long id) {
        log.debug("REST request to get ConsultancyProfile : {}", id);
        ConsultancyProfileDTO consultancyProfileDTO = consultancyProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultancyProfileDTO));
    }

    /**
     * DELETE  /consultancy-profiles/:id : delete the "id" consultancyProfile.
     *
     * @param id the id of the consultancyProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultancy-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultancyProfile(@PathVariable Long id) {
        log.debug("REST request to delete ConsultancyProfile : {}", id);
        consultancyProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/consultancy-profiles?query=:query : search for the consultancyProfile corresponding
     * to the query.
     *
     * @param query the query of the consultancyProfile search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/consultancy-profiles")
    @Timed
    public ResponseEntity<List<ConsultancyProfileDTO>> searchConsultancyProfiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ConsultancyProfiles for query {}", query);
        Page<ConsultancyProfileDTO> page = consultancyProfileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/consultancy-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
