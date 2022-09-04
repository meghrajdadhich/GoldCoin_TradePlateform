package com.pctc.goldcoin.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ApplyJob entity.
 */
public class ApplyJobDTO implements Serializable {

    private Long id;

    private Instant applyJobStartDate;

    private Instant applyJobEndDate;

    private Set<UserDTO> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getApplyJobStartDate() {
        return applyJobStartDate;
    }

    public void setApplyJobStartDate(Instant applyJobStartDate) {
        this.applyJobStartDate = applyJobStartDate;
    }

    public Instant getApplyJobEndDate() {
        return applyJobEndDate;
    }

    public void setApplyJobEndDate(Instant applyJobEndDate) {
        this.applyJobEndDate = applyJobEndDate;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplyJobDTO applyJobDTO = (ApplyJobDTO) o;
        if(applyJobDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applyJobDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplyJobDTO{" +
            "id=" + getId() +
            ", applyJobStartDate='" + getApplyJobStartDate() + "'" +
            ", applyJobEndDate='" + getApplyJobEndDate() + "'" +
            "}";
    }
}
