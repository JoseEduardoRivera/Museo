package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Exhibicion;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.ExhibicionRepository;
import com.mycompany.myapp.service.criteria.ExhibicionCriteria;
import com.mycompany.myapp.service.dto.ExhibicionDTO;
import com.mycompany.myapp.service.mapper.ExhibicionMapper;
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
 * Integration tests for the {@link ExhibicionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExhibicionResourceIT {

    private static final String DEFAULT_NOM_EXI = "AAAAAAAAAA";
    private static final String UPDATED_NOM_EXI = "BBBBBBBBBB";

    private static final String DEFAULT_FECH_INI = "AAAAAAAAAA";
    private static final String UPDATED_FECH_INI = "BBBBBBBBBB";

    private static final String DEFAULT_FECH_FIN = "AAAAAAAAAA";
    private static final String UPDATED_FECH_FIN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/exhibicions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExhibicionRepository exhibicionRepository;

    @Autowired
    private ExhibicionMapper exhibicionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExhibicionMockMvc;

    private Exhibicion exhibicion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exhibicion createEntity(EntityManager em) {
        Exhibicion exhibicion = new Exhibicion().nomExi(DEFAULT_NOM_EXI).fechIni(DEFAULT_FECH_INI).fechFin(DEFAULT_FECH_FIN);
        return exhibicion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Exhibicion createUpdatedEntity(EntityManager em) {
        Exhibicion exhibicion = new Exhibicion().nomExi(UPDATED_NOM_EXI).fechIni(UPDATED_FECH_INI).fechFin(UPDATED_FECH_FIN);
        return exhibicion;
    }

    @BeforeEach
    public void initTest() {
        exhibicion = createEntity(em);
    }

    @Test
    @Transactional
    void createExhibicion() throws Exception {
        int databaseSizeBeforeCreate = exhibicionRepository.findAll().size();
        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);
        restExhibicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exhibicionDTO)))
            .andExpect(status().isCreated());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeCreate + 1);
        Exhibicion testExhibicion = exhibicionList.get(exhibicionList.size() - 1);
        assertThat(testExhibicion.getNomExi()).isEqualTo(DEFAULT_NOM_EXI);
        assertThat(testExhibicion.getFechIni()).isEqualTo(DEFAULT_FECH_INI);
        assertThat(testExhibicion.getFechFin()).isEqualTo(DEFAULT_FECH_FIN);
    }

    @Test
    @Transactional
    void createExhibicionWithExistingId() throws Exception {
        // Create the Exhibicion with an existing ID
        exhibicion.setId(1L);
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        int databaseSizeBeforeCreate = exhibicionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExhibicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exhibicionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomExiIsRequired() throws Exception {
        int databaseSizeBeforeTest = exhibicionRepository.findAll().size();
        // set the field null
        exhibicion.setNomExi(null);

        // Create the Exhibicion, which fails.
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        restExhibicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exhibicionDTO)))
            .andExpect(status().isBadRequest());

        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechIniIsRequired() throws Exception {
        int databaseSizeBeforeTest = exhibicionRepository.findAll().size();
        // set the field null
        exhibicion.setFechIni(null);

        // Create the Exhibicion, which fails.
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        restExhibicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exhibicionDTO)))
            .andExpect(status().isBadRequest());

        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechFinIsRequired() throws Exception {
        int databaseSizeBeforeTest = exhibicionRepository.findAll().size();
        // set the field null
        exhibicion.setFechFin(null);

        // Create the Exhibicion, which fails.
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        restExhibicionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exhibicionDTO)))
            .andExpect(status().isBadRequest());

        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllExhibicions() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList
        restExhibicionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exhibicion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomExi").value(hasItem(DEFAULT_NOM_EXI)))
            .andExpect(jsonPath("$.[*].fechIni").value(hasItem(DEFAULT_FECH_INI)))
            .andExpect(jsonPath("$.[*].fechFin").value(hasItem(DEFAULT_FECH_FIN)));
    }

    @Test
    @Transactional
    void getExhibicion() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get the exhibicion
        restExhibicionMockMvc
            .perform(get(ENTITY_API_URL_ID, exhibicion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exhibicion.getId().intValue()))
            .andExpect(jsonPath("$.nomExi").value(DEFAULT_NOM_EXI))
            .andExpect(jsonPath("$.fechIni").value(DEFAULT_FECH_INI))
            .andExpect(jsonPath("$.fechFin").value(DEFAULT_FECH_FIN));
    }

    @Test
    @Transactional
    void getExhibicionsByIdFiltering() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        Long id = exhibicion.getId();

        defaultExhibicionShouldBeFound("id.equals=" + id);
        defaultExhibicionShouldNotBeFound("id.notEquals=" + id);

        defaultExhibicionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExhibicionShouldNotBeFound("id.greaterThan=" + id);

        defaultExhibicionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExhibicionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExhibicionsByNomExiIsEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where nomExi equals to DEFAULT_NOM_EXI
        defaultExhibicionShouldBeFound("nomExi.equals=" + DEFAULT_NOM_EXI);

        // Get all the exhibicionList where nomExi equals to UPDATED_NOM_EXI
        defaultExhibicionShouldNotBeFound("nomExi.equals=" + UPDATED_NOM_EXI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByNomExiIsNotEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where nomExi not equals to DEFAULT_NOM_EXI
        defaultExhibicionShouldNotBeFound("nomExi.notEquals=" + DEFAULT_NOM_EXI);

        // Get all the exhibicionList where nomExi not equals to UPDATED_NOM_EXI
        defaultExhibicionShouldBeFound("nomExi.notEquals=" + UPDATED_NOM_EXI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByNomExiIsInShouldWork() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where nomExi in DEFAULT_NOM_EXI or UPDATED_NOM_EXI
        defaultExhibicionShouldBeFound("nomExi.in=" + DEFAULT_NOM_EXI + "," + UPDATED_NOM_EXI);

        // Get all the exhibicionList where nomExi equals to UPDATED_NOM_EXI
        defaultExhibicionShouldNotBeFound("nomExi.in=" + UPDATED_NOM_EXI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByNomExiIsNullOrNotNull() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where nomExi is not null
        defaultExhibicionShouldBeFound("nomExi.specified=true");

        // Get all the exhibicionList where nomExi is null
        defaultExhibicionShouldNotBeFound("nomExi.specified=false");
    }

    @Test
    @Transactional
    void getAllExhibicionsByNomExiContainsSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where nomExi contains DEFAULT_NOM_EXI
        defaultExhibicionShouldBeFound("nomExi.contains=" + DEFAULT_NOM_EXI);

        // Get all the exhibicionList where nomExi contains UPDATED_NOM_EXI
        defaultExhibicionShouldNotBeFound("nomExi.contains=" + UPDATED_NOM_EXI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByNomExiNotContainsSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where nomExi does not contain DEFAULT_NOM_EXI
        defaultExhibicionShouldNotBeFound("nomExi.doesNotContain=" + DEFAULT_NOM_EXI);

        // Get all the exhibicionList where nomExi does not contain UPDATED_NOM_EXI
        defaultExhibicionShouldBeFound("nomExi.doesNotContain=" + UPDATED_NOM_EXI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechIniIsEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechIni equals to DEFAULT_FECH_INI
        defaultExhibicionShouldBeFound("fechIni.equals=" + DEFAULT_FECH_INI);

        // Get all the exhibicionList where fechIni equals to UPDATED_FECH_INI
        defaultExhibicionShouldNotBeFound("fechIni.equals=" + UPDATED_FECH_INI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechIniIsNotEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechIni not equals to DEFAULT_FECH_INI
        defaultExhibicionShouldNotBeFound("fechIni.notEquals=" + DEFAULT_FECH_INI);

        // Get all the exhibicionList where fechIni not equals to UPDATED_FECH_INI
        defaultExhibicionShouldBeFound("fechIni.notEquals=" + UPDATED_FECH_INI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechIniIsInShouldWork() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechIni in DEFAULT_FECH_INI or UPDATED_FECH_INI
        defaultExhibicionShouldBeFound("fechIni.in=" + DEFAULT_FECH_INI + "," + UPDATED_FECH_INI);

        // Get all the exhibicionList where fechIni equals to UPDATED_FECH_INI
        defaultExhibicionShouldNotBeFound("fechIni.in=" + UPDATED_FECH_INI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechIniIsNullOrNotNull() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechIni is not null
        defaultExhibicionShouldBeFound("fechIni.specified=true");

        // Get all the exhibicionList where fechIni is null
        defaultExhibicionShouldNotBeFound("fechIni.specified=false");
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechIniContainsSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechIni contains DEFAULT_FECH_INI
        defaultExhibicionShouldBeFound("fechIni.contains=" + DEFAULT_FECH_INI);

        // Get all the exhibicionList where fechIni contains UPDATED_FECH_INI
        defaultExhibicionShouldNotBeFound("fechIni.contains=" + UPDATED_FECH_INI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechIniNotContainsSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechIni does not contain DEFAULT_FECH_INI
        defaultExhibicionShouldNotBeFound("fechIni.doesNotContain=" + DEFAULT_FECH_INI);

        // Get all the exhibicionList where fechIni does not contain UPDATED_FECH_INI
        defaultExhibicionShouldBeFound("fechIni.doesNotContain=" + UPDATED_FECH_INI);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechFinIsEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechFin equals to DEFAULT_FECH_FIN
        defaultExhibicionShouldBeFound("fechFin.equals=" + DEFAULT_FECH_FIN);

        // Get all the exhibicionList where fechFin equals to UPDATED_FECH_FIN
        defaultExhibicionShouldNotBeFound("fechFin.equals=" + UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechFinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechFin not equals to DEFAULT_FECH_FIN
        defaultExhibicionShouldNotBeFound("fechFin.notEquals=" + DEFAULT_FECH_FIN);

        // Get all the exhibicionList where fechFin not equals to UPDATED_FECH_FIN
        defaultExhibicionShouldBeFound("fechFin.notEquals=" + UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechFinIsInShouldWork() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechFin in DEFAULT_FECH_FIN or UPDATED_FECH_FIN
        defaultExhibicionShouldBeFound("fechFin.in=" + DEFAULT_FECH_FIN + "," + UPDATED_FECH_FIN);

        // Get all the exhibicionList where fechFin equals to UPDATED_FECH_FIN
        defaultExhibicionShouldNotBeFound("fechFin.in=" + UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechFinIsNullOrNotNull() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechFin is not null
        defaultExhibicionShouldBeFound("fechFin.specified=true");

        // Get all the exhibicionList where fechFin is null
        defaultExhibicionShouldNotBeFound("fechFin.specified=false");
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechFinContainsSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechFin contains DEFAULT_FECH_FIN
        defaultExhibicionShouldBeFound("fechFin.contains=" + DEFAULT_FECH_FIN);

        // Get all the exhibicionList where fechFin contains UPDATED_FECH_FIN
        defaultExhibicionShouldNotBeFound("fechFin.contains=" + UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void getAllExhibicionsByFechFinNotContainsSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        // Get all the exhibicionList where fechFin does not contain DEFAULT_FECH_FIN
        defaultExhibicionShouldNotBeFound("fechFin.doesNotContain=" + DEFAULT_FECH_FIN);

        // Get all the exhibicionList where fechFin does not contain UPDATED_FECH_FIN
        defaultExhibicionShouldBeFound("fechFin.doesNotContain=" + UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void getAllExhibicionsByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        exhibicion.addObjArte(objArte);
        exhibicionRepository.saveAndFlush(exhibicion);
        Long objArteId = objArte.getId();

        // Get all the exhibicionList where objArte equals to objArteId
        defaultExhibicionShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the exhibicionList where objArte equals to (objArteId + 1)
        defaultExhibicionShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExhibicionShouldBeFound(String filter) throws Exception {
        restExhibicionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exhibicion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomExi").value(hasItem(DEFAULT_NOM_EXI)))
            .andExpect(jsonPath("$.[*].fechIni").value(hasItem(DEFAULT_FECH_INI)))
            .andExpect(jsonPath("$.[*].fechFin").value(hasItem(DEFAULT_FECH_FIN)));

        // Check, that the count call also returns 1
        restExhibicionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExhibicionShouldNotBeFound(String filter) throws Exception {
        restExhibicionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExhibicionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExhibicion() throws Exception {
        // Get the exhibicion
        restExhibicionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewExhibicion() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();

        // Update the exhibicion
        Exhibicion updatedExhibicion = exhibicionRepository.findById(exhibicion.getId()).get();
        // Disconnect from session so that the updates on updatedExhibicion are not directly saved in db
        em.detach(updatedExhibicion);
        updatedExhibicion.nomExi(UPDATED_NOM_EXI).fechIni(UPDATED_FECH_INI).fechFin(UPDATED_FECH_FIN);
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(updatedExhibicion);

        restExhibicionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exhibicionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exhibicionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
        Exhibicion testExhibicion = exhibicionList.get(exhibicionList.size() - 1);
        assertThat(testExhibicion.getNomExi()).isEqualTo(UPDATED_NOM_EXI);
        assertThat(testExhibicion.getFechIni()).isEqualTo(UPDATED_FECH_INI);
        assertThat(testExhibicion.getFechFin()).isEqualTo(UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void putNonExistingExhibicion() throws Exception {
        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();
        exhibicion.setId(count.incrementAndGet());

        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExhibicionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exhibicionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exhibicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExhibicion() throws Exception {
        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();
        exhibicion.setId(count.incrementAndGet());

        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExhibicionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exhibicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExhibicion() throws Exception {
        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();
        exhibicion.setId(count.incrementAndGet());

        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExhibicionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exhibicionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExhibicionWithPatch() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();

        // Update the exhibicion using partial update
        Exhibicion partialUpdatedExhibicion = new Exhibicion();
        partialUpdatedExhibicion.setId(exhibicion.getId());

        partialUpdatedExhibicion.fechIni(UPDATED_FECH_INI);

        restExhibicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExhibicion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExhibicion))
            )
            .andExpect(status().isOk());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
        Exhibicion testExhibicion = exhibicionList.get(exhibicionList.size() - 1);
        assertThat(testExhibicion.getNomExi()).isEqualTo(DEFAULT_NOM_EXI);
        assertThat(testExhibicion.getFechIni()).isEqualTo(UPDATED_FECH_INI);
        assertThat(testExhibicion.getFechFin()).isEqualTo(DEFAULT_FECH_FIN);
    }

    @Test
    @Transactional
    void fullUpdateExhibicionWithPatch() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();

        // Update the exhibicion using partial update
        Exhibicion partialUpdatedExhibicion = new Exhibicion();
        partialUpdatedExhibicion.setId(exhibicion.getId());

        partialUpdatedExhibicion.nomExi(UPDATED_NOM_EXI).fechIni(UPDATED_FECH_INI).fechFin(UPDATED_FECH_FIN);

        restExhibicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExhibicion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExhibicion))
            )
            .andExpect(status().isOk());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
        Exhibicion testExhibicion = exhibicionList.get(exhibicionList.size() - 1);
        assertThat(testExhibicion.getNomExi()).isEqualTo(UPDATED_NOM_EXI);
        assertThat(testExhibicion.getFechIni()).isEqualTo(UPDATED_FECH_INI);
        assertThat(testExhibicion.getFechFin()).isEqualTo(UPDATED_FECH_FIN);
    }

    @Test
    @Transactional
    void patchNonExistingExhibicion() throws Exception {
        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();
        exhibicion.setId(count.incrementAndGet());

        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExhibicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exhibicionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exhibicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExhibicion() throws Exception {
        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();
        exhibicion.setId(count.incrementAndGet());

        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExhibicionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exhibicionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExhibicion() throws Exception {
        int databaseSizeBeforeUpdate = exhibicionRepository.findAll().size();
        exhibicion.setId(count.incrementAndGet());

        // Create the Exhibicion
        ExhibicionDTO exhibicionDTO = exhibicionMapper.toDto(exhibicion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExhibicionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(exhibicionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Exhibicion in the database
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExhibicion() throws Exception {
        // Initialize the database
        exhibicionRepository.saveAndFlush(exhibicion);

        int databaseSizeBeforeDelete = exhibicionRepository.findAll().size();

        // Delete the exhibicion
        restExhibicionMockMvc
            .perform(delete(ENTITY_API_URL_ID, exhibicion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Exhibicion> exhibicionList = exhibicionRepository.findAll();
        assertThat(exhibicionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
