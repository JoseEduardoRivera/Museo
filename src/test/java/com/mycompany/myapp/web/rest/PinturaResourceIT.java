package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.domain.Pintura;
import com.mycompany.myapp.repository.PinturaRepository;
import com.mycompany.myapp.service.criteria.PinturaCriteria;
import com.mycompany.myapp.service.dto.PinturaDTO;
import com.mycompany.myapp.service.mapper.PinturaMapper;
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
 * Integration tests for the {@link PinturaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PinturaResourceIT {

    private static final String DEFAULT_TIPO_PINTURA = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PINTURA = "BBBBBBBBBB";

    private static final String DEFAULT_MATERIAL_PINTURA = "AAAAAAAAAA";
    private static final String UPDATED_MATERIAL_PINTURA = "BBBBBBBBBB";

    private static final String DEFAULT_ESTILO_PINT = "AAAAAAAAAA";
    private static final String UPDATED_ESTILO_PINT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/pinturas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PinturaRepository pinturaRepository;

    @Autowired
    private PinturaMapper pinturaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPinturaMockMvc;

    private Pintura pintura;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pintura createEntity(EntityManager em) {
        Pintura pintura = new Pintura()
            .tipoPintura(DEFAULT_TIPO_PINTURA)
            .materialPintura(DEFAULT_MATERIAL_PINTURA)
            .estiloPint(DEFAULT_ESTILO_PINT);
        return pintura;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pintura createUpdatedEntity(EntityManager em) {
        Pintura pintura = new Pintura()
            .tipoPintura(UPDATED_TIPO_PINTURA)
            .materialPintura(UPDATED_MATERIAL_PINTURA)
            .estiloPint(UPDATED_ESTILO_PINT);
        return pintura;
    }

    @BeforeEach
    public void initTest() {
        pintura = createEntity(em);
    }

    @Test
    @Transactional
    void createPintura() throws Exception {
        int databaseSizeBeforeCreate = pinturaRepository.findAll().size();
        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);
        restPinturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pinturaDTO)))
            .andExpect(status().isCreated());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeCreate + 1);
        Pintura testPintura = pinturaList.get(pinturaList.size() - 1);
        assertThat(testPintura.getTipoPintura()).isEqualTo(DEFAULT_TIPO_PINTURA);
        assertThat(testPintura.getMaterialPintura()).isEqualTo(DEFAULT_MATERIAL_PINTURA);
        assertThat(testPintura.getEstiloPint()).isEqualTo(DEFAULT_ESTILO_PINT);
    }

    @Test
    @Transactional
    void createPinturaWithExistingId() throws Exception {
        // Create the Pintura with an existing ID
        pintura.setId(1L);
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        int databaseSizeBeforeCreate = pinturaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPinturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pinturaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoPinturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pinturaRepository.findAll().size();
        // set the field null
        pintura.setTipoPintura(null);

        // Create the Pintura, which fails.
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        restPinturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pinturaDTO)))
            .andExpect(status().isBadRequest());

        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaterialPinturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = pinturaRepository.findAll().size();
        // set the field null
        pintura.setMaterialPintura(null);

        // Create the Pintura, which fails.
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        restPinturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pinturaDTO)))
            .andExpect(status().isBadRequest());

        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstiloPintIsRequired() throws Exception {
        int databaseSizeBeforeTest = pinturaRepository.findAll().size();
        // set the field null
        pintura.setEstiloPint(null);

        // Create the Pintura, which fails.
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        restPinturaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pinturaDTO)))
            .andExpect(status().isBadRequest());

        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPinturas() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList
        restPinturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pintura.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPintura").value(hasItem(DEFAULT_TIPO_PINTURA)))
            .andExpect(jsonPath("$.[*].materialPintura").value(hasItem(DEFAULT_MATERIAL_PINTURA)))
            .andExpect(jsonPath("$.[*].estiloPint").value(hasItem(DEFAULT_ESTILO_PINT)));
    }

    @Test
    @Transactional
    void getPintura() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get the pintura
        restPinturaMockMvc
            .perform(get(ENTITY_API_URL_ID, pintura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pintura.getId().intValue()))
            .andExpect(jsonPath("$.tipoPintura").value(DEFAULT_TIPO_PINTURA))
            .andExpect(jsonPath("$.materialPintura").value(DEFAULT_MATERIAL_PINTURA))
            .andExpect(jsonPath("$.estiloPint").value(DEFAULT_ESTILO_PINT));
    }

    @Test
    @Transactional
    void getPinturasByIdFiltering() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        Long id = pintura.getId();

        defaultPinturaShouldBeFound("id.equals=" + id);
        defaultPinturaShouldNotBeFound("id.notEquals=" + id);

        defaultPinturaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPinturaShouldNotBeFound("id.greaterThan=" + id);

        defaultPinturaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPinturaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPinturasByTipoPinturaIsEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where tipoPintura equals to DEFAULT_TIPO_PINTURA
        defaultPinturaShouldBeFound("tipoPintura.equals=" + DEFAULT_TIPO_PINTURA);

        // Get all the pinturaList where tipoPintura equals to UPDATED_TIPO_PINTURA
        defaultPinturaShouldNotBeFound("tipoPintura.equals=" + UPDATED_TIPO_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByTipoPinturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where tipoPintura not equals to DEFAULT_TIPO_PINTURA
        defaultPinturaShouldNotBeFound("tipoPintura.notEquals=" + DEFAULT_TIPO_PINTURA);

        // Get all the pinturaList where tipoPintura not equals to UPDATED_TIPO_PINTURA
        defaultPinturaShouldBeFound("tipoPintura.notEquals=" + UPDATED_TIPO_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByTipoPinturaIsInShouldWork() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where tipoPintura in DEFAULT_TIPO_PINTURA or UPDATED_TIPO_PINTURA
        defaultPinturaShouldBeFound("tipoPintura.in=" + DEFAULT_TIPO_PINTURA + "," + UPDATED_TIPO_PINTURA);

        // Get all the pinturaList where tipoPintura equals to UPDATED_TIPO_PINTURA
        defaultPinturaShouldNotBeFound("tipoPintura.in=" + UPDATED_TIPO_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByTipoPinturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where tipoPintura is not null
        defaultPinturaShouldBeFound("tipoPintura.specified=true");

        // Get all the pinturaList where tipoPintura is null
        defaultPinturaShouldNotBeFound("tipoPintura.specified=false");
    }

    @Test
    @Transactional
    void getAllPinturasByTipoPinturaContainsSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where tipoPintura contains DEFAULT_TIPO_PINTURA
        defaultPinturaShouldBeFound("tipoPintura.contains=" + DEFAULT_TIPO_PINTURA);

        // Get all the pinturaList where tipoPintura contains UPDATED_TIPO_PINTURA
        defaultPinturaShouldNotBeFound("tipoPintura.contains=" + UPDATED_TIPO_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByTipoPinturaNotContainsSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where tipoPintura does not contain DEFAULT_TIPO_PINTURA
        defaultPinturaShouldNotBeFound("tipoPintura.doesNotContain=" + DEFAULT_TIPO_PINTURA);

        // Get all the pinturaList where tipoPintura does not contain UPDATED_TIPO_PINTURA
        defaultPinturaShouldBeFound("tipoPintura.doesNotContain=" + UPDATED_TIPO_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByMaterialPinturaIsEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where materialPintura equals to DEFAULT_MATERIAL_PINTURA
        defaultPinturaShouldBeFound("materialPintura.equals=" + DEFAULT_MATERIAL_PINTURA);

        // Get all the pinturaList where materialPintura equals to UPDATED_MATERIAL_PINTURA
        defaultPinturaShouldNotBeFound("materialPintura.equals=" + UPDATED_MATERIAL_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByMaterialPinturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where materialPintura not equals to DEFAULT_MATERIAL_PINTURA
        defaultPinturaShouldNotBeFound("materialPintura.notEquals=" + DEFAULT_MATERIAL_PINTURA);

        // Get all the pinturaList where materialPintura not equals to UPDATED_MATERIAL_PINTURA
        defaultPinturaShouldBeFound("materialPintura.notEquals=" + UPDATED_MATERIAL_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByMaterialPinturaIsInShouldWork() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where materialPintura in DEFAULT_MATERIAL_PINTURA or UPDATED_MATERIAL_PINTURA
        defaultPinturaShouldBeFound("materialPintura.in=" + DEFAULT_MATERIAL_PINTURA + "," + UPDATED_MATERIAL_PINTURA);

        // Get all the pinturaList where materialPintura equals to UPDATED_MATERIAL_PINTURA
        defaultPinturaShouldNotBeFound("materialPintura.in=" + UPDATED_MATERIAL_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByMaterialPinturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where materialPintura is not null
        defaultPinturaShouldBeFound("materialPintura.specified=true");

        // Get all the pinturaList where materialPintura is null
        defaultPinturaShouldNotBeFound("materialPintura.specified=false");
    }

    @Test
    @Transactional
    void getAllPinturasByMaterialPinturaContainsSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where materialPintura contains DEFAULT_MATERIAL_PINTURA
        defaultPinturaShouldBeFound("materialPintura.contains=" + DEFAULT_MATERIAL_PINTURA);

        // Get all the pinturaList where materialPintura contains UPDATED_MATERIAL_PINTURA
        defaultPinturaShouldNotBeFound("materialPintura.contains=" + UPDATED_MATERIAL_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByMaterialPinturaNotContainsSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where materialPintura does not contain DEFAULT_MATERIAL_PINTURA
        defaultPinturaShouldNotBeFound("materialPintura.doesNotContain=" + DEFAULT_MATERIAL_PINTURA);

        // Get all the pinturaList where materialPintura does not contain UPDATED_MATERIAL_PINTURA
        defaultPinturaShouldBeFound("materialPintura.doesNotContain=" + UPDATED_MATERIAL_PINTURA);
    }

    @Test
    @Transactional
    void getAllPinturasByEstiloPintIsEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where estiloPint equals to DEFAULT_ESTILO_PINT
        defaultPinturaShouldBeFound("estiloPint.equals=" + DEFAULT_ESTILO_PINT);

        // Get all the pinturaList where estiloPint equals to UPDATED_ESTILO_PINT
        defaultPinturaShouldNotBeFound("estiloPint.equals=" + UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void getAllPinturasByEstiloPintIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where estiloPint not equals to DEFAULT_ESTILO_PINT
        defaultPinturaShouldNotBeFound("estiloPint.notEquals=" + DEFAULT_ESTILO_PINT);

        // Get all the pinturaList where estiloPint not equals to UPDATED_ESTILO_PINT
        defaultPinturaShouldBeFound("estiloPint.notEquals=" + UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void getAllPinturasByEstiloPintIsInShouldWork() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where estiloPint in DEFAULT_ESTILO_PINT or UPDATED_ESTILO_PINT
        defaultPinturaShouldBeFound("estiloPint.in=" + DEFAULT_ESTILO_PINT + "," + UPDATED_ESTILO_PINT);

        // Get all the pinturaList where estiloPint equals to UPDATED_ESTILO_PINT
        defaultPinturaShouldNotBeFound("estiloPint.in=" + UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void getAllPinturasByEstiloPintIsNullOrNotNull() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where estiloPint is not null
        defaultPinturaShouldBeFound("estiloPint.specified=true");

        // Get all the pinturaList where estiloPint is null
        defaultPinturaShouldNotBeFound("estiloPint.specified=false");
    }

    @Test
    @Transactional
    void getAllPinturasByEstiloPintContainsSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where estiloPint contains DEFAULT_ESTILO_PINT
        defaultPinturaShouldBeFound("estiloPint.contains=" + DEFAULT_ESTILO_PINT);

        // Get all the pinturaList where estiloPint contains UPDATED_ESTILO_PINT
        defaultPinturaShouldNotBeFound("estiloPint.contains=" + UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void getAllPinturasByEstiloPintNotContainsSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        // Get all the pinturaList where estiloPint does not contain DEFAULT_ESTILO_PINT
        defaultPinturaShouldNotBeFound("estiloPint.doesNotContain=" + DEFAULT_ESTILO_PINT);

        // Get all the pinturaList where estiloPint does not contain UPDATED_ESTILO_PINT
        defaultPinturaShouldBeFound("estiloPint.doesNotContain=" + UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void getAllPinturasByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        pintura.setObjArte(objArte);
        pinturaRepository.saveAndFlush(pintura);
        Long objArteId = objArte.getId();

        // Get all the pinturaList where objArte equals to objArteId
        defaultPinturaShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the pinturaList where objArte equals to (objArteId + 1)
        defaultPinturaShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPinturaShouldBeFound(String filter) throws Exception {
        restPinturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pintura.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPintura").value(hasItem(DEFAULT_TIPO_PINTURA)))
            .andExpect(jsonPath("$.[*].materialPintura").value(hasItem(DEFAULT_MATERIAL_PINTURA)))
            .andExpect(jsonPath("$.[*].estiloPint").value(hasItem(DEFAULT_ESTILO_PINT)));

        // Check, that the count call also returns 1
        restPinturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPinturaShouldNotBeFound(String filter) throws Exception {
        restPinturaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPinturaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPintura() throws Exception {
        // Get the pintura
        restPinturaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPintura() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();

        // Update the pintura
        Pintura updatedPintura = pinturaRepository.findById(pintura.getId()).get();
        // Disconnect from session so that the updates on updatedPintura are not directly saved in db
        em.detach(updatedPintura);
        updatedPintura.tipoPintura(UPDATED_TIPO_PINTURA).materialPintura(UPDATED_MATERIAL_PINTURA).estiloPint(UPDATED_ESTILO_PINT);
        PinturaDTO pinturaDTO = pinturaMapper.toDto(updatedPintura);

        restPinturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pinturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pinturaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
        Pintura testPintura = pinturaList.get(pinturaList.size() - 1);
        assertThat(testPintura.getTipoPintura()).isEqualTo(UPDATED_TIPO_PINTURA);
        assertThat(testPintura.getMaterialPintura()).isEqualTo(UPDATED_MATERIAL_PINTURA);
        assertThat(testPintura.getEstiloPint()).isEqualTo(UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void putNonExistingPintura() throws Exception {
        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();
        pintura.setId(count.incrementAndGet());

        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPinturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pinturaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pinturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPintura() throws Exception {
        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();
        pintura.setId(count.incrementAndGet());

        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPinturaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pinturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPintura() throws Exception {
        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();
        pintura.setId(count.incrementAndGet());

        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPinturaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pinturaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePinturaWithPatch() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();

        // Update the pintura using partial update
        Pintura partialUpdatedPintura = new Pintura();
        partialUpdatedPintura.setId(pintura.getId());

        partialUpdatedPintura.materialPintura(UPDATED_MATERIAL_PINTURA);

        restPinturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPintura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPintura))
            )
            .andExpect(status().isOk());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
        Pintura testPintura = pinturaList.get(pinturaList.size() - 1);
        assertThat(testPintura.getTipoPintura()).isEqualTo(DEFAULT_TIPO_PINTURA);
        assertThat(testPintura.getMaterialPintura()).isEqualTo(UPDATED_MATERIAL_PINTURA);
        assertThat(testPintura.getEstiloPint()).isEqualTo(DEFAULT_ESTILO_PINT);
    }

    @Test
    @Transactional
    void fullUpdatePinturaWithPatch() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();

        // Update the pintura using partial update
        Pintura partialUpdatedPintura = new Pintura();
        partialUpdatedPintura.setId(pintura.getId());

        partialUpdatedPintura.tipoPintura(UPDATED_TIPO_PINTURA).materialPintura(UPDATED_MATERIAL_PINTURA).estiloPint(UPDATED_ESTILO_PINT);

        restPinturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPintura.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPintura))
            )
            .andExpect(status().isOk());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
        Pintura testPintura = pinturaList.get(pinturaList.size() - 1);
        assertThat(testPintura.getTipoPintura()).isEqualTo(UPDATED_TIPO_PINTURA);
        assertThat(testPintura.getMaterialPintura()).isEqualTo(UPDATED_MATERIAL_PINTURA);
        assertThat(testPintura.getEstiloPint()).isEqualTo(UPDATED_ESTILO_PINT);
    }

    @Test
    @Transactional
    void patchNonExistingPintura() throws Exception {
        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();
        pintura.setId(count.incrementAndGet());

        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPinturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pinturaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pinturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPintura() throws Exception {
        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();
        pintura.setId(count.incrementAndGet());

        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPinturaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pinturaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPintura() throws Exception {
        int databaseSizeBeforeUpdate = pinturaRepository.findAll().size();
        pintura.setId(count.incrementAndGet());

        // Create the Pintura
        PinturaDTO pinturaDTO = pinturaMapper.toDto(pintura);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPinturaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pinturaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Pintura in the database
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePintura() throws Exception {
        // Initialize the database
        pinturaRepository.saveAndFlush(pintura);

        int databaseSizeBeforeDelete = pinturaRepository.findAll().size();

        // Delete the pintura
        restPinturaMockMvc
            .perform(delete(ENTITY_API_URL_ID, pintura.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Pintura> pinturaList = pinturaRepository.findAll();
        assertThat(pinturaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
