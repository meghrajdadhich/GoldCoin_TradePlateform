package com.pctc.goldcoin.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Notification entity.
 */
public class NotificationDTO implements Serializable {

    private Long id;

    private String notiId;

    private String notiTitle;

    private String notiSubject;

    private String notiValue;

    private String notiType;

    private Instant notiStartDate;

    private Instant notiEndDate;

    private Long notiById;

    private Long notiToId;

    private Long notiCompId;

    private Long notiLocId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotiId() {
        return notiId;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }

    public String getNotiTitle() {
        return notiTitle;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiSubject() {
        return notiSubject;
    }

    public void setNotiSubject(String notiSubject) {
        this.notiSubject = notiSubject;
    }

    public String getNotiValue() {
        return notiValue;
    }

    public void setNotiValue(String notiValue) {
        this.notiValue = notiValue;
    }

    public String getNotiType() {
        return notiType;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }

    public Instant getNotiStartDate() {
        return notiStartDate;
    }

    public void setNotiStartDate(Instant notiStartDate) {
        this.notiStartDate = notiStartDate;
    }

    public Instant getNotiEndDate() {
        return notiEndDate;
    }

    public void setNotiEndDate(Instant notiEndDate) {
        this.notiEndDate = notiEndDate;
    }

    public Long getNotiById() {
        return notiById;
    }

    public void setNotiById(Long userId) {
        this.notiById = userId;
    }

    public Long getNotiToId() {
        return notiToId;
    }

    public void setNotiToId(Long userId) {
        this.notiToId = userId;
    }

    public Long getNotiCompId() {
        return notiCompId;
    }

    public void setNotiCompId(Long companyProfileId) {
        this.notiCompId = companyProfileId;
    }

    public Long getNotiLocId() {
        return notiLocId;
    }

    public void setNotiLocId(Long locationId) {
        this.notiLocId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if(notificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
            "id=" + getId() +
            ", notiId='" + getNotiId() + "'" +
            ", notiTitle='" + getNotiTitle() + "'" +
            ", notiSubject='" + getNotiSubject() + "'" +
            ", notiValue='" + getNotiValue() + "'" +
            ", notiType='" + getNotiType() + "'" +
            ", notiStartDate='" + getNotiStartDate() + "'" +
            ", notiEndDate='" + getNotiEndDate() + "'" +
            "}";
    }
}
