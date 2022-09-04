package com.pctc.goldcoin.domain;


import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ConsultancyProfile.
 */
@Entity
@Table(name = "consultancy_profile")
@Document(indexName = "consultancyprofile")
public class ConsultancyProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consultancy_title")
    private String consultancyTitle;

    @OneToOne
    @JoinColumn(unique = true)
    private User consUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultancyTitle() {
        return consultancyTitle;
    }

    public ConsultancyProfile consultancyTitle(String consultancyTitle) {
        this.consultancyTitle = consultancyTitle;
        return this;
    }

    public void setConsultancyTitle(String consultancyTitle) {
        this.consultancyTitle = consultancyTitle;
    }

    public User getConsUser() {
        return consUser;
    }

    public ConsultancyProfile consUser(User user) {
        this.consUser = user;
        return this;
    }

    public void setConsUser(User user) {
        this.consUser = user;
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
        ConsultancyProfile consultancyProfile = (ConsultancyProfile) o;
        if (consultancyProfile.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultancyProfile.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultancyProfile{" +
            "id=" + getId() +
            ", consultancyTitle='" + getConsultancyTitle() + "'" +
            "}";
    }
}
