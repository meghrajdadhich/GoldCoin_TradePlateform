package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.InterviewSchedule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InterviewSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InterviewScheduleRepository extends JpaRepository<InterviewSchedule, Long> {

}
