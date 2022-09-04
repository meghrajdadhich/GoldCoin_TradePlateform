package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.JobSkills;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the JobSkills entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobSkillsRepository extends JpaRepository<JobSkills, Long> {

}
