package com.mindeavors.queuen.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mindeavors.queuen.domain.Window;
import com.mindeavors.queuen.service.WindowService;
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
 * REST controller for managing Window.
 */
@RestController
@RequestMapping("/api")
public class WindowResource {

    private final Logger log = LoggerFactory.getLogger(WindowResource.class);

    private static final String ENTITY_NAME = "window";

    private final WindowService windowService;

    public WindowResource(WindowService windowService) {
        this.windowService = windowService;
    }

    /**
     * POST  /windows : Create a new window.
     *
     * @param window the window to create
     * @return the ResponseEntity with status 201 (Created) and with body the new window, or with status 400 (Bad Request) if the window has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/windows")
    @Timed
    public ResponseEntity<Window> createWindow(@Valid @RequestBody Window window) throws URISyntaxException {
        log.debug("REST request to save Window : {}", window);
        if (window.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new window cannot already have an ID")).body(null);
        }
        Window result = windowService.save(window);
        return ResponseEntity.created(new URI("/api/windows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /windows : Updates an existing window.
     *
     * @param window the window to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated window,
     * or with status 400 (Bad Request) if the window is not valid,
     * or with status 500 (Internal Server Error) if the window couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/windows")
    @Timed
    public ResponseEntity<Window> updateWindow(@Valid @RequestBody Window window) throws URISyntaxException {
        log.debug("REST request to update Window : {}", window);
        if (window.getId() == null) {
            return createWindow(window);
        }
        Window result = windowService.save(window);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, window.getId().toString()))
            .body(result);
    }

    /**
     * GET  /windows : get all the windows.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of windows in body
     */
    @GetMapping("/windows")
    @Timed
    public ResponseEntity<List<Window>> getAllWindows(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Windows");
        Page<Window> page = windowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/windows");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /windows/:id : get the "id" window.
     *
     * @param id the id of the window to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the window, or with status 404 (Not Found)
     */
    @GetMapping("/windows/{id}")
    @Timed
    public ResponseEntity<Window> getWindow(@PathVariable Long id) {
        log.debug("REST request to get Window : {}", id);
        Window window = windowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(window));
    }

    /**
     * DELETE  /windows/:id : delete the "id" window.
     *
     * @param id the id of the window to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/windows/{id}")
    @Timed
    public ResponseEntity<Void> deleteWindow(@PathVariable Long id) {
        log.debug("REST request to delete Window : {}", id);
        windowService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
