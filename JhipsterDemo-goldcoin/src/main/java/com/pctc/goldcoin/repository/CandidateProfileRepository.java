package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.CandidateProfile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CandidateProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Long> {

}
