package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.ApplyJob;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the ApplyJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJob, Long> {
    @Query("select distinct apply_job from ApplyJob apply_job left join fetch apply_job.users")
    List<ApplyJob> findAllWithEagerRelationships();

    @Query("select apply_job from ApplyJob apply_job left join fetch apply_job.users where apply_job.id =:id")
    ApplyJob findOneWithEagerRelationships(@Param("id") Long id);

}
