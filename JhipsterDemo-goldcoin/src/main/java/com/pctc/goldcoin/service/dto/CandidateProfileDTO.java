package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CandidateProfile entity.
 */
public class CandidateProfileDTO implements Serializable {

    private Long id;

    private String candidateId;

    private String userId;

    private Long candidateUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(String candidateId) {
        this.candidateId = candidateId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCandidateUserId() {
        return candidateUserId;
    }

    public void setCandidateUserId(Long userId) {
        this.candidateUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CandidateProfileDTO candidateProfileDTO = (CandidateProfileDTO) o;
        if(candidateProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), candidateProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CandidateProfileDTO{" +
            "id=" + getId() +
            ", candidateId='" + getCandidateId() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
