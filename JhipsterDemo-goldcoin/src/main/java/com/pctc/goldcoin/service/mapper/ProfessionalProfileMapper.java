package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.ProfessionalProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProfessionalProfile and its DTO ProfessionalProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {ResumeMapper.class})
public interface ProfessionalProfileMapper extends EntityMapper<ProfessionalProfileDTO, ProfessionalProfile> {

    @Mapping(source = "resume.id", target = "resumeId")
    ProfessionalProfileDTO toDto(ProfessionalProfile professionalProfile);

    @Mapping(source = "resumeId", target = "resume")
    ProfessionalProfile toEntity(ProfessionalProfileDTO professionalProfileDTO);

    default ProfessionalProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProfessionalProfile professionalProfile = new ProfessionalProfile();
        professionalProfile.setId(id);
        return professionalProfile;
    }
}
