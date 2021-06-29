package com.mindeavors.queuen.web.rest;

import com.mindeavors.queuen.QueueNApp;

import com.mindeavors.queuen.domain.SuperAdmin;
import com.mindeavors.queuen.repository.SuperAdminRepository;
import com.mindeavors.queuen.service.SuperAdminService;
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
 * Test class for the SuperAdminResource REST controller.
 *
 * @see SuperAdminResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueNApp.class)
public class SuperAdminResourceIntTest {

    @Autowired
    private SuperAdminRepository superAdminRepository;

    @Autowired
    private SuperAdminService superAdminService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSuperAdminMockMvc;

    private SuperAdmin superAdmin;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuperAdminResource superAdminResource = new SuperAdminResource(superAdminService);
        this.restSuperAdminMockMvc = MockMvcBuilders.standaloneSetup(superAdminResource)
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
    public static SuperAdmin createEntity(EntityManager em) {
        SuperAdmin superAdmin = new SuperAdmin();
        return superAdmin;
    }

    @Before
    public void initTest() {
        superAdmin = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuperAdmin() throws Exception {
        int databaseSizeBeforeCreate = superAdminRepository.findAll().size();

        // Create the SuperAdmin
        restSuperAdminMockMvc.perform(post("/api/super-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(superAdmin)))
            .andExpect(status().isCreated());

        // Validate the SuperAdmin in the database
        List<SuperAdmin> superAdminList = superAdminRepository.findAll();
        assertThat(superAdminList).hasSize(databaseSizeBeforeCreate + 1);
        SuperAdmin testSuperAdmin = superAdminList.get(superAdminList.size() - 1);
    }

    @Test
    @Transactional
    public void createSuperAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = superAdminRepository.findAll().size();

        // Create the SuperAdmin with an existing ID
        superAdmin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuperAdminMockMvc.perform(post("/api/super-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(superAdmin)))
            .andExpect(status().isBadRequest());

        // Validate the SuperAdmin in the database
        List<SuperAdmin> superAdminList = superAdminRepository.findAll();
        assertThat(superAdminList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSuperAdmins() throws Exception {
        // Initialize the database
        superAdminRepository.saveAndFlush(superAdmin);

        // Get all the superAdminList
        restSuperAdminMockMvc.perform(get("/api/super-admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(superAdmin.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSuperAdmin() throws Exception {
        // Initialize the database
        superAdminRepository.saveAndFlush(superAdmin);

        // Get the superAdmin
        restSuperAdminMockMvc.perform(get("/api/super-admins/{id}", superAdmin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(superAdmin.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSuperAdmin() throws Exception {
        // Get the superAdmin
        restSuperAdminMockMvc.perform(get("/api/super-admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuperAdmin() throws Exception {
        // Initialize the database
        superAdminService.save(superAdmin);

        int databaseSizeBeforeUpdate = superAdminRepository.findAll().size();

        // Update the superAdmin
        SuperAdmin updatedSuperAdmin = superAdminRepository.findOne(superAdmin.getId());

        restSuperAdminMockMvc.perform(put("/api/super-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuperAdmin)))
            .andExpect(status().isOk());

        // Validate the SuperAdmin in the database
        List<SuperAdmin> superAdminList = superAdminRepository.findAll();
        assertThat(superAdminList).hasSize(databaseSizeBeforeUpdate);
        SuperAdmin testSuperAdmin = superAdminList.get(superAdminList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSuperAdmin() throws Exception {
        int databaseSizeBeforeUpdate = superAdminRepository.findAll().size();

        // Create the SuperAdmin

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSuperAdminMockMvc.perform(put("/api/super-admins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(superAdmin)))
            .andExpect(status().isCreated());

        // Validate the SuperAdmin in the database
        List<SuperAdmin> superAdminList = superAdminRepository.findAll();
        assertThat(superAdminList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSuperAdmin() throws Exception {
        // Initialize the database
        superAdminService.save(superAdmin);

        int databaseSizeBeforeDelete = superAdminRepository.findAll().size();

        // Get the superAdmin
        restSuperAdminMockMvc.perform(delete("/api/super-admins/{id}", superAdmin.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SuperAdmin> superAdminList = superAdminRepository.findAll();
        assertThat(superAdminList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SuperAdmin.class);
        SuperAdmin superAdmin1 = new SuperAdmin();
        superAdmin1.setId(1L);
        SuperAdmin superAdmin2 = new SuperAdmin();
        superAdmin2.setId(superAdmin1.getId());
        assertThat(superAdmin1).isEqualTo(superAdmin2);
        superAdmin2.setId(2L);
        assertThat(superAdmin1).isNotEqualTo(superAdmin2);
        superAdmin1.setId(null);
        assertThat(superAdmin1).isNotEqualTo(superAdmin2);
    }
}
