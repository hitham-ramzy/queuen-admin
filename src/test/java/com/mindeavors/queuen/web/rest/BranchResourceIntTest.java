package com.mindeavors.queuen.web.rest;

import com.mindeavors.queuen.QueueNApp;

import com.mindeavors.queuen.domain.Branch;
import com.mindeavors.queuen.domain.Company;
import com.mindeavors.queuen.repository.BranchRepository;
import com.mindeavors.queuen.service.BranchService;
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

import com.mindeavors.queuen.domain.enumeration.Day;
/**
 * Test class for the BranchResource REST controller.
 *
 * @see BranchResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueNApp.class)
public class BranchResourceIntTest {

    private static final Instant DEFAULT_START_WORKING_HOURS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_WORKING_HOURS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_WORKING_HOURS = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_WORKING_HOURS = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Day DEFAULT_WORKING_DAYS = Day.SATURDAY;
    private static final Day UPDATED_WORKING_DAYS = Day.SUNDAY;

    private static final Integer DEFAULT_ACCEPTED_PARALLEL_RESERVATION_NUMBER = 1;
    private static final Integer UPDATED_ACCEPTED_PARALLEL_RESERVATION_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_CREATED_AT = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_AT = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Boolean DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN = false;
    private static final Boolean UPDATED_LAST_DEACTIVATION_BY_SUPER_ADMIN = true;

    @Autowired
    private BranchRepository branchRepository;

    @Autowired
    private BranchService branchService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBranchMockMvc;

    private Branch branch;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BranchResource branchResource = new BranchResource(branchService);
        this.restBranchMockMvc = MockMvcBuilders.standaloneSetup(branchResource)
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
    public static Branch createEntity(EntityManager em) {
        Branch branch = new Branch()
            .startWorkingHours(DEFAULT_START_WORKING_HOURS)
            .endWorkingHours(DEFAULT_END_WORKING_HOURS)
            .workingDays(DEFAULT_WORKING_DAYS)
            .acceptedParallelReservationNumber(DEFAULT_ACCEPTED_PARALLEL_RESERVATION_NUMBER)
            .createdAt(DEFAULT_CREATED_AT)
            .createdBy(DEFAULT_CREATED_BY)
            .active(DEFAULT_ACTIVE)
            .lastDeactivationBySuperAdmin(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN);
        // Add required entity
        Company company = CompanyResourceIntTest.createEntity(em);
        em.persist(company);
        em.flush();
        branch.setCompany(company);
        return branch;
    }

    @Before
    public void initTest() {
        branch = createEntity(em);
    }

    @Test
    @Transactional
    public void createBranch() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // Create the Branch
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate + 1);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getStartWorkingHours()).isEqualTo(DEFAULT_START_WORKING_HOURS);
        assertThat(testBranch.getEndWorkingHours()).isEqualTo(DEFAULT_END_WORKING_HOURS);
        assertThat(testBranch.getWorkingDays()).isEqualTo(DEFAULT_WORKING_DAYS);
        assertThat(testBranch.getAcceptedParallelReservationNumber()).isEqualTo(DEFAULT_ACCEPTED_PARALLEL_RESERVATION_NUMBER);
        assertThat(testBranch.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testBranch.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBranch.isActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testBranch.isLastDeactivationBySuperAdmin()).isEqualTo(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN);
    }

    @Test
    @Transactional
    public void createBranchWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = branchRepository.findAll().size();

        // Create the Branch with an existing ID
        branch.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAcceptedParallelReservationNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setAcceptedParallelReservationNumber(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setCreatedAt(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setCreatedBy(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiveIsRequired() throws Exception {
        int databaseSizeBeforeTest = branchRepository.findAll().size();
        // set the field null
        branch.setActive(null);

        // Create the Branch, which fails.

        restBranchMockMvc.perform(post("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isBadRequest());

        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBranches() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get all the branchList
        restBranchMockMvc.perform(get("/api/branches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(branch.getId().intValue())))
            .andExpect(jsonPath("$.[*].startWorkingHours").value(hasItem(DEFAULT_START_WORKING_HOURS.toString())))
            .andExpect(jsonPath("$.[*].endWorkingHours").value(hasItem(DEFAULT_END_WORKING_HOURS.toString())))
            .andExpect(jsonPath("$.[*].workingDays").value(hasItem(DEFAULT_WORKING_DAYS.toString())))
            .andExpect(jsonPath("$.[*].acceptedParallelReservationNumber").value(hasItem(DEFAULT_ACCEPTED_PARALLEL_RESERVATION_NUMBER)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(sameInstant(DEFAULT_CREATED_AT))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastDeactivationBySuperAdmin").value(hasItem(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN.booleanValue())));
    }

    @Test
    @Transactional
    public void getBranch() throws Exception {
        // Initialize the database
        branchRepository.saveAndFlush(branch);

        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", branch.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(branch.getId().intValue()))
            .andExpect(jsonPath("$.startWorkingHours").value(DEFAULT_START_WORKING_HOURS.toString()))
            .andExpect(jsonPath("$.endWorkingHours").value(DEFAULT_END_WORKING_HOURS.toString()))
            .andExpect(jsonPath("$.workingDays").value(DEFAULT_WORKING_DAYS.toString()))
            .andExpect(jsonPath("$.acceptedParallelReservationNumber").value(DEFAULT_ACCEPTED_PARALLEL_RESERVATION_NUMBER))
            .andExpect(jsonPath("$.createdAt").value(sameInstant(DEFAULT_CREATED_AT)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastDeactivationBySuperAdmin").value(DEFAULT_LAST_DEACTIVATION_BY_SUPER_ADMIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBranch() throws Exception {
        // Get the branch
        restBranchMockMvc.perform(get("/api/branches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBranch() throws Exception {
        // Initialize the database
        branchService.save(branch);

        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Update the branch
        Branch updatedBranch = branchRepository.findOne(branch.getId());
        updatedBranch
            .startWorkingHours(UPDATED_START_WORKING_HOURS)
            .endWorkingHours(UPDATED_END_WORKING_HOURS)
            .workingDays(UPDATED_WORKING_DAYS)
            .acceptedParallelReservationNumber(UPDATED_ACCEPTED_PARALLEL_RESERVATION_NUMBER)
            .createdAt(UPDATED_CREATED_AT)
            .createdBy(UPDATED_CREATED_BY)
            .active(UPDATED_ACTIVE)
            .lastDeactivationBySuperAdmin(UPDATED_LAST_DEACTIVATION_BY_SUPER_ADMIN);

        restBranchMockMvc.perform(put("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBranch)))
            .andExpect(status().isOk());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate);
        Branch testBranch = branchList.get(branchList.size() - 1);
        assertThat(testBranch.getStartWorkingHours()).isEqualTo(UPDATED_START_WORKING_HOURS);
        assertThat(testBranch.getEndWorkingHours()).isEqualTo(UPDATED_END_WORKING_HOURS);
        assertThat(testBranch.getWorkingDays()).isEqualTo(UPDATED_WORKING_DAYS);
        assertThat(testBranch.getAcceptedParallelReservationNumber()).isEqualTo(UPDATED_ACCEPTED_PARALLEL_RESERVATION_NUMBER);
        assertThat(testBranch.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testBranch.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBranch.isActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testBranch.isLastDeactivationBySuperAdmin()).isEqualTo(UPDATED_LAST_DEACTIVATION_BY_SUPER_ADMIN);
    }

    @Test
    @Transactional
    public void updateNonExistingBranch() throws Exception {
        int databaseSizeBeforeUpdate = branchRepository.findAll().size();

        // Create the Branch

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBranchMockMvc.perform(put("/api/branches")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(branch)))
            .andExpect(status().isCreated());

        // Validate the Branch in the database
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBranch() throws Exception {
        // Initialize the database
        branchService.save(branch);

        int databaseSizeBeforeDelete = branchRepository.findAll().size();

        // Get the branch
        restBranchMockMvc.perform(delete("/api/branches/{id}", branch.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Branch> branchList = branchRepository.findAll();
        assertThat(branchList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Branch.class);
        Branch branch1 = new Branch();
        branch1.setId(1L);
        Branch branch2 = new Branch();
        branch2.setId(branch1.getId());
        assertThat(branch1).isEqualTo(branch2);
        branch2.setId(2L);
        assertThat(branch1).isNotEqualTo(branch2);
        branch1.setId(null);
        assertThat(branch1).isNotEqualTo(branch2);
    }
}
