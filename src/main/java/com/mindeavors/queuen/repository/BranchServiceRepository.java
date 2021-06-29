package com.mindeavors.queuen.repository;

import com.mindeavors.queuen.domain.BranchService;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BranchService entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BranchServiceRepository extends JpaRepository<BranchService, Long> {

}
