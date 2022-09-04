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
 * A Resume.
 */
@Entity
@Table(name = "resume")
@Document(indexName = "resume")
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "resume_update_date")
    private Instant resumeUpdateDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User resumeUser;

    @OneToMany(mappedBy = "resume")
    @JsonIgnore
    private Set<EducationalProfile> resumeEducations = new HashSet<>();

    @OneToMany(mappedBy = "resume")
    @JsonIgnore
    private Set<ProfessionalProfile> resumeProfessinals = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getResumeUpdateDate() {
        return resumeUpdateDate;
    }

    public Resume resumeUpdateDate(Instant resumeUpdateDate) {
        this.resumeUpdateDate = resumeUpdateDate;
        return this;
    }

    public void setResumeUpdateDate(Instant resumeUpdateDate) {
        this.resumeUpdateDate = resumeUpdateDate;
    }

    public User getResumeUser() {
        return resumeUser;
    }

    public Resume resumeUser(User user) {
        this.resumeUser = user;
        return this;
    }

    public void setResumeUser(User user) {
        this.resumeUser = user;
    }

    public Set<EducationalProfile> getResumeEducations() {
        return resumeEducations;
    }

    public Resume resumeEducations(Set<EducationalProfile> educationalProfiles) {
        this.resumeEducations = educationalProfiles;
        return this;
    }

    public Resume addResumeEducation(EducationalProfile educationalProfile) {
        this.resumeEducations.add(educationalProfile);
        educationalProfile.setResume(this);
        return this;
    }

    public Resume removeResumeEducation(EducationalProfile educationalProfile) {
        this.resumeEducations.remove(educationalProfile);
        educationalProfile.setResume(null);
        return this;
    }

    public void setResumeEducations(Set<EducationalProfile> educationalProfiles) {
        this.resumeEducations = educationalProfiles;
    }

    public Set<ProfessionalProfile> getResumeProfessinals() {
        return resumeProfessinals;
    }

    public Resume resumeProfessinals(Set<ProfessionalProfile> professionalProfiles) {
        this.resumeProfessinals = professionalProfiles;
        return this;
    }

    public Resume addResumeProfessinal(ProfessionalProfile professionalProfile) {
        this.resumeProfessinals.add(professionalProfile);
        professionalProfile.setResume(this);
        return this;
    }

    public Resume removeResumeProfessinal(ProfessionalProfile professionalProfile) {
        this.resumeProfessinals.remove(professionalProfile);
        professionalProfile.setResume(null);
        return this;
    }

    public void setResumeProfessinals(Set<ProfessionalProfile> professionalProfiles) {
        this.resumeProfessinals = professionalProfiles;
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
        Resume resume = (Resume) o;
        if (resume.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resume.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Resume{" +
            "id=" + getId() +
            ", resumeUpdateDate='" + getResumeUpdateDate() + "'" +
            "}";
    }
}
