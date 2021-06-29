package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.Window;
import com.mindeavors.queuen.repository.WindowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Window.
 */
@Service
@Transactional
public class WindowService {

    private final Logger log = LoggerFactory.getLogger(WindowService.class);

    private final WindowRepository windowRepository;

    public WindowService(WindowRepository windowRepository) {
        this.windowRepository = windowRepository;
    }

    /**
     * Save a window.
     *
     * @param window the entity to save
     * @return the persisted entity
     */
    public Window save(Window window) {
        log.debug("Request to save Window : {}", window);
        return windowRepository.save(window);
    }

    /**
     *  Get all the windows.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Window> findAll(Pageable pageable) {
        log.debug("Request to get all Windows");
        return windowRepository.findAll(pageable);
    }

    /**
     *  Get one window by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Window findOne(Long id) {
        log.debug("Request to get Window : {}", id);
        return windowRepository.findOne(id);
    }

    /**
     *  Delete the  window by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Window : {}", id);
        windowRepository.delete(id);
    }
}
