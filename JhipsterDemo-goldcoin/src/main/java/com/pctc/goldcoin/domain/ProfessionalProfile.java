package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ProfessionalProfile.
 */
@Entity
@Table(name = "professional_profile")
@Document(indexName = "professionalprofile")
public class ProfessionalProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pro_title")
    private String proTitle;

    @Column(name = "pro_company_name")
    private String proCompanyName;

    @Column(name = "pro_start_date")
    private String proStartDate;

    @Column(name = "pro_end_date")
    private String proEndDate;

    @Column(name = "pro_location")
    private String proLocation;

    @ManyToOne
    private Resume resume;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProTitle() {
        return proTitle;
    }

    public ProfessionalProfile proTitle(String proTitle) {
        this.proTitle = proTitle;
        return this;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public String getProCompanyName() {
        return proCompanyName;
    }

    public ProfessionalProfile proCompanyName(String proCompanyName) {
        this.proCompanyName = proCompanyName;
        return this;
    }

    public void setProCompanyName(String proCompanyName) {
        this.proCompanyName = proCompanyName;
    }

    public String getProStartDate() {
        return proStartDate;
    }

    public ProfessionalProfile proStartDate(String proStartDate) {
        this.proStartDate = proStartDate;
        return this;
    }

    public void setProStartDate(String proStartDate) {
        this.proStartDate = proStartDate;
    }

    public String getProEndDate() {
        return proEndDate;
    }

    public ProfessionalProfile proEndDate(String proEndDate) {
        this.proEndDate = proEndDate;
        return this;
    }

    public void setProEndDate(String proEndDate) {
        this.proEndDate = proEndDate;
    }

    public String getProLocation() {
        return proLocation;
    }

    public ProfessionalProfile proLocation(String proLocation) {
        this.proLocation = proLocation;
        return this;
    }

    public void setProLocation(String proLocation) {
        this.proLocation = proLocation;
    }

    public Resume getResume() {
        return resume;
    }

    public ProfessionalProfile resume(Resume resume) {
        this.resume = resume;
        return this;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
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
        ProfessionalProfile professionalProfile = (ProfessionalProfile) o;
        if (professionalProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionalProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionalProfile{" +
            "id=" + getId() +
            ", proTitle='" + getProTitle() + "'" +
            ", proCompanyName='" + getProCompanyName() + "'" +
            ", proStartDate='" + getProStartDate() + "'" +
            ", proEndDate='" + getProEndDate() + "'" +
            ", proLocation='" + getProLocation() + "'" +
            "}";
    }
}
