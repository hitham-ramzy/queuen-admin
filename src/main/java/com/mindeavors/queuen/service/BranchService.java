package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.Branch;
import com.mindeavors.queuen.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Branch.
 */
@Service
@Transactional
public class BranchService {

    private final Logger log = LoggerFactory.getLogger(BranchService.class);

    private final BranchRepository branchRepository;

    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    /**
     * Save a branch.
     *
     * @param branch the entity to save
     * @return the persisted entity
     */
    public Branch save(Branch branch) {
        log.debug("Request to save Branch : {}", branch);
        return branchRepository.save(branch);
    }

    /**
     *  Get all the branches.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Branch> findAll(Pageable pageable) {
        log.debug("Request to get all Branches");
        return branchRepository.findAll(pageable);
    }

    /**
     *  Get one branch by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Branch findOne(Long id) {
        log.debug("Request to get Branch : {}", id);
        return branchRepository.findOne(id);
    }

    /**
     *  Delete the  branch by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Branch : {}", id);
        branchRepository.delete(id);
    }
}
