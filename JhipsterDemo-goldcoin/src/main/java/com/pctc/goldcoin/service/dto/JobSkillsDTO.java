package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the JobSkills entity.
 */
public class JobSkillsDTO implements Serializable {

    private Long id;

    private String jobskillName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobskillName() {
        return jobskillName;
    }

    public void setJobskillName(String jobskillName) {
        this.jobskillName = jobskillName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JobSkillsDTO jobSkillsDTO = (JobSkillsDTO) o;
        if(jobSkillsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), jobSkillsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "JobSkillsDTO{" +
            "id=" + getId() +
            ", jobskillName='" + getJobskillName() + "'" +
            "}";
    }
}
