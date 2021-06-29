package com.mindeavors.queuen.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindeavors.queuen.domain.AuditLog;
import com.mindeavors.queuen.service.AuditLogService;
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
 * REST controller for managing AuditLog.
 */
@RestController
@RequestMapping("/api")
public class AuditLogResource {

    private final Logger log = LoggerFactory.getLogger(AuditLogResource.class);

    private static final String ENTITY_NAME = "auditLog";

    private final AuditLogService auditLogService;

    public AuditLogResource(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    /**
     * POST  /audit-logs : Create a new auditLog.
     *
     * @param auditLog the auditLog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new auditLog, or with status 400 (Bad Request) if the auditLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/audit-logs")
    @Timed
    public ResponseEntity<AuditLog> createAuditLog(@RequestBody AuditLog auditLog) throws URISyntaxException {
        log.debug("REST request to save AuditLog : {}", auditLog);
        if (auditLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new auditLog cannot already have an ID")).body(null);
        }
        AuditLog result = auditLogService.save(auditLog);
        return ResponseEntity.created(new URI("/api/audit-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /audit-logs : Updates an existing auditLog.
     *
     * @param auditLog the auditLog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated auditLog,
     * or with status 400 (Bad Request) if the auditLog is not valid,
     * or with status 500 (Internal Server Error) if the auditLog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/audit-logs")
    @Timed
    public ResponseEntity<AuditLog> updateAuditLog(@RequestBody AuditLog auditLog) throws URISyntaxException {
        log.debug("REST request to update AuditLog : {}", auditLog);
        if (auditLog.getId() == null) {
            return createAuditLog(auditLog);
        }
        AuditLog result = auditLogService.save(auditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, auditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /audit-logs : get all the auditLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of auditLogs in body
     */
    @GetMapping("/audit-logs")
    @Timed
    public ResponseEntity<List<AuditLog>> getAllAuditLogs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AuditLogs");
        Page<AuditLog> page = auditLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/audit-logs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /audit-logs/:id : get the "id" auditLog.
     *
     * @param id the id of the auditLog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the auditLog, or with status 404 (Not Found)
     */
    @GetMapping("/audit-logs/{id}")
    @Timed
    public ResponseEntity<AuditLog> getAuditLog(@PathVariable Long id) {
        log.debug("REST request to get AuditLog : {}", id);
        AuditLog auditLog = auditLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(auditLog));
    }

    /**
     * DELETE  /audit-logs/:id : delete the "id" auditLog.
     *
     * @param id the id of the auditLog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/audit-logs/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuditLog(@PathVariable Long id) {
        log.debug("REST request to delete AuditLog : {}", id);
        auditLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
