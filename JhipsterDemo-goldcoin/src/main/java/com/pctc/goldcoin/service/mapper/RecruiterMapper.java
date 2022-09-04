package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.RecruiterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Recruiter and its DTO RecruiterDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface RecruiterMapper extends EntityMapper<RecruiterDTO, Recruiter> {

    @Mapping(source = "users.id", target = "usersId")
    RecruiterDTO toDto(Recruiter recruiter);

    @Mapping(source = "usersId", target = "users")
    @Mapping(target = "jobTasks", ignore = true)
    Recruiter toEntity(RecruiterDTO recruiterDTO);

    default Recruiter fromId(Long id) {
        if (id == null) {
            return null;
        }
        Recruiter recruiter = new Recruiter();
        recruiter.setId(id);
        return recruiter;
    }
}
