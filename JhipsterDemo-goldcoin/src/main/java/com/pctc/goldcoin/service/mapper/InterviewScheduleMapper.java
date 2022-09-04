package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.InterviewScheduleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InterviewSchedule and its DTO InterviewScheduleDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, LocationMapper.class, JobMapper.class})
public interface InterviewScheduleMapper extends EntityMapper<InterviewScheduleDTO, InterviewSchedule> {

    @Mapping(source = "interviewerHR.id", target = "interviewerHRId")
    @Mapping(source = "interviewerCons.id", target = "interviewerConsId")
    @Mapping(source = "interviewerCanditate.id", target = "interviewerCanditateId")
    @Mapping(source = "interviewerCordinator.id", target = "interviewerCordinatorId")
    @Mapping(source = "interviewLocation.id", target = "interviewLocationId")
    @Mapping(source = "jobInterview.id", target = "jobInterviewId")
    @Mapping(source = "byUser.id", target = "byUserId")
    @Mapping(source = "candidateUser.id", target = "candidateUserId")
    @Mapping(source = "recruiterIUser.id", target = "recruiterIUserId")
    @Mapping(source = "cordinatorUser.id", target = "cordinatorUserId")
    @Mapping(source = "consultancyUser.id", target = "consultancyUserId")
    @Mapping(source = "companyUser.id", target = "companyUserId")
    InterviewScheduleDTO toDto(InterviewSchedule interviewSchedule);

    @Mapping(source = "interviewerHRId", target = "interviewerHR")
    @Mapping(source = "interviewerConsId", target = "interviewerCons")
    @Mapping(source = "interviewerCanditateId", target = "interviewerCanditate")
    @Mapping(source = "interviewerCordinatorId", target = "interviewerCordinator")
    @Mapping(source = "interviewLocationId", target = "interviewLocation")
    @Mapping(source = "jobInterviewId", target = "jobInterview")
    @Mapping(source = "byUserId", target = "byUser")
    @Mapping(source = "candidateUserId", target = "candidateUser")
    @Mapping(source = "recruiterIUserId", target = "recruiterIUser")
    @Mapping(source = "cordinatorUserId", target = "cordinatorUser")
    @Mapping(source = "consultancyUserId", target = "consultancyUser")
    @Mapping(source = "companyUserId", target = "companyUser")
    InterviewSchedule toEntity(InterviewScheduleDTO interviewScheduleDTO);

    default InterviewSchedule fromId(Long id) {
        if (id == null) {
            return null;
        }
        InterviewSchedule interviewSchedule = new InterviewSchedule();
        interviewSchedule.setId(id);
        return interviewSchedule;
    }
}
