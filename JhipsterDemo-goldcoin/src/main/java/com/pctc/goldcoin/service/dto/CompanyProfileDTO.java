package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CompanyProfile entity.
 */
public class CompanyProfileDTO implements Serializable {

    private Long id;

    private String clientName;

    private String clientContactPersonName;

    private String clientUpdateDate;

    private String clientLocation;

    private String clientStatus;

    private Long companyUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientContactPersonName() {
        return clientContactPersonName;
    }

    public void setClientContactPersonName(String clientContactPersonName) {
        this.clientContactPersonName = clientContactPersonName;
    }

    public String getClientUpdateDate() {
        return clientUpdateDate;
    }

    public void setClientUpdateDate(String clientUpdateDate) {
        this.clientUpdateDate = clientUpdateDate;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public Long getCompanyUserId() {
        return companyUserId;
    }

    public void setCompanyUserId(Long userId) {
        this.companyUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyProfileDTO companyProfileDTO = (CompanyProfileDTO) o;
        if(companyProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyProfileDTO{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", clientContactPersonName='" + getClientContactPersonName() + "'" +
            ", clientUpdateDate='" + getClientUpdateDate() + "'" +
            ", clientLocation='" + getClientLocation() + "'" +
            ", clientStatus='" + getClientStatus() + "'" +
            "}";
    }
}
