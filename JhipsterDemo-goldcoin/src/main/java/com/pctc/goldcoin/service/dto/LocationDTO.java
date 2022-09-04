package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Location entity.
 */
public class LocationDTO implements Serializable {

    private Long id;

    private Long locId;

    private String locType;

    private String locTitle;

    private String locAddress2;

    private String locAddress1;

    private String locArea;

    private String locCity;

    private String locState;

    private String locCountry;

    private String locPinCode;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocId() {
        return locId;
    }

    public void setLocId(Long locId) {
        this.locId = locId;
    }

    public String getLocType() {
        return locType;
    }

    public void setLocType(String locType) {
        this.locType = locType;
    }

    public String getLocTitle() {
        return locTitle;
    }

    public void setLocTitle(String locTitle) {
        this.locTitle = locTitle;
    }

    public String getLocAddress2() {
        return locAddress2;
    }

    public void setLocAddress2(String locAddress2) {
        this.locAddress2 = locAddress2;
    }

    public String getLocAddress1() {
        return locAddress1;
    }

    public void setLocAddress1(String locAddress1) {
        this.locAddress1 = locAddress1;
    }

    public String getLocArea() {
        return locArea;
    }

    public void setLocArea(String locArea) {
        this.locArea = locArea;
    }

    public String getLocCity() {
        return locCity;
    }

    public void setLocCity(String locCity) {
        this.locCity = locCity;
    }

    public String getLocState() {
        return locState;
    }

    public void setLocState(String locState) {
        this.locState = locState;
    }

    public String getLocCountry() {
        return locCountry;
    }

    public void setLocCountry(String locCountry) {
        this.locCountry = locCountry;
    }

    public String getLocPinCode() {
        return locPinCode;
    }

    public void setLocPinCode(String locPinCode) {
        this.locPinCode = locPinCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LocationDTO locationDTO = (LocationDTO) o;
        if(locationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), locationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LocationDTO{" +
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
