package com.mindeavors.queuen.web.rest;

import com.mindeavors.queuen.QueueNApp;

import com.mindeavors.queuen.domain.Queue;
import com.mindeavors.queuen.domain.BranchService;
import com.mindeavors.queuen.repository.QueueRepository;
import com.mindeavors.queuen.service.QueueService;
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
import java.util.List;

import static com.mindeavors.queuen.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mindeavors.queuen.domain.enumeration.QueueStatus;
/**
 * Test class for the QueueResource REST controller.
 *
 * @see QueueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueNApp.class)
public class QueueResourceIntTest {

    private static final QueueStatus DEFAULT_STATUS = QueueStatus.ACTIVE;
    private static final QueueStatus UPDATED_STATUS = QueueStatus.INACTIVE;

    private static final ZonedDateTime DEFAULT_DAY = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DAY = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_CURRENT_SERVING_NUMBER = 1;
    private static final Integer UPDATED_CURRENT_SERVING_NUMBER = 2;

    private static final Integer DEFAULT_LAST_RESERVED_NUMBER = 1;
    private static final Integer UPDATED_LAST_RESERVED_NUMBER = 2;

    private static final Boolean DEFAULT_LAST_STATUS_BY_SUPER_ADMIN = false;
    private static final Boolean UPDATED_LAST_STATUS_BY_SUPER_ADMIN = true;

    @Autowired
    private QueueRepository queueRepository;

    @Autowired
    private QueueService queueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQueueMockMvc;

    private Queue queue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QueueResource queueResource = new QueueResource(queueService);
        this.restQueueMockMvc = MockMvcBuilders.standaloneSetup(queueResource)
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
    public static Queue createEntity(EntityManager em) {
        Queue queue = new Queue()
            .status(DEFAULT_STATUS)
            .day(DEFAULT_DAY)
            .currentServingNumber(DEFAULT_CURRENT_SERVING_NUMBER)
            .lastReservedNumber(DEFAULT_LAST_RESERVED_NUMBER)
            .lastStatusBySuperAdmin(DEFAULT_LAST_STATUS_BY_SUPER_ADMIN);
        // Add required entity
        BranchService service = BranchServiceResourceIntTest.createEntity(em);
        em.persist(service);
        em.flush();
        queue.setService(service);
        return queue;
    }

    @Before
    public void initTest() {
        queue = createEntity(em);
    }

    @Test
    @Transactional
    public void createQueue() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue
        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isCreated());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeCreate + 1);
        Queue testQueue = queueList.get(queueList.size() - 1);
        assertThat(testQueue.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testQueue.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testQueue.getCurrentServingNumber()).isEqualTo(DEFAULT_CURRENT_SERVING_NUMBER);
        assertThat(testQueue.getLastReservedNumber()).isEqualTo(DEFAULT_LAST_RESERVED_NUMBER);
        assertThat(testQueue.isLastStatusBySuperAdmin()).isEqualTo(DEFAULT_LAST_STATUS_BY_SUPER_ADMIN);
    }

    @Test
    @Transactional
    public void createQueueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queueRepository.findAll().size();

        // Create the Queue with an existing ID
        queue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = queueRepository.findAll().size();
        // set the field null
        queue.setStatus(null);

        // Create the Queue, which fails.

        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = queueRepository.findAll().size();
        // set the field null
        queue.setDay(null);

        // Create the Queue, which fails.

        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCurrentServingNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = queueRepository.findAll().size();
        // set the field null
        queue.setCurrentServingNumber(null);

        // Create the Queue, which fails.

        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLastReservedNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = queueRepository.findAll().size();
        // set the field null
        queue.setLastReservedNumber(null);

        // Create the Queue, which fails.

        restQueueMockMvc.perform(post("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isBadRequest());

        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQueues() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get all the queueList
        restQueueMockMvc.perform(get("/api/queues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(queue.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(sameInstant(DEFAULT_DAY))))
            .andExpect(jsonPath("$.[*].currentServingNumber").value(hasItem(DEFAULT_CURRENT_SERVING_NUMBER)))
            .andExpect(jsonPath("$.[*].lastReservedNumber").value(hasItem(DEFAULT_LAST_RESERVED_NUMBER)))
            .andExpect(jsonPath("$.[*].lastStatusBySuperAdmin").value(hasItem(DEFAULT_LAST_STATUS_BY_SUPER_ADMIN.booleanValue())));
    }

    @Test
    @Transactional
    public void getQueue() throws Exception {
        // Initialize the database
        queueRepository.saveAndFlush(queue);

        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", queue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(queue.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.day").value(sameInstant(DEFAULT_DAY)))
            .andExpect(jsonPath("$.currentServingNumber").value(DEFAULT_CURRENT_SERVING_NUMBER))
            .andExpect(jsonPath("$.lastReservedNumber").value(DEFAULT_LAST_RESERVED_NUMBER))
            .andExpect(jsonPath("$.lastStatusBySuperAdmin").value(DEFAULT_LAST_STATUS_BY_SUPER_ADMIN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingQueue() throws Exception {
        // Get the queue
        restQueueMockMvc.perform(get("/api/queues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQueue() throws Exception {
        // Initialize the database
        queueService.save(queue);

        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Update the queue
        Queue updatedQueue = queueRepository.findOne(queue.getId());
        updatedQueue
            .status(UPDATED_STATUS)
            .day(UPDATED_DAY)
            .currentServingNumber(UPDATED_CURRENT_SERVING_NUMBER)
            .lastReservedNumber(UPDATED_LAST_RESERVED_NUMBER)
            .lastStatusBySuperAdmin(UPDATED_LAST_STATUS_BY_SUPER_ADMIN);

        restQueueMockMvc.perform(put("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQueue)))
            .andExpect(status().isOk());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeUpdate);
        Queue testQueue = queueList.get(queueList.size() - 1);
        assertThat(testQueue.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testQueue.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testQueue.getCurrentServingNumber()).isEqualTo(UPDATED_CURRENT_SERVING_NUMBER);
        assertThat(testQueue.getLastReservedNumber()).isEqualTo(UPDATED_LAST_RESERVED_NUMBER);
        assertThat(testQueue.isLastStatusBySuperAdmin()).isEqualTo(UPDATED_LAST_STATUS_BY_SUPER_ADMIN);
    }

    @Test
    @Transactional
    public void updateNonExistingQueue() throws Exception {
        int databaseSizeBeforeUpdate = queueRepository.findAll().size();

        // Create the Queue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restQueueMockMvc.perform(put("/api/queues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(queue)))
            .andExpect(status().isCreated());

        // Validate the Queue in the database
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteQueue() throws Exception {
        // Initialize the database
        queueService.save(queue);

        int databaseSizeBeforeDelete = queueRepository.findAll().size();

        // Get the queue
        restQueueMockMvc.perform(delete("/api/queues/{id}", queue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Queue> queueList = queueRepository.findAll();
        assertThat(queueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Queue.class);
        Queue queue1 = new Queue();
        queue1.setId(1L);
        Queue queue2 = new Queue();
        queue2.setId(queue1.getId());
        assertThat(queue1).isEqualTo(queue2);
        queue2.setId(2L);
        assertThat(queue1).isNotEqualTo(queue2);
        queue1.setId(null);
        assertThat(queue1).isNotEqualTo(queue2);
    }
}
