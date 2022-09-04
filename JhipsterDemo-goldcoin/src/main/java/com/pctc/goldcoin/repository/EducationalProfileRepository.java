package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.EducationalProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EducationalProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EducationalProfileRepository extends JpaRepository<EducationalProfile, Long> {

}
