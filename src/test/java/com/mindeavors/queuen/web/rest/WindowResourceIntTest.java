package com.mindeavors.queuen.web.rest;

import com.mindeavors.queuen.QueueNApp;

import com.mindeavors.queuen.domain.Window;
import com.mindeavors.queuen.domain.BranchService;
import com.mindeavors.queuen.repository.WindowRepository;
import com.mindeavors.queuen.service.WindowService;
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
 * Test class for the WindowResource REST controller.
 *
 * @see WindowResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QueueNApp.class)
public class WindowResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private WindowRepository windowRepository;

    @Autowired
    private WindowService windowService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restWindowMockMvc;

    private Window window;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WindowResource windowResource = new WindowResource(windowService);
        this.restWindowMockMvc = MockMvcBuilders.standaloneSetup(windowResource)
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
    public static Window createEntity(EntityManager em) {
        Window window = new Window()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        BranchService service = BranchServiceResourceIntTest.createEntity(em);
        em.persist(service);
        em.flush();
        window.setService(service);
        return window;
    }

    @Before
    public void initTest() {
        window = createEntity(em);
    }

    @Test
    @Transactional
    public void createWindow() throws Exception {
        int databaseSizeBeforeCreate = windowRepository.findAll().size();

        // Create the Window
        restWindowMockMvc.perform(post("/api/windows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(window)))
            .andExpect(status().isCreated());

        // Validate the Window in the database
        List<Window> windowList = windowRepository.findAll();
        assertThat(windowList).hasSize(databaseSizeBeforeCreate + 1);
        Window testWindow = windowList.get(windowList.size() - 1);
        assertThat(testWindow.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWindow.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createWindowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = windowRepository.findAll().size();

        // Create the Window with an existing ID
        window.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWindowMockMvc.perform(post("/api/windows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(window)))
            .andExpect(status().isBadRequest());

        // Validate the Window in the database
        List<Window> windowList = windowRepository.findAll();
        assertThat(windowList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = windowRepository.findAll().size();
        // set the field null
        window.setName(null);

        // Create the Window, which fails.

        restWindowMockMvc.perform(post("/api/windows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(window)))
            .andExpect(status().isBadRequest());

        List<Window> windowList = windowRepository.findAll();
        assertThat(windowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWindows() throws Exception {
        // Initialize the database
        windowRepository.saveAndFlush(window);

        // Get all the windowList
        restWindowMockMvc.perform(get("/api/windows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(window.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getWindow() throws Exception {
        // Initialize the database
        windowRepository.saveAndFlush(window);

        // Get the window
        restWindowMockMvc.perform(get("/api/windows/{id}", window.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(window.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWindow() throws Exception {
        // Get the window
        restWindowMockMvc.perform(get("/api/windows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWindow() throws Exception {
        // Initialize the database
        windowService.save(window);

        int databaseSizeBeforeUpdate = windowRepository.findAll().size();

        // Update the window
        Window updatedWindow = windowRepository.findOne(window.getId());
        updatedWindow
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restWindowMockMvc.perform(put("/api/windows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWindow)))
            .andExpect(status().isOk());

        // Validate the Window in the database
        List<Window> windowList = windowRepository.findAll();
        assertThat(windowList).hasSize(databaseSizeBeforeUpdate);
        Window testWindow = windowList.get(windowList.size() - 1);
        assertThat(testWindow.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWindow.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingWindow() throws Exception {
        int databaseSizeBeforeUpdate = windowRepository.findAll().size();

        // Create the Window

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restWindowMockMvc.perform(put("/api/windows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(window)))
            .andExpect(status().isCreated());

        // Validate the Window in the database
        List<Window> windowList = windowRepository.findAll();
        assertThat(windowList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteWindow() throws Exception {
        // Initialize the database
        windowService.save(window);

        int databaseSizeBeforeDelete = windowRepository.findAll().size();

        // Get the window
        restWindowMockMvc.perform(delete("/api/windows/{id}", window.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Window> windowList = windowRepository.findAll();
        assertThat(windowList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Window.class);
        Window window1 = new Window();
        window1.setId(1L);
        Window window2 = new Window();
        window2.setId(window1.getId());
        assertThat(window1).isEqualTo(window2);
        window2.setId(2L);
        assertThat(window1).isNotEqualTo(window2);
        window1.setId(null);
        assertThat(window1).isNotEqualTo(window2);
    }
}
