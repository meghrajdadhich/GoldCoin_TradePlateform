package com.pctc.goldcoin.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pctc.goldcoin.service.AttendanceService;
import com.pctc.goldcoin.web.rest.errors.BadRequestAlertException;
import com.pctc.goldcoin.web.rest.util.HeaderUtil;
import com.pctc.goldcoin.service.dto.AttendanceDTO;
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
 * REST controller for managing Attendance.
 */
@RestController
@RequestMapping("/api")
public class AttendanceResource {

    private final Logger log = LoggerFactory.getLogger(AttendanceResource.class);

    private static final String ENTITY_NAME = "attendance";

    private final AttendanceService attendanceService;

    public AttendanceResource(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /**
     * POST  /attendances : Create a new attendance.
     *
     * @param attendanceDTO the attendanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attendanceDTO, or with status 400 (Bad Request) if the attendance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attendances")
    @Timed
    public ResponseEntity<AttendanceDTO> createAttendance(@RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to save Attendance : {}", attendanceDTO);
        if (attendanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendanceDTO result = attendanceService.save(attendanceDTO);
        return ResponseEntity.created(new URI("/api/attendances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attendances : Updates an existing attendance.
     *
     * @param attendanceDTO the attendanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attendanceDTO,
     * or with status 400 (Bad Request) if the attendanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the attendanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attendances")
    @Timed
    public ResponseEntity<AttendanceDTO> updateAttendance(@RequestBody AttendanceDTO attendanceDTO) throws URISyntaxException {
        log.debug("REST request to update Attendance : {}", attendanceDTO);
        if (attendanceDTO.getId() == null) {
            return createAttendance(attendanceDTO);
        }
        AttendanceDTO result = attendanceService.save(attendanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attendanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attendances : get all the attendances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of attendances in body
     */
    @GetMapping("/attendances")
    @Timed
    public List<AttendanceDTO> getAllAttendances() {
        log.debug("REST request to get all Attendances");
        return attendanceService.findAll();
        }

    /**
     * GET  /attendances/:id : get the "id" attendance.
     *
     * @param id the id of the attendanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attendanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attendances/{id}")
    @Timed
    public ResponseEntity<AttendanceDTO> getAttendance(@PathVariable Long id) {
        log.debug("REST request to get Attendance : {}", id);
        AttendanceDTO attendanceDTO = attendanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(attendanceDTO));
    }

    /**
     * DELETE  /attendances/:id : delete the "id" attendance.
     *
     * @param id the id of the attendanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attendances/{id}")
    @Timed
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        log.debug("REST request to delete Attendance : {}", id);
        attendanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/attendances?query=:query : search for the attendance corresponding
     * to the query.
     *
     * @param query the query of the attendance search
     * @return the result of the search
     */
    @GetMapping("/_search/attendances")
    @Timed
    public List<AttendanceDTO> searchAttendances(@RequestParam String query) {
        log.debug("REST request to search Attendances for query {}", query);
        return attendanceService.search(query);
    }

}
