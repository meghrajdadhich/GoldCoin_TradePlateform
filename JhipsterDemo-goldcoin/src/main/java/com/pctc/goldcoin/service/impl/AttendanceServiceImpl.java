package com.pctc.goldcoin.service.impl;

import com.pctc.goldcoin.service.AttendanceService;
import com.pctc.goldcoin.domain.Attendance;
import com.pctc.goldcoin.repository.AttendanceRepository;
import com.pctc.goldcoin.repository.search.AttendanceSearchRepository;
import com.pctc.goldcoin.service.dto.AttendanceDTO;
import com.pctc.goldcoin.service.mapper.AttendanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Attendance.
 */
@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    private final AttendanceSearchRepository attendanceSearchRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper, AttendanceSearchRepository attendanceSearchRepository) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
        this.attendanceSearchRepository = attendanceSearchRepository;
    }

    /**
     * Save a attendance.
     *
     * @param attendanceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AttendanceDTO save(AttendanceDTO attendanceDTO) {
        log.debug("Request to save Attendance : {}", attendanceDTO);
        Attendance attendance = attendanceMapper.toEntity(attendanceDTO);
        attendance = attendanceRepository.save(attendance);
        AttendanceDTO result = attendanceMapper.toDto(attendance);
        attendanceSearchRepository.save(attendance);
        return result;
    }

    /**
     * Get all the attendances.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> findAll() {
        log.debug("Request to get all Attendances");
        return attendanceRepository.findAll().stream()
            .map(attendanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one attendance by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AttendanceDTO findOne(Long id) {
        log.debug("Request to get Attendance : {}", id);
        Attendance attendance = attendanceRepository.findOne(id);
        return attendanceMapper.toDto(attendance);
    }

    /**
     * Delete the attendance by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Attendance : {}", id);
        attendanceRepository.delete(id);
        attendanceSearchRepository.delete(id);
    }

    /**
     * Search for the attendance corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AttendanceDTO> search(String query) {
        log.debug("Request to search Attendances for query {}", query);
        return StreamSupport
            .stream(attendanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(attendanceMapper::toDto)
            .collect(Collectors.toList());
    }
}
