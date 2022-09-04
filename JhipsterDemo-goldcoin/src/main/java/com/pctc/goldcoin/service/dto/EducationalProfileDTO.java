package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the EducationalProfile entity.
 */
public class EducationalProfileDTO implements Serializable {

    private Long id;

    private String eduTitle;

    private String eduStartDate;

    private String eduEndDate;

    private String eduUniversity;

    private String eduCollageName;

    private String eduLocation;

    private Long resumeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEduTitle() {
        return eduTitle;
    }

    public void setEduTitle(String eduTitle) {
        this.eduTitle = eduTitle;
    }

    public String getEduStartDate() {
        return eduStartDate;
    }

    public void setEduStartDate(String eduStartDate) {
        this.eduStartDate = eduStartDate;
    }

    public String getEduEndDate() {
        return eduEndDate;
    }

    public void setEduEndDate(String eduEndDate) {
        this.eduEndDate = eduEndDate;
    }

    public String getEduUniversity() {
        return eduUniversity;
    }

    public void setEduUniversity(String eduUniversity) {
        this.eduUniversity = eduUniversity;
    }

    public String getEduCollageName() {
        return eduCollageName;
    }

    public void setEduCollageName(String eduCollageName) {
        this.eduCollageName = eduCollageName;
    }

    public String getEduLocation() {
        return eduLocation;
    }

    public void setEduLocation(String eduLocation) {
        this.eduLocation = eduLocation;
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

        EducationalProfileDTO educationalProfileDTO = (EducationalProfileDTO) o;
        if(educationalProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), educationalProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EducationalProfileDTO{" +
            "id=" + getId() +
            ", eduTitle='" + getEduTitle() + "'" +
            ", eduStartDate='" + getEduStartDate() + "'" +
            ", eduEndDate='" + getEduEndDate() + "'" +
            ", eduUniversity='" + getEduUniversity() + "'" +
            ", eduCollageName='" + getEduCollageName() + "'" +
            ", eduLocation='" + getEduLocation() + "'" +
            "}";
    }
}
