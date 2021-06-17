package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Escultura;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.EsculturaRepository;
import com.mycompany.myapp.service.criteria.EsculturaCriteria;
import com.mycompany.myapp.service.dto.EsculturaDTO;
import com.mycompany.myapp.service.mapper.EsculturaMapper;
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
 * Integration tests for the {@link EsculturaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EsculturaResourceIT {

    private static final String DEFAULT_ALTURA = "AAAAAAAAAA";
    private static final String UPDATED_ALTURA = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL = "BBBBBBBBBB";

    private static final String DEFAULT_ESTILO = "AAAAAAAAAA";
    private static final String UPDATED_ESTILO = "BBBBBBBBBB";

    private static final String DEFAULT_PESO = "AAAAAAAAAA";
    private static final String UPDATED_PESO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/esculturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EsculturaRepository esculturaRepository;

    @Autowired
    private EsculturaMapper esculturaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEsculturaMockMvc;

    private Escultura escultura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Escultura createEntity(EntityManager em) {
        Escultura escultura = new Escultura().altura(DEFAULT_ALTURA).material(DEFAULT_MATERIAL).estilo(DEFAULT_ESTILO).peso(DEFAULT_PESO);
        return escultura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Escultura createUpdatedEntity(EntityManager em) {
        Escultura escultura = new Escultura().altura(UPDATED_ALTURA).material(UPDATED_MATERIAL).estilo(UPDATED_ESTILO).peso(UPDATED_PESO);
        return escultura;
    }

    @BeforeEach
    public void initTest() {
        escultura = createEntity(em);
    }

    @Test
    @Transactional
    void createEscultura() throws Exception {
        int databaseSizeBeforeCreate = esculturaRepository.findAll().size();
        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);
        restEsculturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isCreated());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeCreate + 1);
        Escultura testEscultura = esculturaList.get(esculturaList.size() - 1);
        assertThat(testEscultura.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testEscultura.getMaterial()).isEqualTo(DEFAULT_MATERIAL);
        assertThat(testEscultura.getEstilo()).isEqualTo(DEFAULT_ESTILO);
        assertThat(testEscultura.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void createEsculturaWithExistingId() throws Exception {
        // Create the Escultura with an existing ID
        escultura.setId(1L);
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        int databaseSizeBeforeCreate = esculturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsculturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAlturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = esculturaRepository.findAll().size();
        // set the field null
        escultura.setAltura(null);

        // Create the Escultura, which fails.
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        restEsculturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isBadRequest());

        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaterialIsRequired() throws Exception {
        int databaseSizeBeforeTest = esculturaRepository.findAll().size();
        // set the field null
        escultura.setMaterial(null);

        // Create the Escultura, which fails.
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        restEsculturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isBadRequest());

        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstiloIsRequired() throws Exception {
        int databaseSizeBeforeTest = esculturaRepository.findAll().size();
        // set the field null
        escultura.setEstilo(null);

        // Create the Escultura, which fails.
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        restEsculturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isBadRequest());

        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = esculturaRepository.findAll().size();
        // set the field null
        escultura.setPeso(null);

        // Create the Escultura, which fails.
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        restEsculturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isBadRequest());

        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEsculturas() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList
        restEsculturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escultura.getId().intValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].estilo").value(hasItem(DEFAULT_ESTILO)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)));
    }

    @Test
    @Transactional
    void getEscultura() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get the escultura
        restEsculturaMockMvc
            .perform(get(ENTITY_API_URL_ID, escultura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(escultura.getId().intValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA))
            .andExpect(jsonPath("$.material").value(DEFAULT_MATERIAL))
            .andExpect(jsonPath("$.estilo").value(DEFAULT_ESTILO))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO));
    }

    @Test
    @Transactional
    void getEsculturasByIdFiltering() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        Long id = escultura.getId();

        defaultEsculturaShouldBeFound("id.equals=" + id);
        defaultEsculturaShouldNotBeFound("id.notEquals=" + id);

        defaultEsculturaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEsculturaShouldNotBeFound("id.greaterThan=" + id);

        defaultEsculturaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEsculturaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEsculturasByAlturaIsEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where altura equals to DEFAULT_ALTURA
        defaultEsculturaShouldBeFound("altura.equals=" + DEFAULT_ALTURA);

        // Get all the esculturaList where altura equals to UPDATED_ALTURA
        defaultEsculturaShouldNotBeFound("altura.equals=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllEsculturasByAlturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where altura not equals to DEFAULT_ALTURA
        defaultEsculturaShouldNotBeFound("altura.notEquals=" + DEFAULT_ALTURA);

        // Get all the esculturaList where altura not equals to UPDATED_ALTURA
        defaultEsculturaShouldBeFound("altura.notEquals=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllEsculturasByAlturaIsInShouldWork() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where altura in DEFAULT_ALTURA or UPDATED_ALTURA
        defaultEsculturaShouldBeFound("altura.in=" + DEFAULT_ALTURA + "," + UPDATED_ALTURA);

        // Get all the esculturaList where altura equals to UPDATED_ALTURA
        defaultEsculturaShouldNotBeFound("altura.in=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllEsculturasByAlturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where altura is not null
        defaultEsculturaShouldBeFound("altura.specified=true");

        // Get all the esculturaList where altura is null
        defaultEsculturaShouldNotBeFound("altura.specified=false");
    }

    @Test
    @Transactional
    void getAllEsculturasByAlturaContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where altura contains DEFAULT_ALTURA
        defaultEsculturaShouldBeFound("altura.contains=" + DEFAULT_ALTURA);

        // Get all the esculturaList where altura contains UPDATED_ALTURA
        defaultEsculturaShouldNotBeFound("altura.contains=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllEsculturasByAlturaNotContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where altura does not contain DEFAULT_ALTURA
        defaultEsculturaShouldNotBeFound("altura.doesNotContain=" + DEFAULT_ALTURA);

        // Get all the esculturaList where altura does not contain UPDATED_ALTURA
        defaultEsculturaShouldBeFound("altura.doesNotContain=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void getAllEsculturasByMaterialIsEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where material equals to DEFAULT_MATERIAL
        defaultEsculturaShouldBeFound("material.equals=" + DEFAULT_MATERIAL);

        // Get all the esculturaList where material equals to UPDATED_MATERIAL
        defaultEsculturaShouldNotBeFound("material.equals=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllEsculturasByMaterialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where material not equals to DEFAULT_MATERIAL
        defaultEsculturaShouldNotBeFound("material.notEquals=" + DEFAULT_MATERIAL);

        // Get all the esculturaList where material not equals to UPDATED_MATERIAL
        defaultEsculturaShouldBeFound("material.notEquals=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllEsculturasByMaterialIsInShouldWork() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where material in DEFAULT_MATERIAL or UPDATED_MATERIAL
        defaultEsculturaShouldBeFound("material.in=" + DEFAULT_MATERIAL + "," + UPDATED_MATERIAL);

        // Get all the esculturaList where material equals to UPDATED_MATERIAL
        defaultEsculturaShouldNotBeFound("material.in=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllEsculturasByMaterialIsNullOrNotNull() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where material is not null
        defaultEsculturaShouldBeFound("material.specified=true");

        // Get all the esculturaList where material is null
        defaultEsculturaShouldNotBeFound("material.specified=false");
    }

    @Test
    @Transactional
    void getAllEsculturasByMaterialContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where material contains DEFAULT_MATERIAL
        defaultEsculturaShouldBeFound("material.contains=" + DEFAULT_MATERIAL);

        // Get all the esculturaList where material contains UPDATED_MATERIAL
        defaultEsculturaShouldNotBeFound("material.contains=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllEsculturasByMaterialNotContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where material does not contain DEFAULT_MATERIAL
        defaultEsculturaShouldNotBeFound("material.doesNotContain=" + DEFAULT_MATERIAL);

        // Get all the esculturaList where material does not contain UPDATED_MATERIAL
        defaultEsculturaShouldBeFound("material.doesNotContain=" + UPDATED_MATERIAL);
    }

    @Test
    @Transactional
    void getAllEsculturasByEstiloIsEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where estilo equals to DEFAULT_ESTILO
        defaultEsculturaShouldBeFound("estilo.equals=" + DEFAULT_ESTILO);

        // Get all the esculturaList where estilo equals to UPDATED_ESTILO
        defaultEsculturaShouldNotBeFound("estilo.equals=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllEsculturasByEstiloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where estilo not equals to DEFAULT_ESTILO
        defaultEsculturaShouldNotBeFound("estilo.notEquals=" + DEFAULT_ESTILO);

        // Get all the esculturaList where estilo not equals to UPDATED_ESTILO
        defaultEsculturaShouldBeFound("estilo.notEquals=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllEsculturasByEstiloIsInShouldWork() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where estilo in DEFAULT_ESTILO or UPDATED_ESTILO
        defaultEsculturaShouldBeFound("estilo.in=" + DEFAULT_ESTILO + "," + UPDATED_ESTILO);

        // Get all the esculturaList where estilo equals to UPDATED_ESTILO
        defaultEsculturaShouldNotBeFound("estilo.in=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllEsculturasByEstiloIsNullOrNotNull() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where estilo is not null
        defaultEsculturaShouldBeFound("estilo.specified=true");

        // Get all the esculturaList where estilo is null
        defaultEsculturaShouldNotBeFound("estilo.specified=false");
    }

    @Test
    @Transactional
    void getAllEsculturasByEstiloContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where estilo contains DEFAULT_ESTILO
        defaultEsculturaShouldBeFound("estilo.contains=" + DEFAULT_ESTILO);

        // Get all the esculturaList where estilo contains UPDATED_ESTILO
        defaultEsculturaShouldNotBeFound("estilo.contains=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllEsculturasByEstiloNotContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where estilo does not contain DEFAULT_ESTILO
        defaultEsculturaShouldNotBeFound("estilo.doesNotContain=" + DEFAULT_ESTILO);

        // Get all the esculturaList where estilo does not contain UPDATED_ESTILO
        defaultEsculturaShouldBeFound("estilo.doesNotContain=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllEsculturasByPesoIsEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where peso equals to DEFAULT_PESO
        defaultEsculturaShouldBeFound("peso.equals=" + DEFAULT_PESO);

        // Get all the esculturaList where peso equals to UPDATED_PESO
        defaultEsculturaShouldNotBeFound("peso.equals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllEsculturasByPesoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where peso not equals to DEFAULT_PESO
        defaultEsculturaShouldNotBeFound("peso.notEquals=" + DEFAULT_PESO);

        // Get all the esculturaList where peso not equals to UPDATED_PESO
        defaultEsculturaShouldBeFound("peso.notEquals=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllEsculturasByPesoIsInShouldWork() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where peso in DEFAULT_PESO or UPDATED_PESO
        defaultEsculturaShouldBeFound("peso.in=" + DEFAULT_PESO + "," + UPDATED_PESO);

        // Get all the esculturaList where peso equals to UPDATED_PESO
        defaultEsculturaShouldNotBeFound("peso.in=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllEsculturasByPesoIsNullOrNotNull() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where peso is not null
        defaultEsculturaShouldBeFound("peso.specified=true");

        // Get all the esculturaList where peso is null
        defaultEsculturaShouldNotBeFound("peso.specified=false");
    }

    @Test
    @Transactional
    void getAllEsculturasByPesoContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where peso contains DEFAULT_PESO
        defaultEsculturaShouldBeFound("peso.contains=" + DEFAULT_PESO);

        // Get all the esculturaList where peso contains UPDATED_PESO
        defaultEsculturaShouldNotBeFound("peso.contains=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllEsculturasByPesoNotContainsSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        // Get all the esculturaList where peso does not contain DEFAULT_PESO
        defaultEsculturaShouldNotBeFound("peso.doesNotContain=" + DEFAULT_PESO);

        // Get all the esculturaList where peso does not contain UPDATED_PESO
        defaultEsculturaShouldBeFound("peso.doesNotContain=" + UPDATED_PESO);
    }

    @Test
    @Transactional
    void getAllEsculturasByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        escultura.setObjArte(objArte);
        esculturaRepository.saveAndFlush(escultura);
        Long objArteId = objArte.getId();

        // Get all the esculturaList where objArte equals to objArteId
        defaultEsculturaShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the esculturaList where objArte equals to (objArteId + 1)
        defaultEsculturaShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEsculturaShouldBeFound(String filter) throws Exception {
        restEsculturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(escultura.getId().intValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA)))
            .andExpect(jsonPath("$.[*].material").value(hasItem(DEFAULT_MATERIAL)))
            .andExpect(jsonPath("$.[*].estilo").value(hasItem(DEFAULT_ESTILO)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO)));

        // Check, that the count call also returns 1
        restEsculturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEsculturaShouldNotBeFound(String filter) throws Exception {
        restEsculturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEsculturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEscultura() throws Exception {
        // Get the escultura
        restEsculturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEscultura() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();

        // Update the escultura
        Escultura updatedEscultura = esculturaRepository.findById(escultura.getId()).get();
        // Disconnect from session so that the updates on updatedEscultura are not directly saved in db
        em.detach(updatedEscultura);
        updatedEscultura.altura(UPDATED_ALTURA).material(UPDATED_MATERIAL).estilo(UPDATED_ESTILO).peso(UPDATED_PESO);
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(updatedEscultura);

        restEsculturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, esculturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(esculturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
        Escultura testEscultura = esculturaList.get(esculturaList.size() - 1);
        assertThat(testEscultura.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testEscultura.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testEscultura.getEstilo()).isEqualTo(UPDATED_ESTILO);
        assertThat(testEscultura.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void putNonExistingEscultura() throws Exception {
        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();
        escultura.setId(count.incrementAndGet());

        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsculturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, esculturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(esculturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEscultura() throws Exception {
        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();
        escultura.setId(count.incrementAndGet());

        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsculturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(esculturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEscultura() throws Exception {
        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();
        escultura.setId(count.incrementAndGet());

        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsculturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(esculturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEsculturaWithPatch() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();

        // Update the escultura using partial update
        Escultura partialUpdatedEscultura = new Escultura();
        partialUpdatedEscultura.setId(escultura.getId());

        partialUpdatedEscultura.material(UPDATED_MATERIAL);

        restEsculturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscultura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEscultura))
            )
            .andExpect(status().isOk());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
        Escultura testEscultura = esculturaList.get(esculturaList.size() - 1);
        assertThat(testEscultura.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testEscultura.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testEscultura.getEstilo()).isEqualTo(DEFAULT_ESTILO);
        assertThat(testEscultura.getPeso()).isEqualTo(DEFAULT_PESO);
    }

    @Test
    @Transactional
    void fullUpdateEsculturaWithPatch() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();

        // Update the escultura using partial update
        Escultura partialUpdatedEscultura = new Escultura();
        partialUpdatedEscultura.setId(escultura.getId());

        partialUpdatedEscultura.altura(UPDATED_ALTURA).material(UPDATED_MATERIAL).estilo(UPDATED_ESTILO).peso(UPDATED_PESO);

        restEsculturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEscultura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEscultura))
            )
            .andExpect(status().isOk());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
        Escultura testEscultura = esculturaList.get(esculturaList.size() - 1);
        assertThat(testEscultura.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testEscultura.getMaterial()).isEqualTo(UPDATED_MATERIAL);
        assertThat(testEscultura.getEstilo()).isEqualTo(UPDATED_ESTILO);
        assertThat(testEscultura.getPeso()).isEqualTo(UPDATED_PESO);
    }

    @Test
    @Transactional
    void patchNonExistingEscultura() throws Exception {
        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();
        escultura.setId(count.incrementAndGet());

        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsculturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, esculturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(esculturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEscultura() throws Exception {
        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();
        escultura.setId(count.incrementAndGet());

        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsculturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(esculturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEscultura() throws Exception {
        int databaseSizeBeforeUpdate = esculturaRepository.findAll().size();
        escultura.setId(count.incrementAndGet());

        // Create the Escultura
        EsculturaDTO esculturaDTO = esculturaMapper.toDto(escultura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEsculturaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(esculturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Escultura in the database
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEscultura() throws Exception {
        // Initialize the database
        esculturaRepository.saveAndFlush(escultura);

        int databaseSizeBeforeDelete = esculturaRepository.findAll().size();

        // Delete the escultura
        restEsculturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, escultura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Escultura> esculturaList = esculturaRepository.findAll();
        assertThat(esculturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
