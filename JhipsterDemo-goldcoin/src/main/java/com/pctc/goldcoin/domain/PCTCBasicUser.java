package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A PCTCBasicUser.
 */
@Entity
@Table(name = "pctc_basic_user")
@Document(indexName = "pctcbasicuser")
public class PCTCBasicUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "address_line_2")
    private String addressLine2;

    @Column(name = "land_mark")
    private String landMark;

    @Column(name = "sub_area")
    private String subArea;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "country")
    private String country;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "party_type")
    private String partyType;

    @Column(name = "ref_user_id")
    private String refUserId;

    @Column(name = "nationality")
    private String nationality;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public PCTCBasicUser dateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public PCTCBasicUser contactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getGender() {
        return gender;
    }

    public PCTCBasicUser gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public PCTCBasicUser addressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public PCTCBasicUser addressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getLandMark() {
        return landMark;
    }

    public PCTCBasicUser landMark(String landMark) {
        this.landMark = landMark;
        return this;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getSubArea() {
        return subArea;
    }

    public PCTCBasicUser subArea(String subArea) {
        this.subArea = subArea;
        return this;
    }

    public void setSubArea(String subArea) {
        this.subArea = subArea;
    }

    public String getCity() {
        return city;
    }

    public PCTCBasicUser city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public PCTCBasicUser state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public PCTCBasicUser country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public PCTCBasicUser pinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPartyType() {
        return partyType;
    }

    public PCTCBasicUser partyType(String partyType) {
        this.partyType = partyType;
        return this;
    }

    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    public String getRefUserId() {
        return refUserId;
    }

    public PCTCBasicUser refUserId(String refUserId) {
        this.refUserId = refUserId;
        return this;
    }

    public void setRefUserId(String refUserId) {
        this.refUserId = refUserId;
    }

    public String getNationality() {
        return nationality;
    }

    public PCTCBasicUser nationality(String nationality) {
        this.nationality = nationality;
        return this;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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
        PCTCBasicUser pCTCBasicUser = (PCTCBasicUser) o;
        if (pCTCBasicUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pCTCBasicUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PCTCBasicUser{" +
            "id=" + getId() +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", contactNumber='" + getContactNumber() + "'" +
            ", gender='" + getGender() + "'" +
            ", addressLine1='" + getAddressLine1() + "'" +
            ", addressLine2='" + getAddressLine2() + "'" +
            ", landMark='" + getLandMark() + "'" +
            ", subArea='" + getSubArea() + "'" +
            ", city='" + getCity() + "'" +
            ", state='" + getState() + "'" +
            ", country='" + getCountry() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", partyType='" + getPartyType() + "'" +
            ", refUserId='" + getRefUserId() + "'" +
            ", nationality='" + getNationality() + "'" +
            "}";
    }
}
