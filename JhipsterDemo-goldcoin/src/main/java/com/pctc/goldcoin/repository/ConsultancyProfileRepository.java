package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.ConsultancyProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConsultancyProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultancyProfileRepository extends JpaRepository<ConsultancyProfile, Long> {

}
