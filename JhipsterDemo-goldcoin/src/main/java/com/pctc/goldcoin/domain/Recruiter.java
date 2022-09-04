package com.pctc.goldcoin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Recruiter.
 */
@Entity
@Table(name = "recruiter")
@Document(indexName = "recruiter")
public class Recruiter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recruiter_start_date")
    private Instant recruiterStartDate;

    @Column(name = "recruiter_end_date")
    private Instant recruiterEndDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User users;

    @OneToMany(mappedBy = "recruiter")
    @JsonIgnore
    private Set<Job> jobTasks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRecruiterStartDate() {
        return recruiterStartDate;
    }

    public Recruiter recruiterStartDate(Instant recruiterStartDate) {
        this.recruiterStartDate = recruiterStartDate;
        return this;
    }

    public void setRecruiterStartDate(Instant recruiterStartDate) {
        this.recruiterStartDate = recruiterStartDate;
    }

    public Instant getRecruiterEndDate() {
        return recruiterEndDate;
    }

    public Recruiter recruiterEndDate(Instant recruiterEndDate) {
        this.recruiterEndDate = recruiterEndDate;
        return this;
    }

    public void setRecruiterEndDate(Instant recruiterEndDate) {
        this.recruiterEndDate = recruiterEndDate;
    }

    public User getUsers() {
        return users;
    }

    public Recruiter users(User user) {
        this.users = user;
        return this;
    }

    public void setUsers(User user) {
        this.users = user;
    }

    public Set<Job> getJobTasks() {
        return jobTasks;
    }

    public Recruiter jobTasks(Set<Job> jobs) {
        this.jobTasks = jobs;
        return this;
    }

    public Recruiter addJobTask(Job job) {
        this.jobTasks.add(job);
        job.setRecruiter(this);
        return this;
    }

    public Recruiter removeJobTask(Job job) {
        this.jobTasks.remove(job);
        job.setRecruiter(null);
        return this;
    }

    public void setJobTasks(Set<Job> jobs) {
        this.jobTasks = jobs;
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
        Recruiter recruiter = (Recruiter) o;
        if (recruiter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recruiter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recruiter{" +
            "id=" + getId() +
            ", recruiterStartDate='" + getRecruiterStartDate() + "'" +
            ", recruiterEndDate='" + getRecruiterEndDate() + "'" +
            "}";
    }
}
