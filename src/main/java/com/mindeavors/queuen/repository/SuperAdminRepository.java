package com.mindeavors.queuen.repository;

import com.mindeavors.queuen.domain.SuperAdmin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SuperAdmin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

}
