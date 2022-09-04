package com.pctc.goldcoin.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ConsultancyProfile entity.
 */
public class ConsultancyProfileDTO implements Serializable {

    private Long id;

    private String consultancyTitle;

    private Long consUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultancyTitle() {
        return consultancyTitle;
    }

    public void setConsultancyTitle(String consultancyTitle) {
        this.consultancyTitle = consultancyTitle;
    }

    public Long getConsUserId() {
        return consUserId;
    }

    public void setConsUserId(Long userId) {
        this.consUserId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultancyProfileDTO consultancyProfileDTO = (ConsultancyProfileDTO) o;
        if(consultancyProfileDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultancyProfileDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultancyProfileDTO{" +
            "id=" + getId() +
            ", consultancyTitle='" + getConsultancyTitle() + "'" +
            "}";
    }
}
