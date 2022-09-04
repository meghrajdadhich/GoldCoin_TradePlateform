package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the InterviewSchedule entity.
 */
public class InterviewScheduleDTO implements Serializable {

    private Long id;

    private Long intschId;

    private String intschTime;

    private String intschStatus;

    private Long interviewerHRId;

    private Long interviewerConsId;

    private Long interviewerCanditateId;

    private Long interviewerCordinatorId;

    private Long interviewLocationId;

    private Long jobInterviewId;

    private Long byUserId;

    private Long candidateUserId;

    private Long recruiterIUserId;

    private Long cordinatorUserId;

    private Long consultancyUserId;

    private Long companyUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntschId() {
        return intschId;
    }

    public void setIntschId(Long intschId) {
        this.intschId = intschId;
    }

    public String getIntschTime() {
        return intschTime;
    }

    public void setIntschTime(String intschTime) {
        this.intschTime = intschTime;
    }

    public String getIntschStatus() {
        return intschStatus;
    }

    public void setIntschStatus(String intschStatus) {
        this.intschStatus = intschStatus;
    }

    public Long getInterviewerHRId() {
        return interviewerHRId;
    }

    public void setInterviewerHRId(Long userId) {
        this.interviewerHRId = userId;
    }

    public Long getInterviewerConsId() {
        return interviewerConsId;
    }

    public void setInterviewerConsId(Long userId) {
        this.interviewerConsId = userId;
    }

    public Long getInterviewerCanditateId() {
        return interviewerCanditateId;
    }

    public void setInterviewerCanditateId(Long userId) {
        this.interviewerCanditateId = userId;
    }

    public Long getInterviewerCordinatorId() {
        return interviewerCordinatorId;
    }

    public void setInterviewerCordinatorId(Long userId) {
        this.interviewerCordinatorId = userId;
    }

    public Long getInterviewLocationId() {
        return interviewLocationId;
    }

    public void setInterviewLocationId(Long locationId) {
        this.interviewLocationId = locationId;
    }

    public Long getJobInterviewId() {
        return jobInterviewId;
    }

    public void setJobInterviewId(Long jobId) {
        this.jobInterviewId = jobId;
    }

    public Long getByUserId() {
        return byUserId;
    }

    public void setByUserId(Long userId) {
        this.byUserId = userId;
    }

    public Long getCandidateUserId() {
        return candidateUserId;
    }

    public void setCandidateUserId(Long userId) {
        this.candidateUserId = userId;
    }

    public Long getRecruiterIUserId() {
        return recruiterIUserId;
    }

    public void setRecruiterIUserId(Long userId) {
        this.recruiterIUserId = userId;
    }

    public Long getCordinatorUserId() {
        return cordinatorUserId;
    }

    public void setCordinatorUserId(Long userId) {
        this.cordinatorUserId = userId;
    }

    public Long getConsultancyUserId() {
        return consultancyUserId;
    }

    public void setConsultancyUserId(Long userId) {
        this.consultancyUserId = userId;
    }

    public Long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Long userId) {
        this.companyUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InterviewScheduleDTO interviewScheduleDTO = (InterviewScheduleDTO) o;
        if(interviewScheduleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interviewScheduleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterviewScheduleDTO{" +
            "id=" + getId() +
            ", intschId=" + getIntschId() +
            ", intschTime='" + getIntschTime() + "'" +
            ", intschStatus='" + getIntschStatus() + "'" +
            "}";
    }
}
