package com.pctc.goldcoin.service;

import com.pctc.goldcoin.service.dto.AttendanceDTO;
import java.util.List;

/**
 * Service Interface for managing Attendance.
 */
public interface AttendanceService {

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save
     * @return the persisted entity
     */
    AttendanceDTO save(AttendanceDTO attendanceDTO);

    /**
     * Get all the attendances.
     *
     * @return the list of entities
     */
    List<AttendanceDTO> findAll();

    /**
     * Get the "id" attendance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    AttendanceDTO findOne(Long id);

    /**
     * Delete the "id" attendance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the attendance corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<AttendanceDTO> search(String query);
}
