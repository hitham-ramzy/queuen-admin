package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.SuperAdmin;
import com.mindeavors.queuen.repository.SuperAdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SuperAdmin.
 */
@Service
@Transactional
public class SuperAdminService {

    private final Logger log = LoggerFactory.getLogger(SuperAdminService.class);

    private final SuperAdminRepository superAdminRepository;

    public SuperAdminService(SuperAdminRepository superAdminRepository) {
        this.superAdminRepository = superAdminRepository;
    }

    /**
     * Save a superAdmin.
     *
     * @param superAdmin the entity to save
     * @return the persisted entity
     */
    public SuperAdmin save(SuperAdmin superAdmin) {
        log.debug("Request to save SuperAdmin : {}", superAdmin);
        return superAdminRepository.save(superAdmin);
    }

    /**
     *  Get all the superAdmins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<SuperAdmin> findAll(Pageable pageable) {
        log.debug("Request to get all SuperAdmins");
        return superAdminRepository.findAll(pageable);
    }

    /**
     *  Get one superAdmin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SuperAdmin findOne(Long id) {
        log.debug("Request to get SuperAdmin : {}", id);
        return superAdminRepository.findOne(id);
    }

    /**
     *  Delete the  superAdmin by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SuperAdmin : {}", id);
        superAdminRepository.delete(id);
    }
}
