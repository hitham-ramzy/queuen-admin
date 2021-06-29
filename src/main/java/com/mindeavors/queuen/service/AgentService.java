package com.mindeavors.queuen.service;

import com.mindeavors.queuen.domain.Agent;
import com.mindeavors.queuen.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Agent.
 */
@Service
@Transactional
public class AgentService {

    private final Logger log = LoggerFactory.getLogger(AgentService.class);

    private final AgentRepository agentRepository;

    public AgentService(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    /**
     * Save a agent.
     *
     * @param agent the entity to save
     * @return the persisted entity
     */
    public Agent save(Agent agent) {
        log.debug("Request to save Agent : {}", agent);
        return agentRepository.save(agent);
    }

    /**
     *  Get all the agents.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Agent> findAll(Pageable pageable) {
        log.debug("Request to get all Agents");
        return agentRepository.findAll(pageable);
    }

    /**
     *  Get one agent by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Agent findOne(Long id) {
        log.debug("Request to get Agent : {}", id);
        return agentRepository.findOne(id);
    }

    /**
     *  Delete the  agent by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Agent : {}", id);
        agentRepository.delete(id);
    }
}
