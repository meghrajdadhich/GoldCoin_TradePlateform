package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.EducationalProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity EducationalProfile and its DTO EducationalProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {ResumeMapper.class})
public interface EducationalProfileMapper extends EntityMapper<EducationalProfileDTO, EducationalProfile> {

    @Mapping(source = "resume.id", target = "resumeId")
    EducationalProfileDTO toDto(EducationalProfile educationalProfile);

    @Mapping(source = "resumeId", target = "resume")
    EducationalProfile toEntity(EducationalProfileDTO educationalProfileDTO);

    default EducationalProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        EducationalProfile educationalProfile = new EducationalProfile();
        educationalProfile.setId(id);
        return educationalProfile;
    }
}
