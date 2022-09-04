package com.pctc.goldcoin.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Resume entity.
 */
public class ResumeDTO implements Serializable {

    private Long id;

    private Instant resumeUpdateDate;

    private Long resumeUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getResumeUpdateDate() {
        return resumeUpdateDate;
    }

    public void setResumeUpdateDate(Instant resumeUpdateDate) {
        this.resumeUpdateDate = resumeUpdateDate;
    }

    public Long getResumeUserId() {
        return resumeUserId;
    }

    public void setResumeUserId(Long userId) {
        this.resumeUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ResumeDTO resumeDTO = (ResumeDTO) o;
        if(resumeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), resumeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ResumeDTO{" +
            "id=" + getId() +
            ", resumeUpdateDate='" + getResumeUpdateDate() + "'" +
            "}";
    }
}
