package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.ConsultancyProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ConsultancyProfile and its DTO ConsultancyProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ConsultancyProfileMapper extends EntityMapper<ConsultancyProfileDTO, ConsultancyProfile> {

    @Mapping(source = "consUser.id", target = "consUserId")
    ConsultancyProfileDTO toDto(ConsultancyProfile consultancyProfile);

    @Mapping(source = "consUserId", target = "consUser")
    ConsultancyProfile toEntity(ConsultancyProfileDTO consultancyProfileDTO);

    default ConsultancyProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        ConsultancyProfile consultancyProfile = new ConsultancyProfile();
        consultancyProfile.setId(id);
        return consultancyProfile;
    }
}
