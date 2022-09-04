package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.ProfessionalProfileService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.service.dto.ProfessionalProfileDTO;
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
 * REST controller for managing ProfessionalProfile.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalProfileResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalProfileResource.class);

    private static final String ENTITY_NAME = "professionalProfile";

    private final ProfessionalProfileService professionalProfileService;

    public ProfessionalProfileResource(ProfessionalProfileService professionalProfileService) {
        this.professionalProfileService = professionalProfileService;
    }

    /**
     * POST  /professional-profiles : Create a new professionalProfile.
     *
     * @param professionalProfileDTO the professionalProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professionalProfileDTO, or with status 400 (Bad Request) if the professionalProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professional-profiles")
    @Timed
    public ResponseEntity<ProfessionalProfileDTO> createProfessionalProfile(@RequestBody ProfessionalProfileDTO professionalProfileDTO) throws URISyntaxException {
        log.debug("REST request to save ProfessionalProfile : {}", professionalProfileDTO);
        if (professionalProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new professionalProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfessionalProfileDTO result = professionalProfileService.save(professionalProfileDTO);
        return ResponseEntity.created(new URI("/api/professional-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professional-profiles : Updates an existing professionalProfile.
     *
     * @param professionalProfileDTO the professionalProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professionalProfileDTO,
     * or with status 400 (Bad Request) if the professionalProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the professionalProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professional-profiles")
    @Timed
    public ResponseEntity<ProfessionalProfileDTO> updateProfessionalProfile(@RequestBody ProfessionalProfileDTO professionalProfileDTO) throws URISyntaxException {
        log.debug("REST request to update ProfessionalProfile : {}", professionalProfileDTO);
        if (professionalProfileDTO.getId() == null) {
            return createProfessionalProfile(professionalProfileDTO);
        }
        ProfessionalProfileDTO result = professionalProfileService.save(professionalProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professionalProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professional-profiles : get all the professionalProfiles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of professionalProfiles in body
     */
    @GetMapping("/professional-profiles")
    @Timed
    public List<ProfessionalProfileDTO> getAllProfessionalProfiles() {
        log.debug("REST request to get all ProfessionalProfiles");
        return professionalProfileService.findAll();
        }

    /**
     * GET  /professional-profiles/:id : get the "id" professionalProfile.
     *
     * @param id the id of the professionalProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professionalProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/professional-profiles/{id}")
    @Timed
    public ResponseEntity<ProfessionalProfileDTO> getProfessionalProfile(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalProfile : {}", id);
        ProfessionalProfileDTO professionalProfileDTO = professionalProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(professionalProfileDTO));
    }

    /**
     * DELETE  /professional-profiles/:id : delete the "id" professionalProfile.
     *
     * @param id the id of the professionalProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professional-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfessionalProfile(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalProfile : {}", id);
        professionalProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/professional-profiles?query=:query : search for the professionalProfile corresponding
     * to the query.
     *
     * @param query the query of the professionalProfile search
     * @return the result of the search
     */
    @GetMapping("/_search/professional-profiles")
    @Timed
    public List<ProfessionalProfileDTO> searchProfessionalProfiles(@RequestParam String query) {
        log.debug("REST request to search ProfessionalProfiles for query {}", query);
        return professionalProfileService.search(query);
    }

}
