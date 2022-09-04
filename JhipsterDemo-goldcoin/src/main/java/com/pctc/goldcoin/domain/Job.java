package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Job.
 */
@Entity
@Table(name = "job")
@Document(indexName = "job")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "job_role")
    private String jobRole;

    @Column(name = "job_type")
    private String jobType;

    @Column(name = "job_industry")
    private String jobIndustry;

    @Column(name = "job_salary")
    private String jobSalary;

    @Column(name = "job_start_date")
    private Instant jobStartDate;

    @Column(name = "job_end_date")
    private Instant jobEndDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User jobReviewer;

    @OneToOne
    @JoinColumn(unique = true)
    private CompanyProfile jobCompany;

    @OneToOne
    @JoinColumn(unique = true)
    private Location jobLocation;

    @ManyToOne
    private Recruiter recruiter;

    @ManyToMany
    @JoinTable(name = "job_skills",
               joinColumns = @JoinColumn(name="jobs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="skills_id", referencedColumnName="id"))
    private Set<JobSkills> skills = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJobId() {
        return jobId;
    }

    public Job jobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Job jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public Job jobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
        return this;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRole() {
        return jobRole;
    }

    public Job jobRole(String jobRole) {
        this.jobRole = jobRole;
        return this;
    }

    public void setJobRole(String jobRole) {
        this.jobRole = jobRole;
    }

    public String getJobType() {
        return jobType;
    }

    public Job jobType(String jobType) {
        this.jobType = jobType;
        return this;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobIndustry() {
        return jobIndustry;
    }

    public Job jobIndustry(String jobIndustry) {
        this.jobIndustry = jobIndustry;
        return this;
    }

    public void setJobIndustry(String jobIndustry) {
        this.jobIndustry = jobIndustry;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public Job jobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
        return this;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public Instant getJobStartDate() {
        return jobStartDate;
    }

    public Job jobStartDate(Instant jobStartDate) {
        this.jobStartDate = jobStartDate;
        return this;
    }

    public void setJobStartDate(Instant jobStartDate) {
        this.jobStartDate = jobStartDate;
    }

    public Instant getJobEndDate() {
        return jobEndDate;
    }

    public Job jobEndDate(Instant jobEndDate) {
        this.jobEndDate = jobEndDate;
        return this;
    }

    public void setJobEndDate(Instant jobEndDate) {
        this.jobEndDate = jobEndDate;
    }

    public User getJobReviewer() {
        return jobReviewer;
    }

    public Job jobReviewer(User user) {
        this.jobReviewer = user;
        return this;
    }

    public void setJobReviewer(User user) {
        this.jobReviewer = user;
    }

    public CompanyProfile getJobCompany() {
        return jobCompany;
    }

    public Job jobCompany(CompanyProfile companyProfile) {
        this.jobCompany = companyProfile;
        return this;
    }

    public void setJobCompany(CompanyProfile companyProfile) {
        this.jobCompany = companyProfile;
    }

    public Location getJobLocation() {
        return jobLocation;
    }

    public Job jobLocation(Location location) {
        this.jobLocation = location;
        return this;
    }

    public void setJobLocation(Location location) {
        this.jobLocation = location;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public Job recruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
        return this;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    public Set<JobSkills> getSkills() {
        return skills;
    }

    public Job skills(Set<JobSkills> jobSkills) {
        this.skills = jobSkills;
        return this;
    }

    public Job addSkills(JobSkills jobSkills) {
        this.skills.add(jobSkills);
        return this;
    }

    public Job removeSkills(JobSkills jobSkills) {
        this.skills.remove(jobSkills);
        return this;
    }

    public void setSkills(Set<JobSkills> jobSkills) {
        this.skills = jobSkills;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Job job = (Job) o;
        if (job.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), job.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Job{" +
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
