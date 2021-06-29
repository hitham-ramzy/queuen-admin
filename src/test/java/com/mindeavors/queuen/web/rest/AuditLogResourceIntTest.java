package com.mindeavors.queuen.web.rest;

import com.mindeavors.queuen.QueueNApp;

import com.mindeavors.queuen.domain.AuditLog;
import com.mindeavors.queuen.repository.AuditLogRepository;
import com.mindeavors.queuen.service.AuditLogService;
import com.mindeavors.queuen.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuditLogResource REST controller.
 *
 * @see AuditLogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueNApp.class)
public class AuditLogResourceIntTest {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAuditLogMockMvc;

    private AuditLog auditLog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuditLogResource auditLogResource = new AuditLogResource(auditLogService);
        this.restAuditLogMockMvc = MockMvcBuilders.standaloneSetup(auditLogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditLog createEntity(EntityManager em) {
        AuditLog auditLog = new AuditLog();
        return auditLog;
    }

    @Before
    public void initTest() {
        auditLog = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuditLog() throws Exception {
        int databaseSizeBeforeCreate = auditLogRepository.findAll().size();

        // Create the AuditLog
        restAuditLogMockMvc.perform(post("/api/audit-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditLog)))
            .andExpect(status().isCreated());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeCreate + 1);
        AuditLog testAuditLog = auditLogList.get(auditLogList.size() - 1);
    }

    @Test
    @Transactional
    public void createAuditLogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auditLogRepository.findAll().size();

        // Create the AuditLog with an existing ID
        auditLog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditLogMockMvc.perform(post("/api/audit-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditLog)))
            .andExpect(status().isBadRequest());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAuditLogs() throws Exception {
        // Initialize the database
        auditLogRepository.saveAndFlush(auditLog);

        // Get all the auditLogList
        restAuditLogMockMvc.perform(get("/api/audit-logs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditLog.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.saveAndFlush(auditLog);

        // Get the auditLog
        restAuditLogMockMvc.perform(get("/api/audit-logs/{id}", auditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auditLog.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuditLog() throws Exception {
        // Get the auditLog
        restAuditLogMockMvc.perform(get("/api/audit-logs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuditLog() throws Exception {
        // Initialize the database
        auditLogService.save(auditLog);

        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();

        // Update the auditLog
        AuditLog updatedAuditLog = auditLogRepository.findOne(auditLog.getId());

        restAuditLogMockMvc.perform(put("/api/audit-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuditLog)))
            .andExpect(status().isOk());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
        AuditLog testAuditLog = auditLogList.get(auditLogList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();

        // Create the AuditLog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAuditLogMockMvc.perform(put("/api/audit-logs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auditLog)))
            .andExpect(status().isCreated());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAuditLog() throws Exception {
        // Initialize the database
        auditLogService.save(auditLog);

        int databaseSizeBeforeDelete = auditLogRepository.findAll().size();

        // Get the auditLog
        restAuditLogMockMvc.perform(delete("/api/audit-logs/{id}", auditLog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuditLog.class);
        AuditLog auditLog1 = new AuditLog();
        auditLog1.setId(1L);
        AuditLog auditLog2 = new AuditLog();
        auditLog2.setId(auditLog1.getId());
        assertThat(auditLog1).isEqualTo(auditLog2);
        auditLog2.setId(2L);
        assertThat(auditLog1).isNotEqualTo(auditLog2);
        auditLog1.setId(null);
        assertThat(auditLog1).isNotEqualTo(auditLog2);
    }
}
