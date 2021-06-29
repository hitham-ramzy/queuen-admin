package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.AuditLog;
import com.mindeavors.queuen.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AuditLog.
 */
@Service
@Transactional
public class AuditLogService {

    private final Logger log = LoggerFactory.getLogger(AuditLogService.class);

    private final AuditLogRepository auditLogRepository;

    public AuditLogService(AuditLogRepository auditLogRepository) {
        this.auditLogRepository = auditLogRepository;
    }

    /**
     * Save a auditLog.
     *
     * @param auditLog the entity to save
     * @return the persisted entity
     */
    public AuditLog save(AuditLog auditLog) {
        log.debug("Request to save AuditLog : {}", auditLog);
        return auditLogRepository.save(auditLog);
    }

    /**
     *  Get all the auditLogs.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AuditLog> findAll(Pageable pageable) {
        log.debug("Request to get all AuditLogs");
        return auditLogRepository.findAll(pageable);
    }

    /**
     *  Get one auditLog by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AuditLog findOne(Long id) {
        log.debug("Request to get AuditLog : {}", id);
        return auditLogRepository.findOne(id);
    }

    /**
     *  Delete the  auditLog by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AuditLog : {}", id);
        auditLogRepository.delete(id);
    }
}
