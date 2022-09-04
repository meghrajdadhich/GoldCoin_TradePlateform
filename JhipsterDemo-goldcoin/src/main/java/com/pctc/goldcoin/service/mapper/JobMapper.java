package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.JobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Job and its DTO JobDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyProfileMapper.class, LocationMapper.class, RecruiterMapper.class, JobSkillsMapper.class})
public interface JobMapper extends EntityMapper<JobDTO, Job> {

    @Mapping(source = "jobReviewer.id", target = "jobReviewerId")
    @Mapping(source = "jobCompany.id", target = "jobCompanyId")
    @Mapping(source = "jobLocation.id", target = "jobLocationId")
    @Mapping(source = "recruiter.id", target = "recruiterId")
    JobDTO toDto(Job job);

    @Mapping(source = "jobReviewerId", target = "jobReviewer")
    @Mapping(source = "jobCompanyId", target = "jobCompany")
    @Mapping(source = "jobLocationId", target = "jobLocation")
    @Mapping(source = "recruiterId", target = "recruiter")
    Job toEntity(JobDTO jobDTO);

    default Job fromId(Long id) {
        if (id == null) {
            return null;
        }
        Job job = new Job();
        job.setId(id);
        return job;
    }
}
