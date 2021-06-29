package com.mindeavors.queuen.repository;

import com.mindeavors.queuen.domain.Window;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Window entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WindowRepository extends JpaRepository<Window, Long> {

}
