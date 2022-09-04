package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ProfessionalProfile entity.
 */
public class ProfessionalProfileDTO implements Serializable {

    private Long id;

    private String proTitle;

    private String proCompanyName;

    private String proStartDate;

    private String proEndDate;

    private String proLocation;

    private Long resumeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProTitle() {
        return proTitle;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public String getProCompanyName() {
        return proCompanyName;
    }

    public void setProCompanyName(String proCompanyName) {
        this.proCompanyName = proCompanyName;
    }

    public String getProStartDate() {
        return proStartDate;
    }

    public void setProStartDate(String proStartDate) {
        this.proStartDate = proStartDate;
    }

    public String getProEndDate() {
        return proEndDate;
    }

    public void setProEndDate(String proEndDate) {
        this.proEndDate = proEndDate;
    }

    public String getProLocation() {
        return proLocation;
    }

    public void setProLocation(String proLocation) {
        this.proLocation = proLocation;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfessionalProfileDTO professionalProfileDTO = (ProfessionalProfileDTO) o;
        if(professionalProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professionalProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfessionalProfileDTO{" +
            "id=" + getId() +
            ", proTitle='" + getProTitle() + "'" +
            ", proCompanyName='" + getProCompanyName() + "'" +
            ", proStartDate='" + getProStartDate() + "'" +
            ", proEndDate='" + getProEndDate() + "'" +
            ", proLocation='" + getProLocation() + "'" +
            "}";
    }
}
