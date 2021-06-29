package com.mindeavors.queuen.repository;

import com.mindeavors.queuen.domain.Admin;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Admin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
