package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Hierarchy.
 */
@Entity
@Table(name = "hierarchy")
@Document(indexName = "hierarchy")
public class Hierarchy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_start_date")
    private Instant reportStartDate;

    @Column(name = "report_end_date")
    private Instant reportEndDate;

    @OneToOne
    @JoinColumn(unique = true)
    private User parentUser;

    @OneToOne
    @JoinColumn(unique = true)
    private User childUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getReportStartDate() {
        return reportStartDate;
    }

    public Hierarchy reportStartDate(Instant reportStartDate) {
        this.reportStartDate = reportStartDate;
        return this;
    }

    public void setReportStartDate(Instant reportStartDate) {
        this.reportStartDate = reportStartDate;
    }

    public Instant getReportEndDate() {
        return reportEndDate;
    }

    public Hierarchy reportEndDate(Instant reportEndDate) {
        this.reportEndDate = reportEndDate;
        return this;
    }

    public void setReportEndDate(Instant reportEndDate) {
        this.reportEndDate = reportEndDate;
    }

    public User getParentUser() {
        return parentUser;
    }

    public Hierarchy parentUser(User user) {
        this.parentUser = user;
        return this;
    }

    public void setParentUser(User user) {
        this.parentUser = user;
    }

    public User getChildUser() {
        return childUser;
    }

    public Hierarchy childUser(User user) {
        this.childUser = user;
        return this;
    }

    public void setChildUser(User user) {
        this.childUser = user;
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
        Hierarchy hierarchy = (Hierarchy) o;
        if (hierarchy.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hierarchy.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Hierarchy{" +
            "id=" + getId() +
            ", reportStartDate='" + getReportStartDate() + "'" +
            ", reportEndDate='" + getReportEndDate() + "'" +
            "}";
    }
}
