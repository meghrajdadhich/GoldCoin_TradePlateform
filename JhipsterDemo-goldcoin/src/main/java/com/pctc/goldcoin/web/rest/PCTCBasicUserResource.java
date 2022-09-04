package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.PCTCBasicUserService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.web.rest.util.PaginationUtil;
import com.pctc.goldcoin.service.dto.PCTCBasicUserDTO;
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
 * REST controller for managing PCTCBasicUser.
 */
@RestController
@RequestMapping("/api")
public class PCTCBasicUserResource {

    private final Logger log = LoggerFactory.getLogger(PCTCBasicUserResource.class);

    private static final String ENTITY_NAME = "pCTCBasicUser";

    private final PCTCBasicUserService pCTCBasicUserService;

    public PCTCBasicUserResource(PCTCBasicUserService pCTCBasicUserService) {
        this.pCTCBasicUserService = pCTCBasicUserService;
    }

    /**
     * POST  /pctc-basic-users : Create a new pCTCBasicUser.
     *
     * @param pCTCBasicUserDTO the pCTCBasicUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pCTCBasicUserDTO, or with status 400 (Bad Request) if the pCTCBasicUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pctc-basic-users")
    @Timed
    public ResponseEntity<PCTCBasicUserDTO> createPCTCBasicUser(@RequestBody PCTCBasicUserDTO pCTCBasicUserDTO) throws URISyntaxException {
        log.debug("REST request to save PCTCBasicUser : {}", pCTCBasicUserDTO);
        if (pCTCBasicUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new pCTCBasicUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PCTCBasicUserDTO result = pCTCBasicUserService.save(pCTCBasicUserDTO);
        return ResponseEntity.created(new URI("/api/pctc-basic-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pctc-basic-users : Updates an existing pCTCBasicUser.
     *
     * @param pCTCBasicUserDTO the pCTCBasicUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pCTCBasicUserDTO,
     * or with status 400 (Bad Request) if the pCTCBasicUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the pCTCBasicUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pctc-basic-users")
    @Timed
    public ResponseEntity<PCTCBasicUserDTO> updatePCTCBasicUser(@RequestBody PCTCBasicUserDTO pCTCBasicUserDTO) throws URISyntaxException {
        log.debug("REST request to update PCTCBasicUser : {}", pCTCBasicUserDTO);
        if (pCTCBasicUserDTO.getId() == null) {
            return createPCTCBasicUser(pCTCBasicUserDTO);
        }
        PCTCBasicUserDTO result = pCTCBasicUserService.save(pCTCBasicUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pCTCBasicUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pctc-basic-users : get all the pCTCBasicUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pCTCBasicUsers in body
     */
    @GetMapping("/pctc-basic-users")
    @Timed
    public ResponseEntity<List<PCTCBasicUserDTO>> getAllPCTCBasicUsers(Pageable pageable) {
        log.debug("REST request to get a page of PCTCBasicUsers");
        Page<PCTCBasicUserDTO> page = pCTCBasicUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pctc-basic-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pctc-basic-users/:id : get the "id" pCTCBasicUser.
     *
     * @param id the id of the pCTCBasicUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pCTCBasicUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pctc-basic-users/{id}")
    @Timed
    public ResponseEntity<PCTCBasicUserDTO> getPCTCBasicUser(@PathVariable Long id) {
        log.debug("REST request to get PCTCBasicUser : {}", id);
        PCTCBasicUserDTO pCTCBasicUserDTO = pCTCBasicUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pCTCBasicUserDTO));
    }

    /**
     * DELETE  /pctc-basic-users/:id : delete the "id" pCTCBasicUser.
     *
     * @param id the id of the pCTCBasicUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pctc-basic-users/{id}")
    @Timed
    public ResponseEntity<Void> deletePCTCBasicUser(@PathVariable Long id) {
        log.debug("REST request to delete PCTCBasicUser : {}", id);
        pCTCBasicUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pctc-basic-users?query=:query : search for the pCTCBasicUser corresponding
     * to the query.
     *
     * @param query the query of the pCTCBasicUser search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pctc-basic-users")
    @Timed
    public ResponseEntity<List<PCTCBasicUserDTO>> searchPCTCBasicUsers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PCTCBasicUsers for query {}", query);
        Page<PCTCBasicUserDTO> page = pCTCBasicUserService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pctc-basic-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
