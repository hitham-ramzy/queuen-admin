package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.Admin;
import com.mindeavors.queuen.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Admin.
 */
@Service
@Transactional
public class AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Save a admin.
     *
     * @param admin the entity to save
     * @return the persisted entity
     */
    public Admin save(Admin admin) {
        log.debug("Request to save Admin : {}", admin);
        return adminRepository.save(admin);
    }

    /**
     *  Get all the admins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Admin> findAll(Pageable pageable) {
        log.debug("Request to get all Admins");
        return adminRepository.findAll(pageable);
    }

    /**
     *  Get one admin by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Admin findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findOne(id);
    }

    /**
     *  Delete the  admin by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.delete(id);
    }
}
