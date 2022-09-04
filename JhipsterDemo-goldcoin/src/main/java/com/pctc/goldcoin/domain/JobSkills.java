package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A JobSkills.
 */
@Entity
@Table(name = "job_skills")
@Document(indexName = "jobskills")
public class JobSkills implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jobskill_name")
    private String jobskillName;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobskillName() {
        return jobskillName;
    }

    public JobSkills jobskillName(String jobskillName) {
        this.jobskillName = jobskillName;
        return this;
    }

    public void setJobskillName(String jobskillName) {
        this.jobskillName = jobskillName;
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
        JobSkills jobSkills = (JobSkills) o;
        if (jobSkills.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobSkills.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobSkills{" +
            "id=" + getId() +
            ", jobskillName='" + getJobskillName() + "'" +
            "}";
    }
}
