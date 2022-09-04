package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.CompanyProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CompanyProfile and its DTO CompanyProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CompanyProfileMapper extends EntityMapper<CompanyProfileDTO, CompanyProfile> {

    @Mapping(source = "companyUser.id", target = "companyUserId")
    CompanyProfileDTO toDto(CompanyProfile companyProfile);

    @Mapping(source = "companyUserId", target = "companyUser")
    CompanyProfile toEntity(CompanyProfileDTO companyProfileDTO);

    default CompanyProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        CompanyProfile companyProfile = new CompanyProfile();
        companyProfile.setId(id);
        return companyProfile;
    }
}
