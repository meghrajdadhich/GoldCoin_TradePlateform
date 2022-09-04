package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.JobSkillsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity JobSkills and its DTO JobSkillsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobSkillsMapper extends EntityMapper<JobSkillsDTO, JobSkills> {



    default JobSkills fromId(Long id) {
        if (id == null) {
            return null;
        }
        JobSkills jobSkills = new JobSkills();
        jobSkills.setId(id);
        return jobSkills;
    }
}
