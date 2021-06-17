package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.domain.OtroObj;
import com.mycompany.myapp.repository.OtroObjRepository;
import com.mycompany.myapp.service.criteria.OtroObjCriteria;
import com.mycompany.myapp.service.dto.OtroObjDTO;
import com.mycompany.myapp.service.mapper.OtroObjMapper;
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
 * Integration tests for the {@link OtroObjResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OtroObjResourceIT {

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTILO = "AAAAAAAAAA";
    private static final String UPDATED_ESTILO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/otro-objs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OtroObjRepository otroObjRepository;

    @Autowired
    private OtroObjMapper otroObjMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOtroObjMockMvc;

    private OtroObj otroObj;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtroObj createEntity(EntityManager em) {
        OtroObj otroObj = new OtroObj().tipo(DEFAULT_TIPO).estilo(DEFAULT_ESTILO);
        return otroObj;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OtroObj createUpdatedEntity(EntityManager em) {
        OtroObj otroObj = new OtroObj().tipo(UPDATED_TIPO).estilo(UPDATED_ESTILO);
        return otroObj;
    }

    @BeforeEach
    public void initTest() {
        otroObj = createEntity(em);
    }

    @Test
    @Transactional
    void createOtroObj() throws Exception {
        int databaseSizeBeforeCreate = otroObjRepository.findAll().size();
        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);
        restOtroObjMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otroObjDTO)))
            .andExpect(status().isCreated());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeCreate + 1);
        OtroObj testOtroObj = otroObjList.get(otroObjList.size() - 1);
        assertThat(testOtroObj.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testOtroObj.getEstilo()).isEqualTo(DEFAULT_ESTILO);
    }

    @Test
    @Transactional
    void createOtroObjWithExistingId() throws Exception {
        // Create the OtroObj with an existing ID
        otroObj.setId(1L);
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        int databaseSizeBeforeCreate = otroObjRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOtroObjMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otroObjDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = otroObjRepository.findAll().size();
        // set the field null
        otroObj.setTipo(null);

        // Create the OtroObj, which fails.
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        restOtroObjMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otroObjDTO)))
            .andExpect(status().isBadRequest());

        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstiloIsRequired() throws Exception {
        int databaseSizeBeforeTest = otroObjRepository.findAll().size();
        // set the field null
        otroObj.setEstilo(null);

        // Create the OtroObj, which fails.
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        restOtroObjMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otroObjDTO)))
            .andExpect(status().isBadRequest());

        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOtroObjs() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList
        restOtroObjMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otroObj.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].estilo").value(hasItem(DEFAULT_ESTILO)));
    }

    @Test
    @Transactional
    void getOtroObj() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get the otroObj
        restOtroObjMockMvc
            .perform(get(ENTITY_API_URL_ID, otroObj.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(otroObj.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.estilo").value(DEFAULT_ESTILO));
    }

    @Test
    @Transactional
    void getOtroObjsByIdFiltering() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        Long id = otroObj.getId();

        defaultOtroObjShouldBeFound("id.equals=" + id);
        defaultOtroObjShouldNotBeFound("id.notEquals=" + id);

        defaultOtroObjShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOtroObjShouldNotBeFound("id.greaterThan=" + id);

        defaultOtroObjShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOtroObjShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOtroObjsByTipoIsEqualToSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where tipo equals to DEFAULT_TIPO
        defaultOtroObjShouldBeFound("tipo.equals=" + DEFAULT_TIPO);

        // Get all the otroObjList where tipo equals to UPDATED_TIPO
        defaultOtroObjShouldNotBeFound("tipo.equals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByTipoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where tipo not equals to DEFAULT_TIPO
        defaultOtroObjShouldNotBeFound("tipo.notEquals=" + DEFAULT_TIPO);

        // Get all the otroObjList where tipo not equals to UPDATED_TIPO
        defaultOtroObjShouldBeFound("tipo.notEquals=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByTipoIsInShouldWork() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where tipo in DEFAULT_TIPO or UPDATED_TIPO
        defaultOtroObjShouldBeFound("tipo.in=" + DEFAULT_TIPO + "," + UPDATED_TIPO);

        // Get all the otroObjList where tipo equals to UPDATED_TIPO
        defaultOtroObjShouldNotBeFound("tipo.in=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByTipoIsNullOrNotNull() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where tipo is not null
        defaultOtroObjShouldBeFound("tipo.specified=true");

        // Get all the otroObjList where tipo is null
        defaultOtroObjShouldNotBeFound("tipo.specified=false");
    }

    @Test
    @Transactional
    void getAllOtroObjsByTipoContainsSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where tipo contains DEFAULT_TIPO
        defaultOtroObjShouldBeFound("tipo.contains=" + DEFAULT_TIPO);

        // Get all the otroObjList where tipo contains UPDATED_TIPO
        defaultOtroObjShouldNotBeFound("tipo.contains=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByTipoNotContainsSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where tipo does not contain DEFAULT_TIPO
        defaultOtroObjShouldNotBeFound("tipo.doesNotContain=" + DEFAULT_TIPO);

        // Get all the otroObjList where tipo does not contain UPDATED_TIPO
        defaultOtroObjShouldBeFound("tipo.doesNotContain=" + UPDATED_TIPO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByEstiloIsEqualToSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where estilo equals to DEFAULT_ESTILO
        defaultOtroObjShouldBeFound("estilo.equals=" + DEFAULT_ESTILO);

        // Get all the otroObjList where estilo equals to UPDATED_ESTILO
        defaultOtroObjShouldNotBeFound("estilo.equals=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByEstiloIsNotEqualToSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where estilo not equals to DEFAULT_ESTILO
        defaultOtroObjShouldNotBeFound("estilo.notEquals=" + DEFAULT_ESTILO);

        // Get all the otroObjList where estilo not equals to UPDATED_ESTILO
        defaultOtroObjShouldBeFound("estilo.notEquals=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByEstiloIsInShouldWork() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where estilo in DEFAULT_ESTILO or UPDATED_ESTILO
        defaultOtroObjShouldBeFound("estilo.in=" + DEFAULT_ESTILO + "," + UPDATED_ESTILO);

        // Get all the otroObjList where estilo equals to UPDATED_ESTILO
        defaultOtroObjShouldNotBeFound("estilo.in=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByEstiloIsNullOrNotNull() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where estilo is not null
        defaultOtroObjShouldBeFound("estilo.specified=true");

        // Get all the otroObjList where estilo is null
        defaultOtroObjShouldNotBeFound("estilo.specified=false");
    }

    @Test
    @Transactional
    void getAllOtroObjsByEstiloContainsSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where estilo contains DEFAULT_ESTILO
        defaultOtroObjShouldBeFound("estilo.contains=" + DEFAULT_ESTILO);

        // Get all the otroObjList where estilo contains UPDATED_ESTILO
        defaultOtroObjShouldNotBeFound("estilo.contains=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByEstiloNotContainsSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        // Get all the otroObjList where estilo does not contain DEFAULT_ESTILO
        defaultOtroObjShouldNotBeFound("estilo.doesNotContain=" + DEFAULT_ESTILO);

        // Get all the otroObjList where estilo does not contain UPDATED_ESTILO
        defaultOtroObjShouldBeFound("estilo.doesNotContain=" + UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void getAllOtroObjsByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        otroObj.setObjArte(objArte);
        otroObjRepository.saveAndFlush(otroObj);
        Long objArteId = objArte.getId();

        // Get all the otroObjList where objArte equals to objArteId
        defaultOtroObjShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the otroObjList where objArte equals to (objArteId + 1)
        defaultOtroObjShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOtroObjShouldBeFound(String filter) throws Exception {
        restOtroObjMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(otroObj.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].estilo").value(hasItem(DEFAULT_ESTILO)));

        // Check, that the count call also returns 1
        restOtroObjMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOtroObjShouldNotBeFound(String filter) throws Exception {
        restOtroObjMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOtroObjMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOtroObj() throws Exception {
        // Get the otroObj
        restOtroObjMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOtroObj() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();

        // Update the otroObj
        OtroObj updatedOtroObj = otroObjRepository.findById(otroObj.getId()).get();
        // Disconnect from session so that the updates on updatedOtroObj are not directly saved in db
        em.detach(updatedOtroObj);
        updatedOtroObj.tipo(UPDATED_TIPO).estilo(UPDATED_ESTILO);
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(updatedOtroObj);

        restOtroObjMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otroObjDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otroObjDTO))
            )
            .andExpect(status().isOk());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
        OtroObj testOtroObj = otroObjList.get(otroObjList.size() - 1);
        assertThat(testOtroObj.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testOtroObj.getEstilo()).isEqualTo(UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void putNonExistingOtroObj() throws Exception {
        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();
        otroObj.setId(count.incrementAndGet());

        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtroObjMockMvc
            .perform(
                put(ENTITY_API_URL_ID, otroObjDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otroObjDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOtroObj() throws Exception {
        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();
        otroObj.setId(count.incrementAndGet());

        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtroObjMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(otroObjDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOtroObj() throws Exception {
        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();
        otroObj.setId(count.incrementAndGet());

        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtroObjMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(otroObjDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOtroObjWithPatch() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();

        // Update the otroObj using partial update
        OtroObj partialUpdatedOtroObj = new OtroObj();
        partialUpdatedOtroObj.setId(otroObj.getId());

        partialUpdatedOtroObj.tipo(UPDATED_TIPO).estilo(UPDATED_ESTILO);

        restOtroObjMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtroObj.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtroObj))
            )
            .andExpect(status().isOk());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
        OtroObj testOtroObj = otroObjList.get(otroObjList.size() - 1);
        assertThat(testOtroObj.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testOtroObj.getEstilo()).isEqualTo(UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void fullUpdateOtroObjWithPatch() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();

        // Update the otroObj using partial update
        OtroObj partialUpdatedOtroObj = new OtroObj();
        partialUpdatedOtroObj.setId(otroObj.getId());

        partialUpdatedOtroObj.tipo(UPDATED_TIPO).estilo(UPDATED_ESTILO);

        restOtroObjMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOtroObj.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOtroObj))
            )
            .andExpect(status().isOk());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
        OtroObj testOtroObj = otroObjList.get(otroObjList.size() - 1);
        assertThat(testOtroObj.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testOtroObj.getEstilo()).isEqualTo(UPDATED_ESTILO);
    }

    @Test
    @Transactional
    void patchNonExistingOtroObj() throws Exception {
        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();
        otroObj.setId(count.incrementAndGet());

        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOtroObjMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, otroObjDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otroObjDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOtroObj() throws Exception {
        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();
        otroObj.setId(count.incrementAndGet());

        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtroObjMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(otroObjDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOtroObj() throws Exception {
        int databaseSizeBeforeUpdate = otroObjRepository.findAll().size();
        otroObj.setId(count.incrementAndGet());

        // Create the OtroObj
        OtroObjDTO otroObjDTO = otroObjMapper.toDto(otroObj);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOtroObjMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(otroObjDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OtroObj in the database
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOtroObj() throws Exception {
        // Initialize the database
        otroObjRepository.saveAndFlush(otroObj);

        int databaseSizeBeforeDelete = otroObjRepository.findAll().size();

        // Delete the otroObj
        restOtroObjMockMvc
            .perform(delete(ENTITY_API_URL_ID, otroObj.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OtroObj> otroObjList = otroObjRepository.findAll();
        assertThat(otroObjList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
