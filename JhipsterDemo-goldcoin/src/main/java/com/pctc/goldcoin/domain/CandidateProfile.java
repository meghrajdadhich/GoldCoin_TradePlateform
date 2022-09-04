package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CandidateProfile.
 */
@Entity
@Table(name = "candidate_profile")
@Document(indexName = "candidateprofile")
public class CandidateProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "candidate_id")
    private String candidateId;

    @Column(name = "user_id")
    private String userId;

    @OneToOne
    @JoinColumn(unique = true)
    private User candidateUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public CandidateProfile candidateId(String candidateId) {
        this.candidateId = candidateId;
        return this;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getUserId() {
        return userId;
    }

    public CandidateProfile userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getCandidateUser() {
        return candidateUser;
    }

    public CandidateProfile candidateUser(User user) {
        this.candidateUser = user;
        return this;
    }

    public void setCandidateUser(User user) {
        this.candidateUser = user;
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
        CandidateProfile candidateProfile = (CandidateProfile) o;
        if (candidateProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateProfile{" +
            "id=" + getId() +
            ", candidateId='" + getCandidateId() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
