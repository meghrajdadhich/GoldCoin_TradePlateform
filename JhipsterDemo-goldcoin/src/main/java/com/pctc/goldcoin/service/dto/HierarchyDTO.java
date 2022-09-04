package com.pctc.goldcoin.service.dto;


import java.time.Instant;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Hierarchy entity.
 */
public class HierarchyDTO implements Serializable {

    private Long id;

    private Instant reportStartDate;

    private Instant reportEndDate;

    private Long parentUserId;

    private Long childUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getReportStartDate() {
        return reportStartDate;
    }

    public void setReportStartDate(Instant reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Instant getReportEndDate() {
        return reportEndDate;
    }

    public void setReportEndDate(Instant reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public Long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(Long userId) {
        this.parentUserId = userId;
    }

    public Long getChildUserId() {
        return childUserId;
    }

    public void setChildUserId(Long userId) {
        this.childUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HierarchyDTO hierarchyDTO = (HierarchyDTO) o;
        if(hierarchyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hierarchyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HierarchyDTO{" +
            "id=" + getId() +
            ", reportStartDate='" + getReportStartDate() + "'" +
            ", reportEndDate='" + getReportEndDate() + "'" +
            "}";
    }
}
