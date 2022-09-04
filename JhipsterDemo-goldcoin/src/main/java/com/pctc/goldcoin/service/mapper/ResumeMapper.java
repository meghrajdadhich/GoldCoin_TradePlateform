package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.ResumeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Resume and its DTO ResumeDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ResumeMapper extends EntityMapper<ResumeDTO, Resume> {

    @Mapping(source = "resumeUser.id", target = "resumeUserId")
    ResumeDTO toDto(Resume resume);

    @Mapping(source = "resumeUserId", target = "resumeUser")
    @Mapping(target = "resumeEducations", ignore = true)
    @Mapping(target = "resumeProfessinals", ignore = true)
    Resume toEntity(ResumeDTO resumeDTO);

    default Resume fromId(Long id) {
        if (id == null) {
            return null;
        }
        Resume resume = new Resume();
        resume.setId(id);
        return resume;
    }
}
