package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Artista;
import com.mycompany.myapp.domain.CollecPerma;
import com.mycompany.myapp.domain.CollecPresta;
import com.mycompany.myapp.domain.Escultura;
import com.mycompany.myapp.domain.Exhibicion;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.domain.OtroObj;
import com.mycompany.myapp.domain.Pintura;
import com.mycompany.myapp.repository.ObjArteRepository;
import com.mycompany.myapp.service.ObjArteService;
import com.mycompany.myapp.service.criteria.ObjArteCriteria;
import com.mycompany.myapp.service.dto.ObjArteDTO;
import com.mycompany.myapp.service.mapper.ObjArteMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ObjArteResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ObjArteResourceIT {

    private static final String DEFAULT_ID_OBJ_ART = "AAAAAAAAAA";
    private static final String UPDATED_ID_OBJ_ART = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS_CULTURA = "AAAAAAAAAA";
    private static final String UPDATED_PAIS_CULTURA = "BBBBBBBBBB";

    private static final String DEFAULT_ANIO = "AAAAAAAAAA";
    private static final String UPDATED_ANIO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO_OBJ = "AAAAAAAAAA";
    private static final String UPDATED_TITULO_OBJ = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_OBJ = "AAAAAAAAAA";
    private static final String UPDATED_DESC_OBJ = "BBBBBBBBBB";

    private static final String DEFAULT_EPOCA_OBJ = "AAAAAAAAAA";
    private static final String UPDATED_EPOCA_OBJ = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/obj-artes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjArteRepository objArteRepository;

    @Mock
    private ObjArteRepository objArteRepositoryMock;

    @Autowired
    private ObjArteMapper objArteMapper;

    @Mock
    private ObjArteService objArteServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restObjArteMockMvc;

    private ObjArte objArte;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjArte createEntity(EntityManager em) {
        ObjArte objArte = new ObjArte()
            .idObjArt(DEFAULT_ID_OBJ_ART)
            .paisCultura(DEFAULT_PAIS_CULTURA)
            .anio(DEFAULT_ANIO)
            .tituloObj(DEFAULT_TITULO_OBJ)
            .descObj(DEFAULT_DESC_OBJ)
            .epocaObj(DEFAULT_EPOCA_OBJ);
        return objArte;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ObjArte createUpdatedEntity(EntityManager em) {
        ObjArte objArte = new ObjArte()
            .idObjArt(UPDATED_ID_OBJ_ART)
            .paisCultura(UPDATED_PAIS_CULTURA)
            .anio(UPDATED_ANIO)
            .tituloObj(UPDATED_TITULO_OBJ)
            .descObj(UPDATED_DESC_OBJ)
            .epocaObj(UPDATED_EPOCA_OBJ);
        return objArte;
    }

    @BeforeEach
    public void initTest() {
        objArte = createEntity(em);
    }

    @Test
    @Transactional
    void createObjArte() throws Exception {
        int databaseSizeBeforeCreate = objArteRepository.findAll().size();
        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);
        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isCreated());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeCreate + 1);
        ObjArte testObjArte = objArteList.get(objArteList.size() - 1);
        assertThat(testObjArte.getIdObjArt()).isEqualTo(DEFAULT_ID_OBJ_ART);
        assertThat(testObjArte.getPaisCultura()).isEqualTo(DEFAULT_PAIS_CULTURA);
        assertThat(testObjArte.getAnio()).isEqualTo(DEFAULT_ANIO);
        assertThat(testObjArte.getTituloObj()).isEqualTo(DEFAULT_TITULO_OBJ);
        assertThat(testObjArte.getDescObj()).isEqualTo(DEFAULT_DESC_OBJ);
        assertThat(testObjArte.getEpocaObj()).isEqualTo(DEFAULT_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void createObjArteWithExistingId() throws Exception {
        // Create the ObjArte with an existing ID
        objArte.setId(1L);
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        int databaseSizeBeforeCreate = objArteRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdObjArtIsRequired() throws Exception {
        int databaseSizeBeforeTest = objArteRepository.findAll().size();
        // set the field null
        objArte.setIdObjArt(null);

        // Create the ObjArte, which fails.
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isBadRequest());

        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisCulturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = objArteRepository.findAll().size();
        // set the field null
        objArte.setPaisCultura(null);

        // Create the ObjArte, which fails.
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isBadRequest());

        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTituloObjIsRequired() throws Exception {
        int databaseSizeBeforeTest = objArteRepository.findAll().size();
        // set the field null
        objArte.setTituloObj(null);

        // Create the ObjArte, which fails.
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isBadRequest());

        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescObjIsRequired() throws Exception {
        int databaseSizeBeforeTest = objArteRepository.findAll().size();
        // set the field null
        objArte.setDescObj(null);

        // Create the ObjArte, which fails.
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isBadRequest());

        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEpocaObjIsRequired() throws Exception {
        int databaseSizeBeforeTest = objArteRepository.findAll().size();
        // set the field null
        objArte.setEpocaObj(null);

        // Create the ObjArte, which fails.
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        restObjArteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isBadRequest());

        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllObjArtes() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList
        restObjArteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objArte.getId().intValue())))
            .andExpect(jsonPath("$.[*].idObjArt").value(hasItem(DEFAULT_ID_OBJ_ART)))
            .andExpect(jsonPath("$.[*].paisCultura").value(hasItem(DEFAULT_PAIS_CULTURA)))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].tituloObj").value(hasItem(DEFAULT_TITULO_OBJ)))
            .andExpect(jsonPath("$.[*].descObj").value(hasItem(DEFAULT_DESC_OBJ)))
            .andExpect(jsonPath("$.[*].epocaObj").value(hasItem(DEFAULT_EPOCA_OBJ)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObjArtesWithEagerRelationshipsIsEnabled() throws Exception {
        when(objArteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObjArteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(objArteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllObjArtesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(objArteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restObjArteMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(objArteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getObjArte() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get the objArte
        restObjArteMockMvc
            .perform(get(ENTITY_API_URL_ID, objArte.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(objArte.getId().intValue()))
            .andExpect(jsonPath("$.idObjArt").value(DEFAULT_ID_OBJ_ART))
            .andExpect(jsonPath("$.paisCultura").value(DEFAULT_PAIS_CULTURA))
            .andExpect(jsonPath("$.anio").value(DEFAULT_ANIO))
            .andExpect(jsonPath("$.tituloObj").value(DEFAULT_TITULO_OBJ))
            .andExpect(jsonPath("$.descObj").value(DEFAULT_DESC_OBJ))
            .andExpect(jsonPath("$.epocaObj").value(DEFAULT_EPOCA_OBJ));
    }

    @Test
    @Transactional
    void getObjArtesByIdFiltering() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        Long id = objArte.getId();

        defaultObjArteShouldBeFound("id.equals=" + id);
        defaultObjArteShouldNotBeFound("id.notEquals=" + id);

        defaultObjArteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultObjArteShouldNotBeFound("id.greaterThan=" + id);

        defaultObjArteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultObjArteShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllObjArtesByIdObjArtIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where idObjArt equals to DEFAULT_ID_OBJ_ART
        defaultObjArteShouldBeFound("idObjArt.equals=" + DEFAULT_ID_OBJ_ART);

        // Get all the objArteList where idObjArt equals to UPDATED_ID_OBJ_ART
        defaultObjArteShouldNotBeFound("idObjArt.equals=" + UPDATED_ID_OBJ_ART);
    }

    @Test
    @Transactional
    void getAllObjArtesByIdObjArtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where idObjArt not equals to DEFAULT_ID_OBJ_ART
        defaultObjArteShouldNotBeFound("idObjArt.notEquals=" + DEFAULT_ID_OBJ_ART);

        // Get all the objArteList where idObjArt not equals to UPDATED_ID_OBJ_ART
        defaultObjArteShouldBeFound("idObjArt.notEquals=" + UPDATED_ID_OBJ_ART);
    }

    @Test
    @Transactional
    void getAllObjArtesByIdObjArtIsInShouldWork() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where idObjArt in DEFAULT_ID_OBJ_ART or UPDATED_ID_OBJ_ART
        defaultObjArteShouldBeFound("idObjArt.in=" + DEFAULT_ID_OBJ_ART + "," + UPDATED_ID_OBJ_ART);

        // Get all the objArteList where idObjArt equals to UPDATED_ID_OBJ_ART
        defaultObjArteShouldNotBeFound("idObjArt.in=" + UPDATED_ID_OBJ_ART);
    }

    @Test
    @Transactional
    void getAllObjArtesByIdObjArtIsNullOrNotNull() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where idObjArt is not null
        defaultObjArteShouldBeFound("idObjArt.specified=true");

        // Get all the objArteList where idObjArt is null
        defaultObjArteShouldNotBeFound("idObjArt.specified=false");
    }

    @Test
    @Transactional
    void getAllObjArtesByIdObjArtContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where idObjArt contains DEFAULT_ID_OBJ_ART
        defaultObjArteShouldBeFound("idObjArt.contains=" + DEFAULT_ID_OBJ_ART);

        // Get all the objArteList where idObjArt contains UPDATED_ID_OBJ_ART
        defaultObjArteShouldNotBeFound("idObjArt.contains=" + UPDATED_ID_OBJ_ART);
    }

    @Test
    @Transactional
    void getAllObjArtesByIdObjArtNotContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where idObjArt does not contain DEFAULT_ID_OBJ_ART
        defaultObjArteShouldNotBeFound("idObjArt.doesNotContain=" + DEFAULT_ID_OBJ_ART);

        // Get all the objArteList where idObjArt does not contain UPDATED_ID_OBJ_ART
        defaultObjArteShouldBeFound("idObjArt.doesNotContain=" + UPDATED_ID_OBJ_ART);
    }

    @Test
    @Transactional
    void getAllObjArtesByPaisCulturaIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where paisCultura equals to DEFAULT_PAIS_CULTURA
        defaultObjArteShouldBeFound("paisCultura.equals=" + DEFAULT_PAIS_CULTURA);

        // Get all the objArteList where paisCultura equals to UPDATED_PAIS_CULTURA
        defaultObjArteShouldNotBeFound("paisCultura.equals=" + UPDATED_PAIS_CULTURA);
    }

    @Test
    @Transactional
    void getAllObjArtesByPaisCulturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where paisCultura not equals to DEFAULT_PAIS_CULTURA
        defaultObjArteShouldNotBeFound("paisCultura.notEquals=" + DEFAULT_PAIS_CULTURA);

        // Get all the objArteList where paisCultura not equals to UPDATED_PAIS_CULTURA
        defaultObjArteShouldBeFound("paisCultura.notEquals=" + UPDATED_PAIS_CULTURA);
    }

    @Test
    @Transactional
    void getAllObjArtesByPaisCulturaIsInShouldWork() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where paisCultura in DEFAULT_PAIS_CULTURA or UPDATED_PAIS_CULTURA
        defaultObjArteShouldBeFound("paisCultura.in=" + DEFAULT_PAIS_CULTURA + "," + UPDATED_PAIS_CULTURA);

        // Get all the objArteList where paisCultura equals to UPDATED_PAIS_CULTURA
        defaultObjArteShouldNotBeFound("paisCultura.in=" + UPDATED_PAIS_CULTURA);
    }

    @Test
    @Transactional
    void getAllObjArtesByPaisCulturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where paisCultura is not null
        defaultObjArteShouldBeFound("paisCultura.specified=true");

        // Get all the objArteList where paisCultura is null
        defaultObjArteShouldNotBeFound("paisCultura.specified=false");
    }

    @Test
    @Transactional
    void getAllObjArtesByPaisCulturaContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where paisCultura contains DEFAULT_PAIS_CULTURA
        defaultObjArteShouldBeFound("paisCultura.contains=" + DEFAULT_PAIS_CULTURA);

        // Get all the objArteList where paisCultura contains UPDATED_PAIS_CULTURA
        defaultObjArteShouldNotBeFound("paisCultura.contains=" + UPDATED_PAIS_CULTURA);
    }

    @Test
    @Transactional
    void getAllObjArtesByPaisCulturaNotContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where paisCultura does not contain DEFAULT_PAIS_CULTURA
        defaultObjArteShouldNotBeFound("paisCultura.doesNotContain=" + DEFAULT_PAIS_CULTURA);

        // Get all the objArteList where paisCultura does not contain UPDATED_PAIS_CULTURA
        defaultObjArteShouldBeFound("paisCultura.doesNotContain=" + UPDATED_PAIS_CULTURA);
    }

    @Test
    @Transactional
    void getAllObjArtesByAnioIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where anio equals to DEFAULT_ANIO
        defaultObjArteShouldBeFound("anio.equals=" + DEFAULT_ANIO);

        // Get all the objArteList where anio equals to UPDATED_ANIO
        defaultObjArteShouldNotBeFound("anio.equals=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    void getAllObjArtesByAnioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where anio not equals to DEFAULT_ANIO
        defaultObjArteShouldNotBeFound("anio.notEquals=" + DEFAULT_ANIO);

        // Get all the objArteList where anio not equals to UPDATED_ANIO
        defaultObjArteShouldBeFound("anio.notEquals=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    void getAllObjArtesByAnioIsInShouldWork() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where anio in DEFAULT_ANIO or UPDATED_ANIO
        defaultObjArteShouldBeFound("anio.in=" + DEFAULT_ANIO + "," + UPDATED_ANIO);

        // Get all the objArteList where anio equals to UPDATED_ANIO
        defaultObjArteShouldNotBeFound("anio.in=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    void getAllObjArtesByAnioIsNullOrNotNull() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where anio is not null
        defaultObjArteShouldBeFound("anio.specified=true");

        // Get all the objArteList where anio is null
        defaultObjArteShouldNotBeFound("anio.specified=false");
    }

    @Test
    @Transactional
    void getAllObjArtesByAnioContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where anio contains DEFAULT_ANIO
        defaultObjArteShouldBeFound("anio.contains=" + DEFAULT_ANIO);

        // Get all the objArteList where anio contains UPDATED_ANIO
        defaultObjArteShouldNotBeFound("anio.contains=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    void getAllObjArtesByAnioNotContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where anio does not contain DEFAULT_ANIO
        defaultObjArteShouldNotBeFound("anio.doesNotContain=" + DEFAULT_ANIO);

        // Get all the objArteList where anio does not contain UPDATED_ANIO
        defaultObjArteShouldBeFound("anio.doesNotContain=" + UPDATED_ANIO);
    }

    @Test
    @Transactional
    void getAllObjArtesByTituloObjIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where tituloObj equals to DEFAULT_TITULO_OBJ
        defaultObjArteShouldBeFound("tituloObj.equals=" + DEFAULT_TITULO_OBJ);

        // Get all the objArteList where tituloObj equals to UPDATED_TITULO_OBJ
        defaultObjArteShouldNotBeFound("tituloObj.equals=" + UPDATED_TITULO_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByTituloObjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where tituloObj not equals to DEFAULT_TITULO_OBJ
        defaultObjArteShouldNotBeFound("tituloObj.notEquals=" + DEFAULT_TITULO_OBJ);

        // Get all the objArteList where tituloObj not equals to UPDATED_TITULO_OBJ
        defaultObjArteShouldBeFound("tituloObj.notEquals=" + UPDATED_TITULO_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByTituloObjIsInShouldWork() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where tituloObj in DEFAULT_TITULO_OBJ or UPDATED_TITULO_OBJ
        defaultObjArteShouldBeFound("tituloObj.in=" + DEFAULT_TITULO_OBJ + "," + UPDATED_TITULO_OBJ);

        // Get all the objArteList where tituloObj equals to UPDATED_TITULO_OBJ
        defaultObjArteShouldNotBeFound("tituloObj.in=" + UPDATED_TITULO_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByTituloObjIsNullOrNotNull() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where tituloObj is not null
        defaultObjArteShouldBeFound("tituloObj.specified=true");

        // Get all the objArteList where tituloObj is null
        defaultObjArteShouldNotBeFound("tituloObj.specified=false");
    }

    @Test
    @Transactional
    void getAllObjArtesByTituloObjContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where tituloObj contains DEFAULT_TITULO_OBJ
        defaultObjArteShouldBeFound("tituloObj.contains=" + DEFAULT_TITULO_OBJ);

        // Get all the objArteList where tituloObj contains UPDATED_TITULO_OBJ
        defaultObjArteShouldNotBeFound("tituloObj.contains=" + UPDATED_TITULO_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByTituloObjNotContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where tituloObj does not contain DEFAULT_TITULO_OBJ
        defaultObjArteShouldNotBeFound("tituloObj.doesNotContain=" + DEFAULT_TITULO_OBJ);

        // Get all the objArteList where tituloObj does not contain UPDATED_TITULO_OBJ
        defaultObjArteShouldBeFound("tituloObj.doesNotContain=" + UPDATED_TITULO_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByDescObjIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where descObj equals to DEFAULT_DESC_OBJ
        defaultObjArteShouldBeFound("descObj.equals=" + DEFAULT_DESC_OBJ);

        // Get all the objArteList where descObj equals to UPDATED_DESC_OBJ
        defaultObjArteShouldNotBeFound("descObj.equals=" + UPDATED_DESC_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByDescObjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where descObj not equals to DEFAULT_DESC_OBJ
        defaultObjArteShouldNotBeFound("descObj.notEquals=" + DEFAULT_DESC_OBJ);

        // Get all the objArteList where descObj not equals to UPDATED_DESC_OBJ
        defaultObjArteShouldBeFound("descObj.notEquals=" + UPDATED_DESC_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByDescObjIsInShouldWork() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where descObj in DEFAULT_DESC_OBJ or UPDATED_DESC_OBJ
        defaultObjArteShouldBeFound("descObj.in=" + DEFAULT_DESC_OBJ + "," + UPDATED_DESC_OBJ);

        // Get all the objArteList where descObj equals to UPDATED_DESC_OBJ
        defaultObjArteShouldNotBeFound("descObj.in=" + UPDATED_DESC_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByDescObjIsNullOrNotNull() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where descObj is not null
        defaultObjArteShouldBeFound("descObj.specified=true");

        // Get all the objArteList where descObj is null
        defaultObjArteShouldNotBeFound("descObj.specified=false");
    }

    @Test
    @Transactional
    void getAllObjArtesByDescObjContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where descObj contains DEFAULT_DESC_OBJ
        defaultObjArteShouldBeFound("descObj.contains=" + DEFAULT_DESC_OBJ);

        // Get all the objArteList where descObj contains UPDATED_DESC_OBJ
        defaultObjArteShouldNotBeFound("descObj.contains=" + UPDATED_DESC_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByDescObjNotContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where descObj does not contain DEFAULT_DESC_OBJ
        defaultObjArteShouldNotBeFound("descObj.doesNotContain=" + DEFAULT_DESC_OBJ);

        // Get all the objArteList where descObj does not contain UPDATED_DESC_OBJ
        defaultObjArteShouldBeFound("descObj.doesNotContain=" + UPDATED_DESC_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByEpocaObjIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where epocaObj equals to DEFAULT_EPOCA_OBJ
        defaultObjArteShouldBeFound("epocaObj.equals=" + DEFAULT_EPOCA_OBJ);

        // Get all the objArteList where epocaObj equals to UPDATED_EPOCA_OBJ
        defaultObjArteShouldNotBeFound("epocaObj.equals=" + UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByEpocaObjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where epocaObj not equals to DEFAULT_EPOCA_OBJ
        defaultObjArteShouldNotBeFound("epocaObj.notEquals=" + DEFAULT_EPOCA_OBJ);

        // Get all the objArteList where epocaObj not equals to UPDATED_EPOCA_OBJ
        defaultObjArteShouldBeFound("epocaObj.notEquals=" + UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByEpocaObjIsInShouldWork() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where epocaObj in DEFAULT_EPOCA_OBJ or UPDATED_EPOCA_OBJ
        defaultObjArteShouldBeFound("epocaObj.in=" + DEFAULT_EPOCA_OBJ + "," + UPDATED_EPOCA_OBJ);

        // Get all the objArteList where epocaObj equals to UPDATED_EPOCA_OBJ
        defaultObjArteShouldNotBeFound("epocaObj.in=" + UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByEpocaObjIsNullOrNotNull() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where epocaObj is not null
        defaultObjArteShouldBeFound("epocaObj.specified=true");

        // Get all the objArteList where epocaObj is null
        defaultObjArteShouldNotBeFound("epocaObj.specified=false");
    }

    @Test
    @Transactional
    void getAllObjArtesByEpocaObjContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where epocaObj contains DEFAULT_EPOCA_OBJ
        defaultObjArteShouldBeFound("epocaObj.contains=" + DEFAULT_EPOCA_OBJ);

        // Get all the objArteList where epocaObj contains UPDATED_EPOCA_OBJ
        defaultObjArteShouldNotBeFound("epocaObj.contains=" + UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByEpocaObjNotContainsSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        // Get all the objArteList where epocaObj does not contain DEFAULT_EPOCA_OBJ
        defaultObjArteShouldNotBeFound("epocaObj.doesNotContain=" + DEFAULT_EPOCA_OBJ);

        // Get all the objArteList where epocaObj does not contain UPDATED_EPOCA_OBJ
        defaultObjArteShouldBeFound("epocaObj.doesNotContain=" + UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void getAllObjArtesByCollecPermaIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        CollecPerma collecPerma = CollecPermaResourceIT.createEntity(em);
        em.persist(collecPerma);
        em.flush();
        objArte.addCollecPerma(collecPerma);
        objArteRepository.saveAndFlush(objArte);
        Long collecPermaId = collecPerma.getId();

        // Get all the objArteList where collecPerma equals to collecPermaId
        defaultObjArteShouldBeFound("collecPermaId.equals=" + collecPermaId);

        // Get all the objArteList where collecPerma equals to (collecPermaId + 1)
        defaultObjArteShouldNotBeFound("collecPermaId.equals=" + (collecPermaId + 1));
    }

    @Test
    @Transactional
    void getAllObjArtesByCollecPrestaIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        CollecPresta collecPresta = CollecPrestaResourceIT.createEntity(em);
        em.persist(collecPresta);
        em.flush();
        objArte.addCollecPresta(collecPresta);
        objArteRepository.saveAndFlush(objArte);
        Long collecPrestaId = collecPresta.getId();

        // Get all the objArteList where collecPresta equals to collecPrestaId
        defaultObjArteShouldBeFound("collecPrestaId.equals=" + collecPrestaId);

        // Get all the objArteList where collecPresta equals to (collecPrestaId + 1)
        defaultObjArteShouldNotBeFound("collecPrestaId.equals=" + (collecPrestaId + 1));
    }

    @Test
    @Transactional
    void getAllObjArtesByPinturaIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        Pintura pintura = PinturaResourceIT.createEntity(em);
        em.persist(pintura);
        em.flush();
        objArte.addPintura(pintura);
        objArteRepository.saveAndFlush(objArte);
        Long pinturaId = pintura.getId();

        // Get all the objArteList where pintura equals to pinturaId
        defaultObjArteShouldBeFound("pinturaId.equals=" + pinturaId);

        // Get all the objArteList where pintura equals to (pinturaId + 1)
        defaultObjArteShouldNotBeFound("pinturaId.equals=" + (pinturaId + 1));
    }

    @Test
    @Transactional
    void getAllObjArtesByEsculturaIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        Escultura escultura = EsculturaResourceIT.createEntity(em);
        em.persist(escultura);
        em.flush();
        objArte.addEscultura(escultura);
        objArteRepository.saveAndFlush(objArte);
        Long esculturaId = escultura.getId();

        // Get all the objArteList where escultura equals to esculturaId
        defaultObjArteShouldBeFound("esculturaId.equals=" + esculturaId);

        // Get all the objArteList where escultura equals to (esculturaId + 1)
        defaultObjArteShouldNotBeFound("esculturaId.equals=" + (esculturaId + 1));
    }

    @Test
    @Transactional
    void getAllObjArtesByOtroObjIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        OtroObj otroObj = OtroObjResourceIT.createEntity(em);
        em.persist(otroObj);
        em.flush();
        objArte.addOtroObj(otroObj);
        objArteRepository.saveAndFlush(objArte);
        Long otroObjId = otroObj.getId();

        // Get all the objArteList where otroObj equals to otroObjId
        defaultObjArteShouldBeFound("otroObjId.equals=" + otroObjId);

        // Get all the objArteList where otroObj equals to (otroObjId + 1)
        defaultObjArteShouldNotBeFound("otroObjId.equals=" + (otroObjId + 1));
    }

    @Test
    @Transactional
    void getAllObjArtesByArtistaIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        Artista artista = ArtistaResourceIT.createEntity(em);
        em.persist(artista);
        em.flush();
        objArte.addArtista(artista);
        objArteRepository.saveAndFlush(objArte);
        Long artistaId = artista.getId();

        // Get all the objArteList where artista equals to artistaId
        defaultObjArteShouldBeFound("artistaId.equals=" + artistaId);

        // Get all the objArteList where artista equals to (artistaId + 1)
        defaultObjArteShouldNotBeFound("artistaId.equals=" + (artistaId + 1));
    }

    @Test
    @Transactional
    void getAllObjArtesByExhibicionIsEqualToSomething() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);
        Exhibicion exhibicion = ExhibicionResourceIT.createEntity(em);
        em.persist(exhibicion);
        em.flush();
        objArte.setExhibicion(exhibicion);
        objArteRepository.saveAndFlush(objArte);
        Long exhibicionId = exhibicion.getId();

        // Get all the objArteList where exhibicion equals to exhibicionId
        defaultObjArteShouldBeFound("exhibicionId.equals=" + exhibicionId);

        // Get all the objArteList where exhibicion equals to (exhibicionId + 1)
        defaultObjArteShouldNotBeFound("exhibicionId.equals=" + (exhibicionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultObjArteShouldBeFound(String filter) throws Exception {
        restObjArteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(objArte.getId().intValue())))
            .andExpect(jsonPath("$.[*].idObjArt").value(hasItem(DEFAULT_ID_OBJ_ART)))
            .andExpect(jsonPath("$.[*].paisCultura").value(hasItem(DEFAULT_PAIS_CULTURA)))
            .andExpect(jsonPath("$.[*].anio").value(hasItem(DEFAULT_ANIO)))
            .andExpect(jsonPath("$.[*].tituloObj").value(hasItem(DEFAULT_TITULO_OBJ)))
            .andExpect(jsonPath("$.[*].descObj").value(hasItem(DEFAULT_DESC_OBJ)))
            .andExpect(jsonPath("$.[*].epocaObj").value(hasItem(DEFAULT_EPOCA_OBJ)));

        // Check, that the count call also returns 1
        restObjArteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultObjArteShouldNotBeFound(String filter) throws Exception {
        restObjArteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restObjArteMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingObjArte() throws Exception {
        // Get the objArte
        restObjArteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewObjArte() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();

        // Update the objArte
        ObjArte updatedObjArte = objArteRepository.findById(objArte.getId()).get();
        // Disconnect from session so that the updates on updatedObjArte are not directly saved in db
        em.detach(updatedObjArte);
        updatedObjArte
            .idObjArt(UPDATED_ID_OBJ_ART)
            .paisCultura(UPDATED_PAIS_CULTURA)
            .anio(UPDATED_ANIO)
            .tituloObj(UPDATED_TITULO_OBJ)
            .descObj(UPDATED_DESC_OBJ)
            .epocaObj(UPDATED_EPOCA_OBJ);
        ObjArteDTO objArteDTO = objArteMapper.toDto(updatedObjArte);

        restObjArteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objArteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objArteDTO))
            )
            .andExpect(status().isOk());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
        ObjArte testObjArte = objArteList.get(objArteList.size() - 1);
        assertThat(testObjArte.getIdObjArt()).isEqualTo(UPDATED_ID_OBJ_ART);
        assertThat(testObjArte.getPaisCultura()).isEqualTo(UPDATED_PAIS_CULTURA);
        assertThat(testObjArte.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testObjArte.getTituloObj()).isEqualTo(UPDATED_TITULO_OBJ);
        assertThat(testObjArte.getDescObj()).isEqualTo(UPDATED_DESC_OBJ);
        assertThat(testObjArte.getEpocaObj()).isEqualTo(UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void putNonExistingObjArte() throws Exception {
        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();
        objArte.setId(count.incrementAndGet());

        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjArteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, objArteDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objArteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchObjArte() throws Exception {
        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();
        objArte.setId(count.incrementAndGet());

        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjArteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(objArteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamObjArte() throws Exception {
        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();
        objArte.setId(count.incrementAndGet());

        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjArteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(objArteDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateObjArteWithPatch() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();

        // Update the objArte using partial update
        ObjArte partialUpdatedObjArte = new ObjArte();
        partialUpdatedObjArte.setId(objArte.getId());

        partialUpdatedObjArte
            .idObjArt(UPDATED_ID_OBJ_ART)
            .paisCultura(UPDATED_PAIS_CULTURA)
            .anio(UPDATED_ANIO)
            .tituloObj(UPDATED_TITULO_OBJ)
            .epocaObj(UPDATED_EPOCA_OBJ);

        restObjArteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjArte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjArte))
            )
            .andExpect(status().isOk());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
        ObjArte testObjArte = objArteList.get(objArteList.size() - 1);
        assertThat(testObjArte.getIdObjArt()).isEqualTo(UPDATED_ID_OBJ_ART);
        assertThat(testObjArte.getPaisCultura()).isEqualTo(UPDATED_PAIS_CULTURA);
        assertThat(testObjArte.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testObjArte.getTituloObj()).isEqualTo(UPDATED_TITULO_OBJ);
        assertThat(testObjArte.getDescObj()).isEqualTo(DEFAULT_DESC_OBJ);
        assertThat(testObjArte.getEpocaObj()).isEqualTo(UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void fullUpdateObjArteWithPatch() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();

        // Update the objArte using partial update
        ObjArte partialUpdatedObjArte = new ObjArte();
        partialUpdatedObjArte.setId(objArte.getId());

        partialUpdatedObjArte
            .idObjArt(UPDATED_ID_OBJ_ART)
            .paisCultura(UPDATED_PAIS_CULTURA)
            .anio(UPDATED_ANIO)
            .tituloObj(UPDATED_TITULO_OBJ)
            .descObj(UPDATED_DESC_OBJ)
            .epocaObj(UPDATED_EPOCA_OBJ);

        restObjArteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedObjArte.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedObjArte))
            )
            .andExpect(status().isOk());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
        ObjArte testObjArte = objArteList.get(objArteList.size() - 1);
        assertThat(testObjArte.getIdObjArt()).isEqualTo(UPDATED_ID_OBJ_ART);
        assertThat(testObjArte.getPaisCultura()).isEqualTo(UPDATED_PAIS_CULTURA);
        assertThat(testObjArte.getAnio()).isEqualTo(UPDATED_ANIO);
        assertThat(testObjArte.getTituloObj()).isEqualTo(UPDATED_TITULO_OBJ);
        assertThat(testObjArte.getDescObj()).isEqualTo(UPDATED_DESC_OBJ);
        assertThat(testObjArte.getEpocaObj()).isEqualTo(UPDATED_EPOCA_OBJ);
    }

    @Test
    @Transactional
    void patchNonExistingObjArte() throws Exception {
        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();
        objArte.setId(count.incrementAndGet());

        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restObjArteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, objArteDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objArteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchObjArte() throws Exception {
        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();
        objArte.setId(count.incrementAndGet());

        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjArteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(objArteDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamObjArte() throws Exception {
        int databaseSizeBeforeUpdate = objArteRepository.findAll().size();
        objArte.setId(count.incrementAndGet());

        // Create the ObjArte
        ObjArteDTO objArteDTO = objArteMapper.toDto(objArte);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restObjArteMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(objArteDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ObjArte in the database
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteObjArte() throws Exception {
        // Initialize the database
        objArteRepository.saveAndFlush(objArte);

        int databaseSizeBeforeDelete = objArteRepository.findAll().size();

        // Delete the objArte
        restObjArteMockMvc
            .perform(delete(ENTITY_API_URL_ID, objArte.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ObjArte> objArteList = objArteRepository.findAll();
        assertThat(objArteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
