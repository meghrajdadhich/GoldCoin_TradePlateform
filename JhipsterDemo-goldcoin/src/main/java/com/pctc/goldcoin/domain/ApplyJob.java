package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ApplyJob.
 */
@Entity
@Table(name = "apply_job")
@Document(indexName = "applyjob")
public class ApplyJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apply_job_start_date")
    private Instant applyJobStartDate;

    @Column(name = "apply_job_end_date")
    private Instant applyJobEndDate;

    @ManyToMany
    @JoinTable(name = "apply_job_users",
               joinColumns = @JoinColumn(name="apply_jobs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="users_id", referencedColumnName="id"))
    private Set<User> users = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getApplyJobStartDate() {
        return applyJobStartDate;
    }

    public ApplyJob applyJobStartDate(Instant applyJobStartDate) {
        this.applyJobStartDate = applyJobStartDate;
        return this;
    }

    public void setApplyJobStartDate(Instant applyJobStartDate) {
        this.applyJobStartDate = applyJobStartDate;
    }

    public Instant getApplyJobEndDate() {
        return applyJobEndDate;
    }

    public ApplyJob applyJobEndDate(Instant applyJobEndDate) {
        this.applyJobEndDate = applyJobEndDate;
        return this;
    }

    public void setApplyJobEndDate(Instant applyJobEndDate) {
        this.applyJobEndDate = applyJobEndDate;
    }

    public Set<User> getUsers() {
        return users;
    }

    public ApplyJob users(Set<User> users) {
        this.users = users;
        return this;
    }

    public ApplyJob addUsers(User user) {
        this.users.add(user);
        return this;
    }

    public ApplyJob removeUsers(User user) {
        this.users.remove(user);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
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
        ApplyJob applyJob = (ApplyJob) o;
        if (applyJob.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applyJob.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplyJob{" +
            "id=" + getId() +
            ", applyJobStartDate='" + getApplyJobStartDate() + "'" +
            ", applyJobEndDate='" + getApplyJobEndDate() + "'" +
            "}";
    }
}
