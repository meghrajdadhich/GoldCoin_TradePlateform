package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Notification.
 */
@Entity
@Table(name = "notification")
@Document(indexName = "notification")
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "noti_id")
    private String notiId;

    @Column(name = "noti_title")
    private String notiTitle;

    @Column(name = "noti_subject")
    private String notiSubject;

    @Column(name = "noti_value")
    private String notiValue;

    @Column(name = "noti_type")
    private String notiType;

    @Column(name = "noti_start_date")
    private Instant notiStartDate;

    @Column(name = "noti_end_date")
    private Instant notiEndDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User notiBy;

    @OneToOne
    @JoinColumn(unique = true)
    private User notiTo;

    @OneToOne
    @JoinColumn(unique = true)
    private CompanyProfile notiComp;

    @OneToOne
    @JoinColumn(unique = true)
    private Location notiLoc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotiId() {
        return notiId;
    }

    public Notification notiId(String notiId) {
        this.notiId = notiId;
        return this;
    }

    public void setNotiId(String notiId) {
        this.notiId = notiId;
    }

    public String getNotiTitle() {
        return notiTitle;
    }

    public Notification notiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
        return this;
    }

    public void setNotiTitle(String notiTitle) {
        this.notiTitle = notiTitle;
    }

    public String getNotiSubject() {
        return notiSubject;
    }

    public Notification notiSubject(String notiSubject) {
        this.notiSubject = notiSubject;
        return this;
    }

    public void setNotiSubject(String notiSubject) {
        this.notiSubject = notiSubject;
    }

    public String getNotiValue() {
        return notiValue;
    }

    public Notification notiValue(String notiValue) {
        this.notiValue = notiValue;
        return this;
    }

    public void setNotiValue(String notiValue) {
        this.notiValue = notiValue;
    }

    public String getNotiType() {
        return notiType;
    }

    public Notification notiType(String notiType) {
        this.notiType = notiType;
        return this;
    }

    public void setNotiType(String notiType) {
        this.notiType = notiType;
    }

    public Instant getNotiStartDate() {
        return notiStartDate;
    }

    public Notification notiStartDate(Instant notiStartDate) {
        this.notiStartDate = notiStartDate;
        return this;
    }

    public void setNotiStartDate(Instant notiStartDate) {
        this.notiStartDate = notiStartDate;
    }

    public Instant getNotiEndDate() {
        return notiEndDate;
    }

    public Notification notiEndDate(Instant notiEndDate) {
        this.notiEndDate = notiEndDate;
        return this;
    }

    public void setNotiEndDate(Instant notiEndDate) {
        this.notiEndDate = notiEndDate;
    }

    public User getNotiBy() {
        return notiBy;
    }

    public Notification notiBy(User user) {
        this.notiBy = user;
        return this;
    }

    public void setNotiBy(User user) {
        this.notiBy = user;
    }

    public User getNotiTo() {
        return notiTo;
    }

    public Notification notiTo(User user) {
        this.notiTo = user;
        return this;
    }

    public void setNotiTo(User user) {
        this.notiTo = user;
    }

    public CompanyProfile getNotiComp() {
        return notiComp;
    }

    public Notification notiComp(CompanyProfile companyProfile) {
        this.notiComp = companyProfile;
        return this;
    }

    public void setNotiComp(CompanyProfile companyProfile) {
        this.notiComp = companyProfile;
    }

    public Location getNotiLoc() {
        return notiLoc;
    }

    public Notification notiLoc(Location location) {
        this.notiLoc = location;
        return this;
    }

    public void setNotiLoc(Location location) {
        this.notiLoc = location;
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
        Notification notification = (Notification) o;
        if (notification.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), notification.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Notification{" +
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
