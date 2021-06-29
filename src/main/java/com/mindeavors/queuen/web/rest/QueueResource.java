package com.mindeavors.queuen.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindeavors.queuen.domain.Queue;
import com.mindeavors.queuen.service.QueueService;
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
 * REST controller for managing Queue.
 */
@RestController
@RequestMapping("/api")
public class QueueResource {

    private final Logger log = LoggerFactory.getLogger(QueueResource.class);

    private static final String ENTITY_NAME = "queue";

    private final QueueService queueService;

    public QueueResource(QueueService queueService) {
        this.queueService = queueService;
    }

    /**
     * POST  /queues : Create a new queue.
     *
     * @param queue the queue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new queue, or with status 400 (Bad Request) if the queue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/queues")
    @Timed
    public ResponseEntity<Queue> createQueue(@Valid @RequestBody Queue queue) throws URISyntaxException {
        log.debug("REST request to save Queue : {}", queue);
        if (queue.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new queue cannot already have an ID")).body(null);
        }
        Queue result = queueService.save(queue);
        return ResponseEntity.created(new URI("/api/queues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /queues : Updates an existing queue.
     *
     * @param queue the queue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated queue,
     * or with status 400 (Bad Request) if the queue is not valid,
     * or with status 500 (Internal Server Error) if the queue couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/queues")
    @Timed
    public ResponseEntity<Queue> updateQueue(@Valid @RequestBody Queue queue) throws URISyntaxException {
        log.debug("REST request to update Queue : {}", queue);
        if (queue.getId() == null) {
            return createQueue(queue);
        }
        Queue result = queueService.save(queue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, queue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /queues : get all the queues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of queues in body
     */
    @GetMapping("/queues")
    @Timed
    public ResponseEntity<List<Queue>> getAllQueues(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Queues");
        Page<Queue> page = queueService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/queues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /queues/:id : get the "id" queue.
     *
     * @param id the id of the queue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the queue, or with status 404 (Not Found)
     */
    @GetMapping("/queues/{id}")
    @Timed
    public ResponseEntity<Queue> getQueue(@PathVariable Long id) {
        log.debug("REST request to get Queue : {}", id);
        Queue queue = queueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(queue));
    }

    /**
     * DELETE  /queues/:id : delete the "id" queue.
     *
     * @param id the id of the queue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/queues/{id}")
    @Timed
    public ResponseEntity<Void> deleteQueue(@PathVariable Long id) {
        log.debug("REST request to delete Queue : {}", id);
        queueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
