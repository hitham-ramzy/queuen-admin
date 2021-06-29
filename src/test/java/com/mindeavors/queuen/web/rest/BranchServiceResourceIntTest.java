package com.mindeavors.queuen.web.rest;

import com.mindeavors.queuen.QueueNApp;

import com.mindeavors.queuen.domain.BranchService;
import com.mindeavors.queuen.domain.Branch;
import com.mindeavors.queuen.repository.BranchServiceRepository;
import com.mindeavors.queuen.service.BranchServiceService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.mindeavors.queuen.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the BranchServiceResource REST controller.
 *
 * @see BranchServiceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueNApp.class)
public class BranchServiceResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTING_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTING_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDING_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDING_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_AVERAGE_SERVING_MINUTES = 1D;
    private static final Double UPDATED_AVERAGE_SERVING_MINUTES = 2D;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN = false;
    private static final Boolean UPDATED_LAST_DEACTIVATION_BY_SUPER_ADMIN = true;

    @Autowired
    private BranchServiceRepository branchServiceRepository;

    @Autowired
    private BranchServiceService branchServiceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBranchServiceMockMvc;

    private BranchService branchService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BranchServiceResource branchServiceResource = new BranchServiceResource(branchServiceService);
        this.restBranchServiceMockMvc = MockMvcBuilders.standaloneSetup(branchServiceResource)
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
    public static BranchService createEntity(EntityManager em) {
        BranchService branchService = new BranchService()
            .name(DEFAULT_NAME)
            .startingTime(DEFAULT_STARTING_TIME)
            .endingTime(DEFAULT_ENDING_TIME)
            .averageServingMinutes(DEFAULT_AVERAGE_SERVING_MINUTES)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .active(DEFAULT_ACTIVE)
            .lastDeactivationBySuperAdmin(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN);
        // Add required entity
        Branch branch = BranchResourceIntTest.createEntity(em);
        em.persist(branch);
        em.flush();
        branchService.setBranch(branch);
        return branchService;
    }

    @Before
    public void initTest() {
        branchService = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranchService() throws Exception {
        int databaseSizeBeforeCreate = branchServiceRepository.findAll().size();

        // Create the BranchService
        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isCreated());

        // Validate the BranchService in the database
        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeCreate + 1);
        BranchService testBranchService = branchServiceList.get(branchServiceList.size() - 1);
        assertThat(testBranchService.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBranchService.getStartingTime()).isEqualTo(DEFAULT_STARTING_TIME);
        assertThat(testBranchService.getEndingTime()).isEqualTo(DEFAULT_ENDING_TIME);
        assertThat(testBranchService.getAverageServingMinutes()).isEqualTo(DEFAULT_AVERAGE_SERVING_MINUTES);
        assertThat(testBranchService.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBranchService.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBranchService.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBranchService.isLastDeactivationBySuperAdmin()).isEqualTo(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN);
    }

    @Test
    @Transactional
    public void createBranchServiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchServiceRepository.findAll().size();

        // Create the BranchService with an existing ID
        branchService.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isBadRequest());

        // Validate the BranchService in the database
        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchServiceRepository.findAll().size();
        // set the field null
        branchService.setName(null);

        // Create the BranchService, which fails.

        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isBadRequest());

        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAverageServingMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchServiceRepository.findAll().size();
        // set the field null
        branchService.setAverageServingMinutes(null);

        // Create the BranchService, which fails.

        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isBadRequest());

        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchServiceRepository.findAll().size();
        // set the field null
        branchService.setCreatedAt(null);

        // Create the BranchService, which fails.

        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isBadRequest());

        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchServiceRepository.findAll().size();
        // set the field null
        branchService.setCreatedBy(null);

        // Create the BranchService, which fails.

        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isBadRequest());

        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchServiceRepository.findAll().size();
        // set the field null
        branchService.setActive(null);

        // Create the BranchService, which fails.

        restBranchServiceMockMvc.perform(post("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isBadRequest());

        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBranchServices() throws Exception {
        // Initialize the database
        branchServiceRepository.saveAndFlush(branchService);

        // Get all the branchServiceList
        restBranchServiceMockMvc.perform(get("/api/branch-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branchService.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startingTime").value(hasItem(DEFAULT_STARTING_TIME.toString())))
            .andExpect(jsonPath("$.[*].endingTime").value(hasItem(DEFAULT_ENDING_TIME.toString())))
            .andExpect(jsonPath("$.[*].averageServingMinutes").value(hasItem(DEFAULT_AVERAGE_SERVING_MINUTES.doubleValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastDeactivationBySuperAdmin").value(hasItem(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN.booleanValue())));
    }

    @Test
    @Transactional
    public void getBranchService() throws Exception {
        // Initialize the database
        branchServiceRepository.saveAndFlush(branchService);

        // Get the branchService
        restBranchServiceMockMvc.perform(get("/api/branch-services/{id}", branchService.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(branchService.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startingTime").value(DEFAULT_STARTING_TIME.toString()))
            .andExpect(jsonPath("$.endingTime").value(DEFAULT_ENDING_TIME.toString()))
            .andExpect(jsonPath("$.averageServingMinutes").value(DEFAULT_AVERAGE_SERVING_MINUTES.doubleValue()))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastDeactivationBySuperAdmin").value(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBranchService() throws Exception {
        // Get the branchService
        restBranchServiceMockMvc.perform(get("/api/branch-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranchService() throws Exception {
        // Initialize the database
        branchServiceService.save(branchService);

        int databaseSizeBeforeUpdate = branchServiceRepository.findAll().size();

        // Update the branchService
        BranchService updatedBranchService = branchServiceRepository.findOne(branchService.getId());
        updatedBranchService
            .name(UPDATED_NAME)
            .startingTime(UPDATED_STARTING_TIME)
            .endingTime(UPDATED_ENDING_TIME)
            .averageServingMinutes(UPDATED_AVERAGE_SERVING_MINUTES)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .active(UPDATED_ACTIVE)
            .lastDeactivationBySuperAdmin(UPDATED_LAST_DEACTIVATION_BY_SUPER_ADMIN);

        restBranchServiceMockMvc.perform(put("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranchService)))
            .andExpect(status().isOk());

        // Validate the BranchService in the database
        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeUpdate);
        BranchService testBranchService = branchServiceList.get(branchServiceList.size() - 1);
        assertThat(testBranchService.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBranchService.getStartingTime()).isEqualTo(UPDATED_STARTING_TIME);
        assertThat(testBranchService.getEndingTime()).isEqualTo(UPDATED_ENDING_TIME);
        assertThat(testBranchService.getAverageServingMinutes()).isEqualTo(UPDATED_AVERAGE_SERVING_MINUTES);
        assertThat(testBranchService.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBranchService.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBranchService.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBranchService.isLastDeactivationBySuperAdmin()).isEqualTo(UPDATED_LAST_DEACTIVATION_BY_SUPER_ADMIN);
    }

    @Test
    @Transactional
    public void updateNonExistingBranchService() throws Exception {
        int databaseSizeBeforeUpdate = branchServiceRepository.findAll().size();

        // Create the BranchService

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBranchServiceMockMvc.perform(put("/api/branch-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branchService)))
            .andExpect(status().isCreated());

        // Validate the BranchService in the database
        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBranchService() throws Exception {
        // Initialize the database
        branchServiceService.save(branchService);

        int databaseSizeBeforeDelete = branchServiceRepository.findAll().size();

        // Get the branchService
        restBranchServiceMockMvc.perform(delete("/api/branch-services/{id}", branchService.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<BranchService> branchServiceList = branchServiceRepository.findAll();
        assertThat(branchServiceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BranchService.class);
        BranchService branchService1 = new BranchService();
        branchService1.setId(1L);
        BranchService branchService2 = new BranchService();
        branchService2.setId(branchService1.getId());
        assertThat(branchService1).isEqualTo(branchService2);
        branchService2.setId(2L);
        assertThat(branchService1).isNotEqualTo(branchService2);
        branchService1.setId(null);
        assertThat(branchService1).isNotEqualTo(branchService2);
    }
}
