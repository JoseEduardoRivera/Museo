package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CollecPerma;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.CollecPermaRepository;
import com.mycompany.myapp.service.criteria.CollecPermaCriteria;
import com.mycompany.myapp.service.dto.CollecPermaDTO;
import com.mycompany.myapp.service.mapper.CollecPermaMapper;
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
 * Integration tests for the {@link CollecPermaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CollecPermaResourceIT {

    private static final String DEFAULT_EXHIBI_ALMACEN = "AAAAAAAAAA";
    private static final String UPDATED_EXHIBI_ALMACEN = "BBBBBBBBBB";

    private static final String DEFAULT_COSTO = "AAAAAAAAAA";
    private static final String UPDATED_COSTO = "BBBBBBBBBB";

    private static final String DEFAULT_FECHA = "AAAAAAAAAA";
    private static final String UPDATED_FECHA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/collec-permas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CollecPermaRepository collecPermaRepository;

    @Autowired
    private CollecPermaMapper collecPermaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCollecPermaMockMvc;

    private CollecPerma collecPerma;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollecPerma createEntity(EntityManager em) {
        CollecPerma collecPerma = new CollecPerma().exhibiAlmacen(DEFAULT_EXHIBI_ALMACEN).costo(DEFAULT_COSTO).fecha(DEFAULT_FECHA);
        return collecPerma;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CollecPerma createUpdatedEntity(EntityManager em) {
        CollecPerma collecPerma = new CollecPerma().exhibiAlmacen(UPDATED_EXHIBI_ALMACEN).costo(UPDATED_COSTO).fecha(UPDATED_FECHA);
        return collecPerma;
    }

    @BeforeEach
    public void initTest() {
        collecPerma = createEntity(em);
    }

    @Test
    @Transactional
    void createCollecPerma() throws Exception {
        int databaseSizeBeforeCreate = collecPermaRepository.findAll().size();
        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);
        restCollecPermaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeCreate + 1);
        CollecPerma testCollecPerma = collecPermaList.get(collecPermaList.size() - 1);
        assertThat(testCollecPerma.getExhibiAlmacen()).isEqualTo(DEFAULT_EXHIBI_ALMACEN);
        assertThat(testCollecPerma.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testCollecPerma.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void createCollecPermaWithExistingId() throws Exception {
        // Create the CollecPerma with an existing ID
        collecPerma.setId(1L);
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        int databaseSizeBeforeCreate = collecPermaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCollecPermaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkExhibiAlmacenIsRequired() throws Exception {
        int databaseSizeBeforeTest = collecPermaRepository.findAll().size();
        // set the field null
        collecPerma.setExhibiAlmacen(null);

        // Create the CollecPerma, which fails.
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        restCollecPermaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCostoIsRequired() throws Exception {
        int databaseSizeBeforeTest = collecPermaRepository.findAll().size();
        // set the field null
        collecPerma.setCosto(null);

        // Create the CollecPerma, which fails.
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        restCollecPermaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = collecPermaRepository.findAll().size();
        // set the field null
        collecPerma.setFecha(null);

        // Create the CollecPerma, which fails.
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        restCollecPermaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCollecPermas() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList
        restCollecPermaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collecPerma.getId().intValue())))
            .andExpect(jsonPath("$.[*].exhibiAlmacen").value(hasItem(DEFAULT_EXHIBI_ALMACEN)))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA)));
    }

    @Test
    @Transactional
    void getCollecPerma() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get the collecPerma
        restCollecPermaMockMvc
            .perform(get(ENTITY_API_URL_ID, collecPerma.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(collecPerma.getId().intValue()))
            .andExpect(jsonPath("$.exhibiAlmacen").value(DEFAULT_EXHIBI_ALMACEN))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA));
    }

    @Test
    @Transactional
    void getCollecPermasByIdFiltering() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        Long id = collecPerma.getId();

        defaultCollecPermaShouldBeFound("id.equals=" + id);
        defaultCollecPermaShouldNotBeFound("id.notEquals=" + id);

        defaultCollecPermaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCollecPermaShouldNotBeFound("id.greaterThan=" + id);

        defaultCollecPermaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCollecPermaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCollecPermasByExhibiAlmacenIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where exhibiAlmacen equals to DEFAULT_EXHIBI_ALMACEN
        defaultCollecPermaShouldBeFound("exhibiAlmacen.equals=" + DEFAULT_EXHIBI_ALMACEN);

        // Get all the collecPermaList where exhibiAlmacen equals to UPDATED_EXHIBI_ALMACEN
        defaultCollecPermaShouldNotBeFound("exhibiAlmacen.equals=" + UPDATED_EXHIBI_ALMACEN);
    }

    @Test
    @Transactional
    void getAllCollecPermasByExhibiAlmacenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where exhibiAlmacen not equals to DEFAULT_EXHIBI_ALMACEN
        defaultCollecPermaShouldNotBeFound("exhibiAlmacen.notEquals=" + DEFAULT_EXHIBI_ALMACEN);

        // Get all the collecPermaList where exhibiAlmacen not equals to UPDATED_EXHIBI_ALMACEN
        defaultCollecPermaShouldBeFound("exhibiAlmacen.notEquals=" + UPDATED_EXHIBI_ALMACEN);
    }

    @Test
    @Transactional
    void getAllCollecPermasByExhibiAlmacenIsInShouldWork() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where exhibiAlmacen in DEFAULT_EXHIBI_ALMACEN or UPDATED_EXHIBI_ALMACEN
        defaultCollecPermaShouldBeFound("exhibiAlmacen.in=" + DEFAULT_EXHIBI_ALMACEN + "," + UPDATED_EXHIBI_ALMACEN);

        // Get all the collecPermaList where exhibiAlmacen equals to UPDATED_EXHIBI_ALMACEN
        defaultCollecPermaShouldNotBeFound("exhibiAlmacen.in=" + UPDATED_EXHIBI_ALMACEN);
    }

    @Test
    @Transactional
    void getAllCollecPermasByExhibiAlmacenIsNullOrNotNull() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where exhibiAlmacen is not null
        defaultCollecPermaShouldBeFound("exhibiAlmacen.specified=true");

        // Get all the collecPermaList where exhibiAlmacen is null
        defaultCollecPermaShouldNotBeFound("exhibiAlmacen.specified=false");
    }

    @Test
    @Transactional
    void getAllCollecPermasByExhibiAlmacenContainsSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where exhibiAlmacen contains DEFAULT_EXHIBI_ALMACEN
        defaultCollecPermaShouldBeFound("exhibiAlmacen.contains=" + DEFAULT_EXHIBI_ALMACEN);

        // Get all the collecPermaList where exhibiAlmacen contains UPDATED_EXHIBI_ALMACEN
        defaultCollecPermaShouldNotBeFound("exhibiAlmacen.contains=" + UPDATED_EXHIBI_ALMACEN);
    }

    @Test
    @Transactional
    void getAllCollecPermasByExhibiAlmacenNotContainsSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where exhibiAlmacen does not contain DEFAULT_EXHIBI_ALMACEN
        defaultCollecPermaShouldNotBeFound("exhibiAlmacen.doesNotContain=" + DEFAULT_EXHIBI_ALMACEN);

        // Get all the collecPermaList where exhibiAlmacen does not contain UPDATED_EXHIBI_ALMACEN
        defaultCollecPermaShouldBeFound("exhibiAlmacen.doesNotContain=" + UPDATED_EXHIBI_ALMACEN);
    }

    @Test
    @Transactional
    void getAllCollecPermasByCostoIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where costo equals to DEFAULT_COSTO
        defaultCollecPermaShouldBeFound("costo.equals=" + DEFAULT_COSTO);

        // Get all the collecPermaList where costo equals to UPDATED_COSTO
        defaultCollecPermaShouldNotBeFound("costo.equals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    void getAllCollecPermasByCostoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where costo not equals to DEFAULT_COSTO
        defaultCollecPermaShouldNotBeFound("costo.notEquals=" + DEFAULT_COSTO);

        // Get all the collecPermaList where costo not equals to UPDATED_COSTO
        defaultCollecPermaShouldBeFound("costo.notEquals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    void getAllCollecPermasByCostoIsInShouldWork() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where costo in DEFAULT_COSTO or UPDATED_COSTO
        defaultCollecPermaShouldBeFound("costo.in=" + DEFAULT_COSTO + "," + UPDATED_COSTO);

        // Get all the collecPermaList where costo equals to UPDATED_COSTO
        defaultCollecPermaShouldNotBeFound("costo.in=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    void getAllCollecPermasByCostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where costo is not null
        defaultCollecPermaShouldBeFound("costo.specified=true");

        // Get all the collecPermaList where costo is null
        defaultCollecPermaShouldNotBeFound("costo.specified=false");
    }

    @Test
    @Transactional
    void getAllCollecPermasByCostoContainsSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where costo contains DEFAULT_COSTO
        defaultCollecPermaShouldBeFound("costo.contains=" + DEFAULT_COSTO);

        // Get all the collecPermaList where costo contains UPDATED_COSTO
        defaultCollecPermaShouldNotBeFound("costo.contains=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    void getAllCollecPermasByCostoNotContainsSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where costo does not contain DEFAULT_COSTO
        defaultCollecPermaShouldNotBeFound("costo.doesNotContain=" + DEFAULT_COSTO);

        // Get all the collecPermaList where costo does not contain UPDATED_COSTO
        defaultCollecPermaShouldBeFound("costo.doesNotContain=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    void getAllCollecPermasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where fecha equals to DEFAULT_FECHA
        defaultCollecPermaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the collecPermaList where fecha equals to UPDATED_FECHA
        defaultCollecPermaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCollecPermasByFechaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where fecha not equals to DEFAULT_FECHA
        defaultCollecPermaShouldNotBeFound("fecha.notEquals=" + DEFAULT_FECHA);

        // Get all the collecPermaList where fecha not equals to UPDATED_FECHA
        defaultCollecPermaShouldBeFound("fecha.notEquals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCollecPermasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCollecPermaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the collecPermaList where fecha equals to UPDATED_FECHA
        defaultCollecPermaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCollecPermasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where fecha is not null
        defaultCollecPermaShouldBeFound("fecha.specified=true");

        // Get all the collecPermaList where fecha is null
        defaultCollecPermaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    void getAllCollecPermasByFechaContainsSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where fecha contains DEFAULT_FECHA
        defaultCollecPermaShouldBeFound("fecha.contains=" + DEFAULT_FECHA);

        // Get all the collecPermaList where fecha contains UPDATED_FECHA
        defaultCollecPermaShouldNotBeFound("fecha.contains=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCollecPermasByFechaNotContainsSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        // Get all the collecPermaList where fecha does not contain DEFAULT_FECHA
        defaultCollecPermaShouldNotBeFound("fecha.doesNotContain=" + DEFAULT_FECHA);

        // Get all the collecPermaList where fecha does not contain UPDATED_FECHA
        defaultCollecPermaShouldBeFound("fecha.doesNotContain=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    void getAllCollecPermasByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        collecPerma.setObjArte(objArte);
        collecPermaRepository.saveAndFlush(collecPerma);
        Long objArteId = objArte.getId();

        // Get all the collecPermaList where objArte equals to objArteId
        defaultCollecPermaShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the collecPermaList where objArte equals to (objArteId + 1)
        defaultCollecPermaShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCollecPermaShouldBeFound(String filter) throws Exception {
        restCollecPermaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(collecPerma.getId().intValue())))
            .andExpect(jsonPath("$.[*].exhibiAlmacen").value(hasItem(DEFAULT_EXHIBI_ALMACEN)))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA)));

        // Check, that the count call also returns 1
        restCollecPermaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCollecPermaShouldNotBeFound(String filter) throws Exception {
        restCollecPermaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCollecPermaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCollecPerma() throws Exception {
        // Get the collecPerma
        restCollecPermaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCollecPerma() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();

        // Update the collecPerma
        CollecPerma updatedCollecPerma = collecPermaRepository.findById(collecPerma.getId()).get();
        // Disconnect from session so that the updates on updatedCollecPerma are not directly saved in db
        em.detach(updatedCollecPerma);
        updatedCollecPerma.exhibiAlmacen(UPDATED_EXHIBI_ALMACEN).costo(UPDATED_COSTO).fecha(UPDATED_FECHA);
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(updatedCollecPerma);

        restCollecPermaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collecPermaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
        CollecPerma testCollecPerma = collecPermaList.get(collecPermaList.size() - 1);
        assertThat(testCollecPerma.getExhibiAlmacen()).isEqualTo(UPDATED_EXHIBI_ALMACEN);
        assertThat(testCollecPerma.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testCollecPerma.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void putNonExistingCollecPerma() throws Exception {
        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();
        collecPerma.setId(count.incrementAndGet());

        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollecPermaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, collecPermaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCollecPerma() throws Exception {
        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();
        collecPerma.setId(count.incrementAndGet());

        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPermaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCollecPerma() throws Exception {
        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();
        collecPerma.setId(count.incrementAndGet());

        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPermaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(collecPermaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCollecPermaWithPatch() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();

        // Update the collecPerma using partial update
        CollecPerma partialUpdatedCollecPerma = new CollecPerma();
        partialUpdatedCollecPerma.setId(collecPerma.getId());

        restCollecPermaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollecPerma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollecPerma))
            )
            .andExpect(status().isOk());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
        CollecPerma testCollecPerma = collecPermaList.get(collecPermaList.size() - 1);
        assertThat(testCollecPerma.getExhibiAlmacen()).isEqualTo(DEFAULT_EXHIBI_ALMACEN);
        assertThat(testCollecPerma.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testCollecPerma.getFecha()).isEqualTo(DEFAULT_FECHA);
    }

    @Test
    @Transactional
    void fullUpdateCollecPermaWithPatch() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();

        // Update the collecPerma using partial update
        CollecPerma partialUpdatedCollecPerma = new CollecPerma();
        partialUpdatedCollecPerma.setId(collecPerma.getId());

        partialUpdatedCollecPerma.exhibiAlmacen(UPDATED_EXHIBI_ALMACEN).costo(UPDATED_COSTO).fecha(UPDATED_FECHA);

        restCollecPermaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCollecPerma.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCollecPerma))
            )
            .andExpect(status().isOk());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
        CollecPerma testCollecPerma = collecPermaList.get(collecPermaList.size() - 1);
        assertThat(testCollecPerma.getExhibiAlmacen()).isEqualTo(UPDATED_EXHIBI_ALMACEN);
        assertThat(testCollecPerma.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testCollecPerma.getFecha()).isEqualTo(UPDATED_FECHA);
    }

    @Test
    @Transactional
    void patchNonExistingCollecPerma() throws Exception {
        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();
        collecPerma.setId(count.incrementAndGet());

        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCollecPermaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, collecPermaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCollecPerma() throws Exception {
        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();
        collecPerma.setId(count.incrementAndGet());

        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPermaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCollecPerma() throws Exception {
        int databaseSizeBeforeUpdate = collecPermaRepository.findAll().size();
        collecPerma.setId(count.incrementAndGet());

        // Create the CollecPerma
        CollecPermaDTO collecPermaDTO = collecPermaMapper.toDto(collecPerma);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCollecPermaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(collecPermaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CollecPerma in the database
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCollecPerma() throws Exception {
        // Initialize the database
        collecPermaRepository.saveAndFlush(collecPerma);

        int databaseSizeBeforeDelete = collecPermaRepository.findAll().size();

        // Delete the collecPerma
        restCollecPermaMockMvc
            .perform(delete(ENTITY_API_URL_ID, collecPerma.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CollecPerma> collecPermaList = collecPermaRepository.findAll();
        assertThat(collecPermaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
