package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A EducationalProfile.
 */
@Entity
@Table(name = "educational_profile")
@Document(indexName = "educationalprofile")
public class EducationalProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "edu_title")
    private String eduTitle;

    @Column(name = "edu_start_date")
    private String eduStartDate;

    @Column(name = "edu_end_date")
    private String eduEndDate;

    @Column(name = "edu_university")
    private String eduUniversity;

    @Column(name = "edu_collage_name")
    private String eduCollageName;

    @Column(name = "edu_location")
    private String eduLocation;

    @ManyToOne
    private Resume resume;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEduTitle() {
        return eduTitle;
    }

    public EducationalProfile eduTitle(String eduTitle) {
        this.eduTitle = eduTitle;
        return this;
    }

    public void setEduTitle(String eduTitle) {
        this.eduTitle = eduTitle;
    }

    public String getEduStartDate() {
        return eduStartDate;
    }

    public EducationalProfile eduStartDate(String eduStartDate) {
        this.eduStartDate = eduStartDate;
        return this;
    }

    public void setEduStartDate(String eduStartDate) {
        this.eduStartDate = eduStartDate;
    }

    public String getEduEndDate() {
        return eduEndDate;
    }

    public EducationalProfile eduEndDate(String eduEndDate) {
        this.eduEndDate = eduEndDate;
        return this;
    }

    public void setEduEndDate(String eduEndDate) {
        this.eduEndDate = eduEndDate;
    }

    public String getEduUniversity() {
        return eduUniversity;
    }

    public EducationalProfile eduUniversity(String eduUniversity) {
        this.eduUniversity = eduUniversity;
        return this;
    }

    public void setEduUniversity(String eduUniversity) {
        this.eduUniversity = eduUniversity;
    }

    public String getEduCollageName() {
        return eduCollageName;
    }

    public EducationalProfile eduCollageName(String eduCollageName) {
        this.eduCollageName = eduCollageName;
        return this;
    }

    public void setEduCollageName(String eduCollageName) {
        this.eduCollageName = eduCollageName;
    }

    public String getEduLocation() {
        return eduLocation;
    }

    public EducationalProfile eduLocation(String eduLocation) {
        this.eduLocation = eduLocation;
        return this;
    }

    public void setEduLocation(String eduLocation) {
        this.eduLocation = eduLocation;
    }

    public Resume getResume() {
        return resume;
    }

    public EducationalProfile resume(Resume resume) {
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
        EducationalProfile educationalProfile = (EducationalProfile) o;
        if (educationalProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), educationalProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EducationalProfile{" +
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
