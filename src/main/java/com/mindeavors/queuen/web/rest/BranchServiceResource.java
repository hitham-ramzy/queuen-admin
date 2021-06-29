package com.mindeavors.queuen.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindeavors.queuen.domain.BranchService;
import com.mindeavors.queuen.service.BranchServiceService;
import com.mindeavors.queuen.web.rest.util.HeaderUtil;
import com.mindeavors.queuen.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing BranchService.
 */
@RestController
@RequestMapping("/api")
public class BranchServiceResource {

    private final Logger log = LoggerFactory.getLogger(BranchServiceResource.class);

    private static final String ENTITY_NAME = "branchService";

    private final BranchServiceService branchServiceService;

    public BranchServiceResource(BranchServiceService branchServiceService) {
        this.branchServiceService = branchServiceService;
    }

    /**
     * POST  /branch-services : Create a new branchService.
     *
     * @param branchService the branchService to create
     * @return the ResponseEntity with status 201 (Created) and with body the new branchService, or with status 400 (Bad Request) if the branchService has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/branch-services")
    @Timed
    public ResponseEntity<BranchService> createBranchService(@Valid @RequestBody BranchService branchService) throws URISyntaxException {
        log.debug("REST request to save BranchService : {}", branchService);
        if (branchService.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new branchService cannot already have an ID")).body(null);
        }
        BranchService result = branchServiceService.save(branchService);
        return ResponseEntity.created(new URI("/api/branch-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /branch-services : Updates an existing branchService.
     *
     * @param branchService the branchService to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated branchService,
     * or with status 400 (Bad Request) if the branchService is not valid,
     * or with status 500 (Internal Server Error) if the branchService couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/branch-services")
    @Timed
    public ResponseEntity<BranchService> updateBranchService(@Valid @RequestBody BranchService branchService) throws URISyntaxException {
        log.debug("REST request to update BranchService : {}", branchService);
        if (branchService.getId() == null) {
            return createBranchService(branchService);
        }
        BranchService result = branchServiceService.save(branchService);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, branchService.getId().toString()))
            .body(result);
    }

    /**
     * GET  /branch-services : get all the branchServices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of branchServices in body
     */
    @GetMapping("/branch-services")
    @Timed
    public ResponseEntity<List<BranchService>> getAllBranchServices(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of BranchServices");
        Page<BranchService> page = branchServiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/branch-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /branch-services/:id : get the "id" branchService.
     *
     * @param id the id of the branchService to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the branchService, or with status 404 (Not Found)
     */
    @GetMapping("/branch-services/{id}")
    @Timed
    public ResponseEntity<BranchService> getBranchService(@PathVariable Long id) {
        log.debug("REST request to get BranchService : {}", id);
        BranchService branchService = branchServiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(branchService));
    }

    /**
     * DELETE  /branch-services/:id : delete the "id" branchService.
     *
     * @param id the id of the branchService to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/branch-services/{id}")
    @Timed
    public ResponseEntity<Void> deleteBranchService(@PathVariable Long id) {
        log.debug("REST request to delete BranchService : {}", id);
        branchServiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
