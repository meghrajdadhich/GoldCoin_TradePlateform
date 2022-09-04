package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.CandidateProfileDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CandidateProfile and its DTO CandidateProfileDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CandidateProfileMapper extends EntityMapper<CandidateProfileDTO, CandidateProfile> {

    @Mapping(source = "candidateUser.id", target = "candidateUserId")
    CandidateProfileDTO toDto(CandidateProfile candidateProfile);

    @Mapping(source = "candidateUserId", target = "candidateUser")
    CandidateProfile toEntity(CandidateProfileDTO candidateProfileDTO);

    default CandidateProfile fromId(Long id) {
        if (id == null) {
            return null;
        }
        CandidateProfile candidateProfile = new CandidateProfile();
        candidateProfile.setId(id);
        return candidateProfile;
    }
}
