package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.BranchService;
import com.mindeavors.queuen.repository.BranchServiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing BranchService.
 */
@Service
@Transactional
public class BranchServiceService {

    private final Logger log = LoggerFactory.getLogger(BranchServiceService.class);

    private final BranchServiceRepository branchServiceRepository;

    public BranchServiceService(BranchServiceRepository branchServiceRepository) {
        this.branchServiceRepository = branchServiceRepository;
    }

    /**
     * Save a branchService.
     *
     * @param branchService the entity to save
     * @return the persisted entity
     */
    public BranchService save(BranchService branchService) {
        log.debug("Request to save BranchService : {}", branchService);
        return branchServiceRepository.save(branchService);
    }

    /**
     *  Get all the branchServices.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<BranchService> findAll(Pageable pageable) {
        log.debug("Request to get all BranchServices");
        return branchServiceRepository.findAll(pageable);
    }

    /**
     *  Get one branchService by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public BranchService findOne(Long id) {
        log.debug("Request to get BranchService : {}", id);
        return branchServiceRepository.findOne(id);
    }

    /**
     *  Delete the  branchService by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete BranchService : {}", id);
        branchServiceRepository.delete(id);
    }
}
