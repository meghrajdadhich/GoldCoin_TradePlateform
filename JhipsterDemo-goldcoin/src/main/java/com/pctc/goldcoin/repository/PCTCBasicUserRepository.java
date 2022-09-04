package com.pctc.goldcoin.repository;

import com.pctc.goldcoin.domain.PCTCBasicUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PCTCBasicUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PCTCBasicUserRepository extends JpaRepository<PCTCBasicUser, Long> {

}
