package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.CompanyProfileService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.CompanyProfileDTO;
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
 * REST controller for managing CompanyProfile.
 */
@RestController
@RequestMapping("/api")
public class CompanyProfileResource {

    private final Logger log = LoggerFactory.getLogger(CompanyProfileResource.class);

    private static final String ENTITY_NAME = "companyProfile";

    private final CompanyProfileService companyProfileService;

    public CompanyProfileResource(CompanyProfileService companyProfileService) {
        this.companyProfileService = companyProfileService;
    }

    /**
     * POST  /company-profiles : Create a new companyProfile.
     *
     * @param companyProfileDTO the companyProfileDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new companyProfileDTO, or with status 400 (Bad Request) if the companyProfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/company-profiles")
    @Timed
    public ResponseEntity<CompanyProfileDTO> createCompanyProfile(@RequestBody CompanyProfileDTO companyProfileDTO) throws URISyntaxException {
        log.debug("REST request to save CompanyProfile : {}", companyProfileDTO);
        if (companyProfileDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyProfileDTO result = companyProfileService.save(companyProfileDTO);
        return ResponseEntity.created(new URI("/api/company-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /company-profiles : Updates an existing companyProfile.
     *
     * @param companyProfileDTO the companyProfileDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated companyProfileDTO,
     * or with status 400 (Bad Request) if the companyProfileDTO is not valid,
     * or with status 500 (Internal Server Error) if the companyProfileDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/company-profiles")
    @Timed
    public ResponseEntity<CompanyProfileDTO> updateCompanyProfile(@RequestBody CompanyProfileDTO companyProfileDTO) throws URISyntaxException {
        log.debug("REST request to update CompanyProfile : {}", companyProfileDTO);
        if (companyProfileDTO.getId() == null) {
            return createCompanyProfile(companyProfileDTO);
        }
        CompanyProfileDTO result = companyProfileService.save(companyProfileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, companyProfileDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /company-profiles : get all the companyProfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of companyProfiles in body
     */
    @GetMapping("/company-profiles")
    @Timed
    public ResponseEntity<List<CompanyProfileDTO>> getAllCompanyProfiles(Pageable pageable) {
        log.debug("REST request to get a page of CompanyProfiles");
        Page<CompanyProfileDTO> page = companyProfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/company-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /company-profiles/:id : get the "id" companyProfile.
     *
     * @param id the id of the companyProfileDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the companyProfileDTO, or with status 404 (Not Found)
     */
    @GetMapping("/company-profiles/{id}")
    @Timed
    public ResponseEntity<CompanyProfileDTO> getCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to get CompanyProfile : {}", id);
        CompanyProfileDTO companyProfileDTO = companyProfileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(companyProfileDTO));
    }

    /**
     * DELETE  /company-profiles/:id : delete the "id" companyProfile.
     *
     * @param id the id of the companyProfileDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/company-profiles/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompanyProfile(@PathVariable Long id) {
        log.debug("REST request to delete CompanyProfile : {}", id);
        companyProfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/company-profiles?query=:query : search for the companyProfile corresponding
     * to the query.
     *
     * @param query the query of the companyProfile search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/company-profiles")
    @Timed
    public ResponseEntity<List<CompanyProfileDTO>> searchCompanyProfiles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of CompanyProfiles for query {}", query);
        Page<CompanyProfileDTO> page = companyProfileService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/company-profiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
