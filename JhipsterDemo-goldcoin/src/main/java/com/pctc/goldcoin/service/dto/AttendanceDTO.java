package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Attendance entity.
 */
public class AttendanceDTO implements Serializable {

    private Long id;

    private String attandanceDate;

    private String attandanceStartTime;

    private String attandanceEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttandanceDate() {
        return attandanceDate;
    }

    public void setAttandanceDate(String attandanceDate) {
        this.attandanceDate = attandanceDate;
    }

    public String getAttandanceStartTime() {
        return attandanceStartTime;
    }

    public void setAttandanceStartTime(String attandanceStartTime) {
        this.attandanceStartTime = attandanceStartTime;
    }

    public String getAttandanceEndTime() {
        return attandanceEndTime;
    }

    public void setAttandanceEndTime(String attandanceEndTime) {
        this.attandanceEndTime = attandanceEndTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendanceDTO attendanceDTO = (AttendanceDTO) o;
        if(attendanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), attendanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AttendanceDTO{" +
            "id=" + getId() +
            ", attandanceDate='" + getAttandanceDate() + "'" +
            ", attandanceStartTime='" + getAttandanceStartTime() + "'" +
            ", attandanceEndTime='" + getAttandanceEndTime() + "'" +
            "}";
    }
}
