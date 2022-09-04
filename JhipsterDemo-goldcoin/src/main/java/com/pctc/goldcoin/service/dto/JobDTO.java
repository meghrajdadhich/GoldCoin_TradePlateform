package com.pctc.goldcoin.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Job entity.
 */
public class JobDTO implements Serializable {

    private Long id;

    private Long jobId;

    private String jobTitle;

    private String jobDescription;

    private String jobRole;

    private String jobType;

    private String jobIndustry;

    private String jobSalary;

    private Instant jobStartDate;

    private Instant jobEndDate;

    private Long jobReviewerId;

    private Long jobCompanyId;

    private Long jobLocationId;

    private Long recruiterId;

    private Set<JobSkillsDTO> skills = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRole() {
        return jobRole;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobIndustry() {
        return jobIndustry;
    }

    public void setJobIndustry(String jobIndustry) {
        this.jobIndustry = jobIndustry;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public Instant getJobStartDate() {
        return jobStartDate;
    }

    public void setJobStartDate(Instant jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public Instant getJobEndDate() {
        return jobEndDate;
    }

    public void setJobEndDate(Instant jobEndDate) {
        this.jobEndDate = jobEndDate;
    }

    public Long getJobReviewerId() {
        return jobReviewerId;
    }

    public void setJobReviewerId(Long userId) {
        this.jobReviewerId = userId;
    }

    public Long getJobCompanyId() {
        return jobCompanyId;
    }

    public void setJobCompanyId(Long companyProfileId) {
        this.jobCompanyId = companyProfileId;
    }

    public Long getJobLocationId() {
        return jobLocationId;
    }

    public void setJobLocationId(Long locationId) {
        this.jobLocationId = locationId;
    }

    public Long getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Long recruiterId) {
        this.recruiterId = recruiterId;
    }

    public Set<JobSkillsDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<JobSkillsDTO> jobSkills) {
        this.skills = jobSkills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobDTO jobDTO = (JobDTO) o;
        if(jobDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobDTO{" +
            "id=" + getId() +
            ", jobId=" + getJobId() +
            ", jobTitle='" + getJobTitle() + "'" +
            ", jobDescription='" + getJobDescription() + "'" +
            ", jobRole='" + getJobRole() + "'" +
            ", jobType='" + getJobType() + "'" +
            ", jobIndustry='" + getJobIndustry() + "'" +
            ", jobSalary='" + getJobSalary() + "'" +
            ", jobStartDate='" + getJobStartDate() + "'" +
            ", jobEndDate='" + getJobEndDate() + "'" +
            "}";
    }
}
