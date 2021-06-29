package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.Queue;
import com.mindeavors.queuen.repository.QueueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Queue.
 */
@Service
@Transactional
public class QueueService {

    private final Logger log = LoggerFactory.getLogger(QueueService.class);

    private final QueueRepository queueRepository;

    public QueueService(QueueRepository queueRepository) {
        this.queueRepository = queueRepository;
    }

    /**
     * Save a queue.
     *
     * @param queue the entity to save
     * @return the persisted entity
     */
    public Queue save(Queue queue) {
        log.debug("Request to save Queue : {}", queue);
        return queueRepository.save(queue);
    }

    /**
     *  Get all the queues.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Queue> findAll(Pageable pageable) {
        log.debug("Request to get all Queues");
        return queueRepository.findAll(pageable);
    }

    /**
     *  Get one queue by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Queue findOne(Long id) {
        log.debug("Request to get Queue : {}", id);
        return queueRepository.findOne(id);
    }

    /**
     *  Delete the  queue by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Queue : {}", id);
        queueRepository.delete(id);
    }
}
