package com.pctc.goldcoin.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Recruiter entity.
 */
public class RecruiterDTO implements Serializable {

    private Long id;

    private Instant recruiterStartDate;

    private Instant recruiterEndDate;

    private Long usersId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRecruiterStartDate() {
        return recruiterStartDate;
    }

    public void setRecruiterStartDate(Instant recruiterStartDate) {
        this.recruiterStartDate = recruiterStartDate;
    }

    public Instant getRecruiterEndDate() {
        return recruiterEndDate;
    }

    public void setRecruiterEndDate(Instant recruiterEndDate) {
        this.recruiterEndDate = recruiterEndDate;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long userId) {
        this.usersId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecruiterDTO recruiterDTO = (RecruiterDTO) o;
        if(recruiterDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recruiterDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RecruiterDTO{" +
            "id=" + getId() +
            ", recruiterStartDate='" + getRecruiterStartDate() + "'" +
            ", recruiterEndDate='" + getRecruiterEndDate() + "'" +
            "}";
    }
}
