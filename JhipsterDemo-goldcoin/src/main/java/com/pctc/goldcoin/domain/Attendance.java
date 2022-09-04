package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Attendance.
 */
@Entity
@Table(name = "attendance")
@Document(indexName = "attendance")
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attandance_date")
    private String attandanceDate;

    @Column(name = "attandance_start_time")
    private String attandanceStartTime;

    @Column(name = "attandance_end_time")
    private String attandanceEndTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttandanceDate() {
        return attandanceDate;
    }

    public Attendance attandanceDate(String attandanceDate) {
        this.attandanceDate = attandanceDate;
        return this;
    }

    public void setAttandanceDate(String attandanceDate) {
        this.attandanceDate = attandanceDate;
    }

    public String getAttandanceStartTime() {
        return attandanceStartTime;
    }

    public Attendance attandanceStartTime(String attandanceStartTime) {
        this.attandanceStartTime = attandanceStartTime;
        return this;
    }

    public void setAttandanceStartTime(String attandanceStartTime) {
        this.attandanceStartTime = attandanceStartTime;
    }

    public String getAttandanceEndTime() {
        return attandanceEndTime;
    }

    public Attendance attandanceEndTime(String attandanceEndTime) {
        this.attandanceEndTime = attandanceEndTime;
        return this;
    }

    public void setAttandanceEndTime(String attandanceEndTime) {
        this.attandanceEndTime = attandanceEndTime;
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
        Attendance attendance = (Attendance) o;
        if (attendance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Attendance{" +
            "id=" + getId() +
            ", attandanceDate='" + getAttandanceDate() + "'" +
            ", attandanceStartTime='" + getAttandanceStartTime() + "'" +
            ", attandanceEndTime='" + getAttandanceEndTime() + "'" +
            "}";
    }
}
