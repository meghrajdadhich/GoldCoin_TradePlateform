package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A CompanyProfile.
 */
@Entity
@Table(name = "company_profile")
@Document(indexName = "companyprofile")
public class CompanyProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_contact_person_name")
    private String clientContactPersonName;

    @Column(name = "client_update_date")
    private String clientUpdateDate;

    @Column(name = "client_location")
    private String clientLocation;

    @Column(name = "client_status")
    private String clientStatus;

    @OneToOne
    @JoinColumn(unique = true)
    private User companyUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public CompanyProfile clientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientContactPersonName() {
        return clientContactPersonName;
    }

    public CompanyProfile clientContactPersonName(String clientContactPersonName) {
        this.clientContactPersonName = clientContactPersonName;
        return this;
    }

    public void setClientContactPersonName(String clientContactPersonName) {
        this.clientContactPersonName = clientContactPersonName;
    }

    public String getClientUpdateDate() {
        return clientUpdateDate;
    }

    public CompanyProfile clientUpdateDate(String clientUpdateDate) {
        this.clientUpdateDate = clientUpdateDate;
        return this;
    }

    public void setClientUpdateDate(String clientUpdateDate) {
        this.clientUpdateDate = clientUpdateDate;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public CompanyProfile clientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
        return this;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getClientStatus() {
        return clientStatus;
    }

    public CompanyProfile clientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
        return this;
    }

    public void setClientStatus(String clientStatus) {
        this.clientStatus = clientStatus;
    }

    public User getCompanyUser() {
        return companyUser;
    }

    public CompanyProfile companyUser(User user) {
        this.companyUser = user;
        return this;
    }

    public void setCompanyUser(User user) {
        this.companyUser = user;
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
        CompanyProfile companyProfile = (CompanyProfile) o;
        if (companyProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), companyProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
            "id=" + getId() +
            ", clientName='" + getClientName() + "'" +
            ", clientContactPersonName='" + getClientContactPersonName() + "'" +
            ", clientUpdateDate='" + getClientUpdateDate() + "'" +
            ", clientLocation='" + getClientLocation() + "'" +
            ", clientStatus='" + getClientStatus() + "'" +
            "}";
    }
}
