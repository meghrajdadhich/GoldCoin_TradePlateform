package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.Job;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Job entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    @Query("select distinct job from Job job left join fetch job.skills")
    List<Job> findAllWithEagerRelationships();

    @Query("select job from Job job left join fetch job.skills where job.id =:id")
    Job findOneWithEagerRelationships(@Param("id") Long id);

}
