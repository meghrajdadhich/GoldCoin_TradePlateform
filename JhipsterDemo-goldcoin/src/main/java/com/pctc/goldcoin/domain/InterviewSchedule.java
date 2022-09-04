package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A InterviewSchedule.
 */
@Entity
@Table(name = "interview_schedule")
@Document(indexName = "interviewschedule")
public class InterviewSchedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intsch_id")
    private Long intschId;

    @Column(name = "intsch_time")
    private String intschTime;

    @Column(name = "intsch_status")
    private String intschStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private User interviewerHR;

    @OneToOne
    @JoinColumn(unique = true)
    private User interviewerCons;

    @OneToOne
    @JoinColumn(unique = true)
    private User interviewerCanditate;

    @OneToOne
    @JoinColumn(unique = true)
    private User interviewerCordinator;

    @OneToOne
    @JoinColumn(unique = true)
    private Location interviewLocation;

    @OneToOne
    @JoinColumn(unique = true)
    private Job jobInterview;

    @OneToOne
    @JoinColumn(unique = true)
    private User byUser;

    @OneToOne
    @JoinColumn(unique = true)
    private User candidateUser;

    @OneToOne
    @JoinColumn(unique = true)
    private User recruiterIUser;

    @OneToOne
    @JoinColumn(unique = true)
    private User cordinatorUser;

    @OneToOne
    @JoinColumn(unique = true)
    private User consultancyUser;

    @OneToOne
    @JoinColumn(unique = true)
    private User companyUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIntschId() {
        return intschId;
    }

    public InterviewSchedule intschId(Long intschId) {
        this.intschId = intschId;
        return this;
    }

    public void setIntschId(Long intschId) {
        this.intschId = intschId;
    }

    public String getIntschTime() {
        return intschTime;
    }

    public InterviewSchedule intschTime(String intschTime) {
        this.intschTime = intschTime;
        return this;
    }

    public void setIntschTime(String intschTime) {
        this.intschTime = intschTime;
    }

    public String getIntschStatus() {
        return intschStatus;
    }

    public InterviewSchedule intschStatus(String intschStatus) {
        this.intschStatus = intschStatus;
        return this;
    }

    public void setIntschStatus(String intschStatus) {
        this.intschStatus = intschStatus;
    }

    public User getInterviewerHR() {
        return interviewerHR;
    }

    public InterviewSchedule interviewerHR(User user) {
        this.interviewerHR = user;
        return this;
    }

    public void setInterviewerHR(User user) {
        this.interviewerHR = user;
    }

    public User getInterviewerCons() {
        return interviewerCons;
    }

    public InterviewSchedule interviewerCons(User user) {
        this.interviewerCons = user;
        return this;
    }

    public void setInterviewerCons(User user) {
        this.interviewerCons = user;
    }

    public User getInterviewerCanditate() {
        return interviewerCanditate;
    }

    public InterviewSchedule interviewerCanditate(User user) {
        this.interviewerCanditate = user;
        return this;
    }

    public void setInterviewerCanditate(User user) {
        this.interviewerCanditate = user;
    }

    public User getInterviewerCordinator() {
        return interviewerCordinator;
    }

    public InterviewSchedule interviewerCordinator(User user) {
        this.interviewerCordinator = user;
        return this;
    }

    public void setInterviewerCordinator(User user) {
        this.interviewerCordinator = user;
    }

    public Location getInterviewLocation() {
        return interviewLocation;
    }

    public InterviewSchedule interviewLocation(Location location) {
        this.interviewLocation = location;
        return this;
    }

    public void setInterviewLocation(Location location) {
        this.interviewLocation = location;
    }

    public Job getJobInterview() {
        return jobInterview;
    }

    public InterviewSchedule jobInterview(Job job) {
        this.jobInterview = job;
        return this;
    }

    public void setJobInterview(Job job) {
        this.jobInterview = job;
    }

    public User getByUser() {
        return byUser;
    }

    public InterviewSchedule byUser(User user) {
        this.byUser = user;
        return this;
    }

    public void setByUser(User user) {
        this.byUser = user;
    }

    public User getCandidateUser() {
        return candidateUser;
    }

    public InterviewSchedule candidateUser(User user) {
        this.candidateUser = user;
        return this;
    }

    public void setCandidateUser(User user) {
        this.candidateUser = user;
    }

    public User getRecruiterIUser() {
        return recruiterIUser;
    }

    public InterviewSchedule recruiterIUser(User user) {
        this.recruiterIUser = user;
        return this;
    }

    public void setRecruiterIUser(User user) {
        this.recruiterIUser = user;
    }

    public User getCordinatorUser() {
        return cordinatorUser;
    }

    public InterviewSchedule cordinatorUser(User user) {
        this.cordinatorUser = user;
        return this;
    }

    public void setCordinatorUser(User user) {
        this.cordinatorUser = user;
    }

    public User getConsultancyUser() {
        return consultancyUser;
    }

    public InterviewSchedule consultancyUser(User user) {
        this.consultancyUser = user;
        return this;
    }

    public void setConsultancyUser(User user) {
        this.consultancyUser = user;
    }

    public User getCompanyUser() {
        return companyUser;
    }

    public InterviewSchedule companyUser(User user) {
        this.companyUser = user;
        return this;
    }

    public void setCompanyUser(User user) {
        this.companyUser = user;
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
        InterviewSchedule interviewSchedule = (InterviewSchedule) o;
        if (interviewSchedule.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), interviewSchedule.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InterviewSchedule{" +
            "id=" + getId() +
            ", intschId=" + getIntschId() +
            ", intschTime='" + getIntschTime() + "'" +
            ", intschStatus='" + getIntschStatus() + "'" +
            "}";
    }
}
