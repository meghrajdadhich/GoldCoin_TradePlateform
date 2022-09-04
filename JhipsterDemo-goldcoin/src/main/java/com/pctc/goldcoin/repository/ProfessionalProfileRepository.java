package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.ProfessionalProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProfessionalProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long> {

}
