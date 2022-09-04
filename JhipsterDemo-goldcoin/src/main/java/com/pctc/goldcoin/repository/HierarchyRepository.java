package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.Hierarchy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Hierarchy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HierarchyRepository extends JpaRepository<Hierarchy, Long> {

}
