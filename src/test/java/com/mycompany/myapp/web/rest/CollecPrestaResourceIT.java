package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CollecPresta;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.CollecPrestaRepository;
import com.mycompany.myapp.service.criteria.CollecPrestaCriteria;
import com.mycompany.myapp.service.dto.CollecPrestaDTO;
import com.mycompany.myapp.service.mapper.CollecPrestaMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CollecPrestaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollecPrestaResourceIT {

    private static final String DEFAULT_INF_PREST = "AAAAAAAAAA";
    private static final String UPDATED_INF_PREST = "BBBBBBBBBB";

    private static final String DEFAULT_FECH_PREST = "AAAAAAAAAA";
    private static final String UPDATED_FECH_PREST = "BBBBBBBBBB";

    private static final String DEFAULT_FECH_DEV = "AAAAAAAAAA";
    private static final String UPDATED_FECH_DEV = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collec-prestas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollecPrestaRepository collecPrestaRepository;

    @Autowired
    private CollecPrestaMapper collecPrestaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollecPrestaMockMvc;

    private CollecPresta collecPresta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollecPresta createEntity(EntityManager em) {
        CollecPresta collecPresta = new CollecPresta().infPrest(DEFAULT_INF_PREST).fechPrest(DEFAULT_FECH_PREST).fechDev(DEFAULT_FECH_DEV);
        return collecPresta;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollecPresta createUpdatedEntity(EntityManager em) {
        CollecPresta collecPresta = new CollecPresta().infPrest(UPDATED_INF_PREST).fechPrest(UPDATED_FECH_PREST).fechDev(UPDATED_FECH_DEV);
        return collecPresta;
    }

    @BeforeEach
    public void initTest() {
        collecPresta = createEntity(em);
    }

    @Test
    @Transactional
    void createCollecPresta() throws Exception {
        int databaseSizeBeforeCreate = collecPrestaRepository.findAll().size();
        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);
        restCollecPrestaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeCreate + 1);
        CollecPresta testCollecPresta = collecPrestaList.get(collecPrestaList.size() - 1);
        assertThat(testCollecPresta.getInfPrest()).isEqualTo(DEFAULT_INF_PREST);
        assertThat(testCollecPresta.getFechPrest()).isEqualTo(DEFAULT_FECH_PREST);
        assertThat(testCollecPresta.getFechDev()).isEqualTo(DEFAULT_FECH_DEV);
    }

    @Test
    @Transactional
    void createCollecPrestaWithExistingId() throws Exception {
        // Create the CollecPresta with an existing ID
        collecPresta.setId(1L);
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        int databaseSizeBeforeCreate = collecPrestaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollecPrestaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInfPrestIsRequired() throws Exception {
        int databaseSizeBeforeTest = collecPrestaRepository.findAll().size();
        // set the field null
        collecPresta.setInfPrest(null);

        // Create the CollecPresta, which fails.
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        restCollecPrestaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechPrestIsRequired() throws Exception {
        int databaseSizeBeforeTest = collecPrestaRepository.findAll().size();
        // set the field null
        collecPresta.setFechPrest(null);

        // Create the CollecPresta, which fails.
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        restCollecPrestaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechDevIsRequired() throws Exception {
        int databaseSizeBeforeTest = collecPrestaRepository.findAll().size();
        // set the field null
        collecPresta.setFechDev(null);

        // Create the CollecPresta, which fails.
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        restCollecPrestaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCollecPrestas() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList
        restCollecPrestaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collecPresta.getId().intValue())))
            .andExpect(jsonPath("$.[*].infPrest").value(hasItem(DEFAULT_INF_PREST)))
            .andExpect(jsonPath("$.[*].fechPrest").value(hasItem(DEFAULT_FECH_PREST)))
            .andExpect(jsonPath("$.[*].fechDev").value(hasItem(DEFAULT_FECH_DEV)));
    }

    @Test
    @Transactional
    void getCollecPresta() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get the collecPresta
        restCollecPrestaMockMvc
            .perform(get(ENTITY_API_URL_ID, collecPresta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collecPresta.getId().intValue()))
            .andExpect(jsonPath("$.infPrest").value(DEFAULT_INF_PREST))
            .andExpect(jsonPath("$.fechPrest").value(DEFAULT_FECH_PREST))
            .andExpect(jsonPath("$.fechDev").value(DEFAULT_FECH_DEV));
    }

    @Test
    @Transactional
    void getCollecPrestasByIdFiltering() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        Long id = collecPresta.getId();

        defaultCollecPrestaShouldBeFound("id.equals=" + id);
        defaultCollecPrestaShouldNotBeFound("id.notEquals=" + id);

        defaultCollecPrestaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCollecPrestaShouldNotBeFound("id.greaterThan=" + id);

        defaultCollecPrestaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCollecPrestaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByInfPrestIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where infPrest equals to DEFAULT_INF_PREST
        defaultCollecPrestaShouldBeFound("infPrest.equals=" + DEFAULT_INF_PREST);

        // Get all the collecPrestaList where infPrest equals to UPDATED_INF_PREST
        defaultCollecPrestaShouldNotBeFound("infPrest.equals=" + UPDATED_INF_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByInfPrestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where infPrest not equals to DEFAULT_INF_PREST
        defaultCollecPrestaShouldNotBeFound("infPrest.notEquals=" + DEFAULT_INF_PREST);

        // Get all the collecPrestaList where infPrest not equals to UPDATED_INF_PREST
        defaultCollecPrestaShouldBeFound("infPrest.notEquals=" + UPDATED_INF_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByInfPrestIsInShouldWork() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where infPrest in DEFAULT_INF_PREST or UPDATED_INF_PREST
        defaultCollecPrestaShouldBeFound("infPrest.in=" + DEFAULT_INF_PREST + "," + UPDATED_INF_PREST);

        // Get all the collecPrestaList where infPrest equals to UPDATED_INF_PREST
        defaultCollecPrestaShouldNotBeFound("infPrest.in=" + UPDATED_INF_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByInfPrestIsNullOrNotNull() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where infPrest is not null
        defaultCollecPrestaShouldBeFound("infPrest.specified=true");

        // Get all the collecPrestaList where infPrest is null
        defaultCollecPrestaShouldNotBeFound("infPrest.specified=false");
    }

    @Test
    @Transactional
    void getAllCollecPrestasByInfPrestContainsSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where infPrest contains DEFAULT_INF_PREST
        defaultCollecPrestaShouldBeFound("infPrest.contains=" + DEFAULT_INF_PREST);

        // Get all the collecPrestaList where infPrest contains UPDATED_INF_PREST
        defaultCollecPrestaShouldNotBeFound("infPrest.contains=" + UPDATED_INF_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByInfPrestNotContainsSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where infPrest does not contain DEFAULT_INF_PREST
        defaultCollecPrestaShouldNotBeFound("infPrest.doesNotContain=" + DEFAULT_INF_PREST);

        // Get all the collecPrestaList where infPrest does not contain UPDATED_INF_PREST
        defaultCollecPrestaShouldBeFound("infPrest.doesNotContain=" + UPDATED_INF_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechPrestIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechPrest equals to DEFAULT_FECH_PREST
        defaultCollecPrestaShouldBeFound("fechPrest.equals=" + DEFAULT_FECH_PREST);

        // Get all the collecPrestaList where fechPrest equals to UPDATED_FECH_PREST
        defaultCollecPrestaShouldNotBeFound("fechPrest.equals=" + UPDATED_FECH_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechPrestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechPrest not equals to DEFAULT_FECH_PREST
        defaultCollecPrestaShouldNotBeFound("fechPrest.notEquals=" + DEFAULT_FECH_PREST);

        // Get all the collecPrestaList where fechPrest not equals to UPDATED_FECH_PREST
        defaultCollecPrestaShouldBeFound("fechPrest.notEquals=" + UPDATED_FECH_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechPrestIsInShouldWork() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechPrest in DEFAULT_FECH_PREST or UPDATED_FECH_PREST
        defaultCollecPrestaShouldBeFound("fechPrest.in=" + DEFAULT_FECH_PREST + "," + UPDATED_FECH_PREST);

        // Get all the collecPrestaList where fechPrest equals to UPDATED_FECH_PREST
        defaultCollecPrestaShouldNotBeFound("fechPrest.in=" + UPDATED_FECH_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechPrestIsNullOrNotNull() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechPrest is not null
        defaultCollecPrestaShouldBeFound("fechPrest.specified=true");

        // Get all the collecPrestaList where fechPrest is null
        defaultCollecPrestaShouldNotBeFound("fechPrest.specified=false");
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechPrestContainsSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechPrest contains DEFAULT_FECH_PREST
        defaultCollecPrestaShouldBeFound("fechPrest.contains=" + DEFAULT_FECH_PREST);

        // Get all the collecPrestaList where fechPrest contains UPDATED_FECH_PREST
        defaultCollecPrestaShouldNotBeFound("fechPrest.contains=" + UPDATED_FECH_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechPrestNotContainsSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechPrest does not contain DEFAULT_FECH_PREST
        defaultCollecPrestaShouldNotBeFound("fechPrest.doesNotContain=" + DEFAULT_FECH_PREST);

        // Get all the collecPrestaList where fechPrest does not contain UPDATED_FECH_PREST
        defaultCollecPrestaShouldBeFound("fechPrest.doesNotContain=" + UPDATED_FECH_PREST);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechDevIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechDev equals to DEFAULT_FECH_DEV
        defaultCollecPrestaShouldBeFound("fechDev.equals=" + DEFAULT_FECH_DEV);

        // Get all the collecPrestaList where fechDev equals to UPDATED_FECH_DEV
        defaultCollecPrestaShouldNotBeFound("fechDev.equals=" + UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechDevIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechDev not equals to DEFAULT_FECH_DEV
        defaultCollecPrestaShouldNotBeFound("fechDev.notEquals=" + DEFAULT_FECH_DEV);

        // Get all the collecPrestaList where fechDev not equals to UPDATED_FECH_DEV
        defaultCollecPrestaShouldBeFound("fechDev.notEquals=" + UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechDevIsInShouldWork() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechDev in DEFAULT_FECH_DEV or UPDATED_FECH_DEV
        defaultCollecPrestaShouldBeFound("fechDev.in=" + DEFAULT_FECH_DEV + "," + UPDATED_FECH_DEV);

        // Get all the collecPrestaList where fechDev equals to UPDATED_FECH_DEV
        defaultCollecPrestaShouldNotBeFound("fechDev.in=" + UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechDevIsNullOrNotNull() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechDev is not null
        defaultCollecPrestaShouldBeFound("fechDev.specified=true");

        // Get all the collecPrestaList where fechDev is null
        defaultCollecPrestaShouldNotBeFound("fechDev.specified=false");
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechDevContainsSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechDev contains DEFAULT_FECH_DEV
        defaultCollecPrestaShouldBeFound("fechDev.contains=" + DEFAULT_FECH_DEV);

        // Get all the collecPrestaList where fechDev contains UPDATED_FECH_DEV
        defaultCollecPrestaShouldNotBeFound("fechDev.contains=" + UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByFechDevNotContainsSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        // Get all the collecPrestaList where fechDev does not contain DEFAULT_FECH_DEV
        defaultCollecPrestaShouldNotBeFound("fechDev.doesNotContain=" + DEFAULT_FECH_DEV);

        // Get all the collecPrestaList where fechDev does not contain UPDATED_FECH_DEV
        defaultCollecPrestaShouldBeFound("fechDev.doesNotContain=" + UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void getAllCollecPrestasByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        collecPresta.setObjArte(objArte);
        collecPrestaRepository.saveAndFlush(collecPresta);
        Long objArteId = objArte.getId();

        // Get all the collecPrestaList where objArte equals to objArteId
        defaultCollecPrestaShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the collecPrestaList where objArte equals to (objArteId + 1)
        defaultCollecPrestaShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCollecPrestaShouldBeFound(String filter) throws Exception {
        restCollecPrestaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collecPresta.getId().intValue())))
            .andExpect(jsonPath("$.[*].infPrest").value(hasItem(DEFAULT_INF_PREST)))
            .andExpect(jsonPath("$.[*].fechPrest").value(hasItem(DEFAULT_FECH_PREST)))
            .andExpect(jsonPath("$.[*].fechDev").value(hasItem(DEFAULT_FECH_DEV)));

        // Check, that the count call also returns 1
        restCollecPrestaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCollecPrestaShouldNotBeFound(String filter) throws Exception {
        restCollecPrestaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCollecPrestaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCollecPresta() throws Exception {
        // Get the collecPresta
        restCollecPrestaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollecPresta() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();

        // Update the collecPresta
        CollecPresta updatedCollecPresta = collecPrestaRepository.findById(collecPresta.getId()).get();
        // Disconnect from session so that the updates on updatedCollecPresta are not directly saved in db
        em.detach(updatedCollecPresta);
        updatedCollecPresta.infPrest(UPDATED_INF_PREST).fechPrest(UPDATED_FECH_PREST).fechDev(UPDATED_FECH_DEV);
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(updatedCollecPresta);

        restCollecPrestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collecPrestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
        CollecPresta testCollecPresta = collecPrestaList.get(collecPrestaList.size() - 1);
        assertThat(testCollecPresta.getInfPrest()).isEqualTo(UPDATED_INF_PREST);
        assertThat(testCollecPresta.getFechPrest()).isEqualTo(UPDATED_FECH_PREST);
        assertThat(testCollecPresta.getFechDev()).isEqualTo(UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void putNonExistingCollecPresta() throws Exception {
        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();
        collecPresta.setId(count.incrementAndGet());

        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollecPrestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collecPrestaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollecPresta() throws Exception {
        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();
        collecPresta.setId(count.incrementAndGet());

        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPrestaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollecPresta() throws Exception {
        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();
        collecPresta.setId(count.incrementAndGet());

        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPrestaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollecPrestaWithPatch() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();

        // Update the collecPresta using partial update
        CollecPresta partialUpdatedCollecPresta = new CollecPresta();
        partialUpdatedCollecPresta.setId(collecPresta.getId());

        partialUpdatedCollecPresta.fechPrest(UPDATED_FECH_PREST);

        restCollecPrestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollecPresta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollecPresta))
            )
            .andExpect(status().isOk());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
        CollecPresta testCollecPresta = collecPrestaList.get(collecPrestaList.size() - 1);
        assertThat(testCollecPresta.getInfPrest()).isEqualTo(DEFAULT_INF_PREST);
        assertThat(testCollecPresta.getFechPrest()).isEqualTo(UPDATED_FECH_PREST);
        assertThat(testCollecPresta.getFechDev()).isEqualTo(DEFAULT_FECH_DEV);
    }

    @Test
    @Transactional
    void fullUpdateCollecPrestaWithPatch() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();

        // Update the collecPresta using partial update
        CollecPresta partialUpdatedCollecPresta = new CollecPresta();
        partialUpdatedCollecPresta.setId(collecPresta.getId());

        partialUpdatedCollecPresta.infPrest(UPDATED_INF_PREST).fechPrest(UPDATED_FECH_PREST).fechDev(UPDATED_FECH_DEV);

        restCollecPrestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollecPresta.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollecPresta))
            )
            .andExpect(status().isOk());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
        CollecPresta testCollecPresta = collecPrestaList.get(collecPrestaList.size() - 1);
        assertThat(testCollecPresta.getInfPrest()).isEqualTo(UPDATED_INF_PREST);
        assertThat(testCollecPresta.getFechPrest()).isEqualTo(UPDATED_FECH_PREST);
        assertThat(testCollecPresta.getFechDev()).isEqualTo(UPDATED_FECH_DEV);
    }

    @Test
    @Transactional
    void patchNonExistingCollecPresta() throws Exception {
        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();
        collecPresta.setId(count.incrementAndGet());

        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollecPrestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collecPrestaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollecPresta() throws Exception {
        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();
        collecPresta.setId(count.incrementAndGet());

        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPrestaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollecPresta() throws Exception {
        int databaseSizeBeforeUpdate = collecPrestaRepository.findAll().size();
        collecPresta.setId(count.incrementAndGet());

        // Create the CollecPresta
        CollecPrestaDTO collecPrestaDTO = collecPrestaMapper.toDto(collecPresta);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPrestaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collecPrestaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollecPresta in the database
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollecPresta() throws Exception {
        // Initialize the database
        collecPrestaRepository.saveAndFlush(collecPresta);

        int databaseSizeBeforeDelete = collecPrestaRepository.findAll().size();

        // Delete the collecPresta
        restCollecPrestaMockMvc
            .perform(delete(ENTITY_API_URL_ID, collecPresta.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollecPresta> collecPrestaList = collecPrestaRepository.findAll();
        assertThat(collecPrestaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
