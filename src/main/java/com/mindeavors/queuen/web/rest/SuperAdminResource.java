package com.mindeavors.queuen.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindeavors.queuen.domain.SuperAdmin;
import com.mindeavors.queuen.service.SuperAdminService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SuperAdmin.
 */
@RestController
@RequestMapping("/api")
public class SuperAdminResource {

    private final Logger log = LoggerFactory.getLogger(SuperAdminResource.class);

    private static final String ENTITY_NAME = "superAdmin";

    private final SuperAdminService superAdminService;

    public SuperAdminResource(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    /**
     * POST  /super-admins : Create a new superAdmin.
     *
     * @param superAdmin the superAdmin to create
     * @return the ResponseEntity with status 201 (Created) and with body the new superAdmin, or with status 400 (Bad Request) if the superAdmin has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/super-admins")
    @Timed
    public ResponseEntity<SuperAdmin> createSuperAdmin(@RequestBody SuperAdmin superAdmin) throws URISyntaxException {
        log.debug("REST request to save SuperAdmin : {}", superAdmin);
        if (superAdmin.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new superAdmin cannot already have an ID")).body(null);
        }
        SuperAdmin result = superAdminService.save(superAdmin);
        return ResponseEntity.created(new URI("/api/super-admins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /super-admins : Updates an existing superAdmin.
     *
     * @param superAdmin the superAdmin to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated superAdmin,
     * or with status 400 (Bad Request) if the superAdmin is not valid,
     * or with status 500 (Internal Server Error) if the superAdmin couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/super-admins")
    @Timed
    public ResponseEntity<SuperAdmin> updateSuperAdmin(@RequestBody SuperAdmin superAdmin) throws URISyntaxException {
        log.debug("REST request to update SuperAdmin : {}", superAdmin);
        if (superAdmin.getId() == null) {
            return createSuperAdmin(superAdmin);
        }
        SuperAdmin result = superAdminService.save(superAdmin);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, superAdmin.getId().toString()))
            .body(result);
    }

    /**
     * GET  /super-admins : get all the superAdmins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of superAdmins in body
     */
    @GetMapping("/super-admins")
    @Timed
    public ResponseEntity<List<SuperAdmin>> getAllSuperAdmins(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of SuperAdmins");
        Page<SuperAdmin> page = superAdminService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/super-admins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /super-admins/:id : get the "id" superAdmin.
     *
     * @param id the id of the superAdmin to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the superAdmin, or with status 404 (Not Found)
     */
    @GetMapping("/super-admins/{id}")
    @Timed
    public ResponseEntity<SuperAdmin> getSuperAdmin(@PathVariable Long id) {
        log.debug("REST request to get SuperAdmin : {}", id);
        SuperAdmin superAdmin = superAdminService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(superAdmin));
    }

    /**
     * DELETE  /super-admins/:id : delete the "id" superAdmin.
     *
     * @param id the id of the superAdmin to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/super-admins/{id}")
    @Timed
    public ResponseEntity<Void> deleteSuperAdmin(@PathVariable Long id) {
        log.debug("REST request to delete SuperAdmin : {}", id);
        superAdminService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
