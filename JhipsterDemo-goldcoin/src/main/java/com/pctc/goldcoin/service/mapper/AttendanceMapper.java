package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.AttendanceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Attendance and its DTO AttendanceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttendanceMapper extends EntityMapper<AttendanceDTO, Attendance> {



    default Attendance fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setId(id);
        return attendance;
    }
}
