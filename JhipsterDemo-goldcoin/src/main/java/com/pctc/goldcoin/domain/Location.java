package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
@Document(indexName = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loc_id")
    private Long locId;

    @Column(name = "loc_type")
    private String locType;

    @Column(name = "loc_title")
    private String locTitle;

    @Column(name = "loc_address_2")
    private String locAddress2;

    @Column(name = "loc_address_1")
    private String locAddress1;

    @Column(name = "loc_area")
    private String locArea;

    @Column(name = "loc_city")
    private String locCity;

    @Column(name = "loc_state")
    private String locState;

    @Column(name = "loc_country")
    private String locCountry;

    @Column(name = "loc_pin_code")
    private String locPinCode;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocId() {
        return locId;
    }

    public Location locId(Long locId) {
        this.locId = locId;
        return this;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getLocType() {
        return locType;
    }

    public Location locType(String locType) {
        this.locType = locType;
        return this;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLocTitle() {
        return locTitle;
    }

    public Location locTitle(String locTitle) {
        this.locTitle = locTitle;
        return this;
    }

    public void setLocTitle(String locTitle) {
        this.locTitle = locTitle;
    }

    public String getLocAddress2() {
        return locAddress2;
    }

    public Location locAddress2(String locAddress2) {
        this.locAddress2 = locAddress2;
        return this;
    }

    public void setLocAddress2(String locAddress2) {
        this.locAddress2 = locAddress2;
    }

    public String getLocAddress1() {
        return locAddress1;
    }

    public Location locAddress1(String locAddress1) {
        this.locAddress1 = locAddress1;
        return this;
    }

    public void setLocAddress1(String locAddress1) {
        this.locAddress1 = locAddress1;
    }

    public String getLocArea() {
        return locArea;
    }

    public Location locArea(String locArea) {
        this.locArea = locArea;
        return this;
    }

    public void setLocArea(String locArea) {
        this.locArea = locArea;
    }

    public String getLocCity() {
        return locCity;
    }

    public Location locCity(String locCity) {
        this.locCity = locCity;
        return this;
    }

    public void setLocCity(String locCity) {
        this.locCity = locCity;
    }

    public String getLocState() {
        return locState;
    }

    public Location locState(String locState) {
        this.locState = locState;
        return this;
    }

    public void setLocState(String locState) {
        this.locState = locState;
    }

    public String getLocCountry() {
        return locCountry;
    }

    public Location locCountry(String locCountry) {
        this.locCountry = locCountry;
        return this;
    }

    public void setLocCountry(String locCountry) {
        this.locCountry = locCountry;
    }

    public String getLocPinCode() {
        return locPinCode;
    }

    public Location locPinCode(String locPinCode) {
        this.locPinCode = locPinCode;
        return this;
    }

    public void setLocPinCode(String locPinCode) {
        this.locPinCode = locPinCode;
    }

    public User getUser() {
        return user;
    }

    public Location user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", locId=" + getLocId() +
            ", locType='" + getLocType() + "'" +
            ", locTitle='" + getLocTitle() + "'" +
            ", locAddress2='" + getLocAddress2() + "'" +
            ", locAddress1='" + getLocAddress1() + "'" +
            ", locArea='" + getLocArea() + "'" +
            ", locCity='" + getLocCity() + "'" +
            ", locState='" + getLocState() + "'" +
            ", locCountry='" + getLocCountry() + "'" +
            ", locPinCode='" + getLocPinCode() + "'" +
            "}";
    }
}
