package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Artista;
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.ArtistaRepository;
import com.mycompany.myapp.service.criteria.ArtistaCriteria;
import com.mycompany.myapp.service.dto.ArtistaDTO;
import com.mycompany.myapp.service.mapper.ArtistaMapper;
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
 * Integration tests for the {@link ArtistaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ArtistaResourceIT {

    private static final String DEFAULT_NOM_ART = "AAAAAAAAAA";
    private static final String UPDATED_NOM_ART = "BBBBBBBBBB";

    private static final String DEFAULT_FECH_NAC = "AAAAAAAAAA";
    private static final String UPDATED_FECH_NAC = "BBBBBBBBBB";

    private static final String DEFAULT_FECH_DEFU = "AAAAAAAAAA";
    private static final String UPDATED_FECH_DEFU = "BBBBBBBBBB";

    private static final String DEFAULT_PAIS_ORIGEN = "AAAAAAAAAA";
    private static final String UPDATED_PAIS_ORIGEN = "BBBBBBBBBB";

    private static final String DEFAULT_ESTILO_ART = "AAAAAAAAAA";
    private static final String UPDATED_ESTILO_ART = "BBBBBBBBBB";

    private static final String DEFAULT_EPOCA = "AAAAAAAAAA";
    private static final String UPDATED_EPOCA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/artistas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private ArtistaMapper artistaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArtistaMockMvc;

    private Artista artista;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artista createEntity(EntityManager em) {
        Artista artista = new Artista()
            .nomArt(DEFAULT_NOM_ART)
            .fechNac(DEFAULT_FECH_NAC)
            .fechDefu(DEFAULT_FECH_DEFU)
            .paisOrigen(DEFAULT_PAIS_ORIGEN)
            .estiloArt(DEFAULT_ESTILO_ART)
            .epoca(DEFAULT_EPOCA);
        return artista;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artista createUpdatedEntity(EntityManager em) {
        Artista artista = new Artista()
            .nomArt(UPDATED_NOM_ART)
            .fechNac(UPDATED_FECH_NAC)
            .fechDefu(UPDATED_FECH_DEFU)
            .paisOrigen(UPDATED_PAIS_ORIGEN)
            .estiloArt(UPDATED_ESTILO_ART)
            .epoca(UPDATED_EPOCA);
        return artista;
    }

    @BeforeEach
    public void initTest() {
        artista = createEntity(em);
    }

    @Test
    @Transactional
    void createArtista() throws Exception {
        int databaseSizeBeforeCreate = artistaRepository.findAll().size();
        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);
        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isCreated());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeCreate + 1);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNomArt()).isEqualTo(DEFAULT_NOM_ART);
        assertThat(testArtista.getFechNac()).isEqualTo(DEFAULT_FECH_NAC);
        assertThat(testArtista.getFechDefu()).isEqualTo(DEFAULT_FECH_DEFU);
        assertThat(testArtista.getPaisOrigen()).isEqualTo(DEFAULT_PAIS_ORIGEN);
        assertThat(testArtista.getEstiloArt()).isEqualTo(DEFAULT_ESTILO_ART);
        assertThat(testArtista.getEpoca()).isEqualTo(DEFAULT_EPOCA);
    }

    @Test
    @Transactional
    void createArtistaWithExistingId() throws Exception {
        // Create the Artista with an existing ID
        artista.setId(1L);
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        int databaseSizeBeforeCreate = artistaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomArtIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistaRepository.findAll().size();
        // set the field null
        artista.setNomArt(null);

        // Create the Artista, which fails.
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechNacIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistaRepository.findAll().size();
        // set the field null
        artista.setFechNac(null);

        // Create the Artista, which fails.
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaisOrigenIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistaRepository.findAll().size();
        // set the field null
        artista.setPaisOrigen(null);

        // Create the Artista, which fails.
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstiloArtIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistaRepository.findAll().size();
        // set the field null
        artista.setEstiloArt(null);

        // Create the Artista, which fails.
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEpocaIsRequired() throws Exception {
        int databaseSizeBeforeTest = artistaRepository.findAll().size();
        // set the field null
        artista.setEpoca(null);

        // Create the Artista, which fails.
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        restArtistaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isBadRequest());

        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllArtistas() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomArt").value(hasItem(DEFAULT_NOM_ART)))
            .andExpect(jsonPath("$.[*].fechNac").value(hasItem(DEFAULT_FECH_NAC)))
            .andExpect(jsonPath("$.[*].fechDefu").value(hasItem(DEFAULT_FECH_DEFU)))
            .andExpect(jsonPath("$.[*].paisOrigen").value(hasItem(DEFAULT_PAIS_ORIGEN)))
            .andExpect(jsonPath("$.[*].estiloArt").value(hasItem(DEFAULT_ESTILO_ART)))
            .andExpect(jsonPath("$.[*].epoca").value(hasItem(DEFAULT_EPOCA)));
    }

    @Test
    @Transactional
    void getArtista() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get the artista
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL_ID, artista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(artista.getId().intValue()))
            .andExpect(jsonPath("$.nomArt").value(DEFAULT_NOM_ART))
            .andExpect(jsonPath("$.fechNac").value(DEFAULT_FECH_NAC))
            .andExpect(jsonPath("$.fechDefu").value(DEFAULT_FECH_DEFU))
            .andExpect(jsonPath("$.paisOrigen").value(DEFAULT_PAIS_ORIGEN))
            .andExpect(jsonPath("$.estiloArt").value(DEFAULT_ESTILO_ART))
            .andExpect(jsonPath("$.epoca").value(DEFAULT_EPOCA));
    }

    @Test
    @Transactional
    void getArtistasByIdFiltering() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        Long id = artista.getId();

        defaultArtistaShouldBeFound("id.equals=" + id);
        defaultArtistaShouldNotBeFound("id.notEquals=" + id);

        defaultArtistaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArtistaShouldNotBeFound("id.greaterThan=" + id);

        defaultArtistaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArtistaShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllArtistasByNomArtIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nomArt equals to DEFAULT_NOM_ART
        defaultArtistaShouldBeFound("nomArt.equals=" + DEFAULT_NOM_ART);

        // Get all the artistaList where nomArt equals to UPDATED_NOM_ART
        defaultArtistaShouldNotBeFound("nomArt.equals=" + UPDATED_NOM_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByNomArtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nomArt not equals to DEFAULT_NOM_ART
        defaultArtistaShouldNotBeFound("nomArt.notEquals=" + DEFAULT_NOM_ART);

        // Get all the artistaList where nomArt not equals to UPDATED_NOM_ART
        defaultArtistaShouldBeFound("nomArt.notEquals=" + UPDATED_NOM_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByNomArtIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nomArt in DEFAULT_NOM_ART or UPDATED_NOM_ART
        defaultArtistaShouldBeFound("nomArt.in=" + DEFAULT_NOM_ART + "," + UPDATED_NOM_ART);

        // Get all the artistaList where nomArt equals to UPDATED_NOM_ART
        defaultArtistaShouldNotBeFound("nomArt.in=" + UPDATED_NOM_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByNomArtIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nomArt is not null
        defaultArtistaShouldBeFound("nomArt.specified=true");

        // Get all the artistaList where nomArt is null
        defaultArtistaShouldNotBeFound("nomArt.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByNomArtContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nomArt contains DEFAULT_NOM_ART
        defaultArtistaShouldBeFound("nomArt.contains=" + DEFAULT_NOM_ART);

        // Get all the artistaList where nomArt contains UPDATED_NOM_ART
        defaultArtistaShouldNotBeFound("nomArt.contains=" + UPDATED_NOM_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByNomArtNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where nomArt does not contain DEFAULT_NOM_ART
        defaultArtistaShouldNotBeFound("nomArt.doesNotContain=" + DEFAULT_NOM_ART);

        // Get all the artistaList where nomArt does not contain UPDATED_NOM_ART
        defaultArtistaShouldBeFound("nomArt.doesNotContain=" + UPDATED_NOM_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByFechNacIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechNac equals to DEFAULT_FECH_NAC
        defaultArtistaShouldBeFound("fechNac.equals=" + DEFAULT_FECH_NAC);

        // Get all the artistaList where fechNac equals to UPDATED_FECH_NAC
        defaultArtistaShouldNotBeFound("fechNac.equals=" + UPDATED_FECH_NAC);
    }

    @Test
    @Transactional
    void getAllArtistasByFechNacIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechNac not equals to DEFAULT_FECH_NAC
        defaultArtistaShouldNotBeFound("fechNac.notEquals=" + DEFAULT_FECH_NAC);

        // Get all the artistaList where fechNac not equals to UPDATED_FECH_NAC
        defaultArtistaShouldBeFound("fechNac.notEquals=" + UPDATED_FECH_NAC);
    }

    @Test
    @Transactional
    void getAllArtistasByFechNacIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechNac in DEFAULT_FECH_NAC or UPDATED_FECH_NAC
        defaultArtistaShouldBeFound("fechNac.in=" + DEFAULT_FECH_NAC + "," + UPDATED_FECH_NAC);

        // Get all the artistaList where fechNac equals to UPDATED_FECH_NAC
        defaultArtistaShouldNotBeFound("fechNac.in=" + UPDATED_FECH_NAC);
    }

    @Test
    @Transactional
    void getAllArtistasByFechNacIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechNac is not null
        defaultArtistaShouldBeFound("fechNac.specified=true");

        // Get all the artistaList where fechNac is null
        defaultArtistaShouldNotBeFound("fechNac.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByFechNacContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechNac contains DEFAULT_FECH_NAC
        defaultArtistaShouldBeFound("fechNac.contains=" + DEFAULT_FECH_NAC);

        // Get all the artistaList where fechNac contains UPDATED_FECH_NAC
        defaultArtistaShouldNotBeFound("fechNac.contains=" + UPDATED_FECH_NAC);
    }

    @Test
    @Transactional
    void getAllArtistasByFechNacNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechNac does not contain DEFAULT_FECH_NAC
        defaultArtistaShouldNotBeFound("fechNac.doesNotContain=" + DEFAULT_FECH_NAC);

        // Get all the artistaList where fechNac does not contain UPDATED_FECH_NAC
        defaultArtistaShouldBeFound("fechNac.doesNotContain=" + UPDATED_FECH_NAC);
    }

    @Test
    @Transactional
    void getAllArtistasByFechDefuIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechDefu equals to DEFAULT_FECH_DEFU
        defaultArtistaShouldBeFound("fechDefu.equals=" + DEFAULT_FECH_DEFU);

        // Get all the artistaList where fechDefu equals to UPDATED_FECH_DEFU
        defaultArtistaShouldNotBeFound("fechDefu.equals=" + UPDATED_FECH_DEFU);
    }

    @Test
    @Transactional
    void getAllArtistasByFechDefuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechDefu not equals to DEFAULT_FECH_DEFU
        defaultArtistaShouldNotBeFound("fechDefu.notEquals=" + DEFAULT_FECH_DEFU);

        // Get all the artistaList where fechDefu not equals to UPDATED_FECH_DEFU
        defaultArtistaShouldBeFound("fechDefu.notEquals=" + UPDATED_FECH_DEFU);
    }

    @Test
    @Transactional
    void getAllArtistasByFechDefuIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechDefu in DEFAULT_FECH_DEFU or UPDATED_FECH_DEFU
        defaultArtistaShouldBeFound("fechDefu.in=" + DEFAULT_FECH_DEFU + "," + UPDATED_FECH_DEFU);

        // Get all the artistaList where fechDefu equals to UPDATED_FECH_DEFU
        defaultArtistaShouldNotBeFound("fechDefu.in=" + UPDATED_FECH_DEFU);
    }

    @Test
    @Transactional
    void getAllArtistasByFechDefuIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechDefu is not null
        defaultArtistaShouldBeFound("fechDefu.specified=true");

        // Get all the artistaList where fechDefu is null
        defaultArtistaShouldNotBeFound("fechDefu.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByFechDefuContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechDefu contains DEFAULT_FECH_DEFU
        defaultArtistaShouldBeFound("fechDefu.contains=" + DEFAULT_FECH_DEFU);

        // Get all the artistaList where fechDefu contains UPDATED_FECH_DEFU
        defaultArtistaShouldNotBeFound("fechDefu.contains=" + UPDATED_FECH_DEFU);
    }

    @Test
    @Transactional
    void getAllArtistasByFechDefuNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where fechDefu does not contain DEFAULT_FECH_DEFU
        defaultArtistaShouldNotBeFound("fechDefu.doesNotContain=" + DEFAULT_FECH_DEFU);

        // Get all the artistaList where fechDefu does not contain UPDATED_FECH_DEFU
        defaultArtistaShouldBeFound("fechDefu.doesNotContain=" + UPDATED_FECH_DEFU);
    }

    @Test
    @Transactional
    void getAllArtistasByPaisOrigenIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where paisOrigen equals to DEFAULT_PAIS_ORIGEN
        defaultArtistaShouldBeFound("paisOrigen.equals=" + DEFAULT_PAIS_ORIGEN);

        // Get all the artistaList where paisOrigen equals to UPDATED_PAIS_ORIGEN
        defaultArtistaShouldNotBeFound("paisOrigen.equals=" + UPDATED_PAIS_ORIGEN);
    }

    @Test
    @Transactional
    void getAllArtistasByPaisOrigenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where paisOrigen not equals to DEFAULT_PAIS_ORIGEN
        defaultArtistaShouldNotBeFound("paisOrigen.notEquals=" + DEFAULT_PAIS_ORIGEN);

        // Get all the artistaList where paisOrigen not equals to UPDATED_PAIS_ORIGEN
        defaultArtistaShouldBeFound("paisOrigen.notEquals=" + UPDATED_PAIS_ORIGEN);
    }

    @Test
    @Transactional
    void getAllArtistasByPaisOrigenIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where paisOrigen in DEFAULT_PAIS_ORIGEN or UPDATED_PAIS_ORIGEN
        defaultArtistaShouldBeFound("paisOrigen.in=" + DEFAULT_PAIS_ORIGEN + "," + UPDATED_PAIS_ORIGEN);

        // Get all the artistaList where paisOrigen equals to UPDATED_PAIS_ORIGEN
        defaultArtistaShouldNotBeFound("paisOrigen.in=" + UPDATED_PAIS_ORIGEN);
    }

    @Test
    @Transactional
    void getAllArtistasByPaisOrigenIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where paisOrigen is not null
        defaultArtistaShouldBeFound("paisOrigen.specified=true");

        // Get all the artistaList where paisOrigen is null
        defaultArtistaShouldNotBeFound("paisOrigen.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByPaisOrigenContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where paisOrigen contains DEFAULT_PAIS_ORIGEN
        defaultArtistaShouldBeFound("paisOrigen.contains=" + DEFAULT_PAIS_ORIGEN);

        // Get all the artistaList where paisOrigen contains UPDATED_PAIS_ORIGEN
        defaultArtistaShouldNotBeFound("paisOrigen.contains=" + UPDATED_PAIS_ORIGEN);
    }

    @Test
    @Transactional
    void getAllArtistasByPaisOrigenNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where paisOrigen does not contain DEFAULT_PAIS_ORIGEN
        defaultArtistaShouldNotBeFound("paisOrigen.doesNotContain=" + DEFAULT_PAIS_ORIGEN);

        // Get all the artistaList where paisOrigen does not contain UPDATED_PAIS_ORIGEN
        defaultArtistaShouldBeFound("paisOrigen.doesNotContain=" + UPDATED_PAIS_ORIGEN);
    }

    @Test
    @Transactional
    void getAllArtistasByEstiloArtIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where estiloArt equals to DEFAULT_ESTILO_ART
        defaultArtistaShouldBeFound("estiloArt.equals=" + DEFAULT_ESTILO_ART);

        // Get all the artistaList where estiloArt equals to UPDATED_ESTILO_ART
        defaultArtistaShouldNotBeFound("estiloArt.equals=" + UPDATED_ESTILO_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByEstiloArtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where estiloArt not equals to DEFAULT_ESTILO_ART
        defaultArtistaShouldNotBeFound("estiloArt.notEquals=" + DEFAULT_ESTILO_ART);

        // Get all the artistaList where estiloArt not equals to UPDATED_ESTILO_ART
        defaultArtistaShouldBeFound("estiloArt.notEquals=" + UPDATED_ESTILO_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByEstiloArtIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where estiloArt in DEFAULT_ESTILO_ART or UPDATED_ESTILO_ART
        defaultArtistaShouldBeFound("estiloArt.in=" + DEFAULT_ESTILO_ART + "," + UPDATED_ESTILO_ART);

        // Get all the artistaList where estiloArt equals to UPDATED_ESTILO_ART
        defaultArtistaShouldNotBeFound("estiloArt.in=" + UPDATED_ESTILO_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByEstiloArtIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where estiloArt is not null
        defaultArtistaShouldBeFound("estiloArt.specified=true");

        // Get all the artistaList where estiloArt is null
        defaultArtistaShouldNotBeFound("estiloArt.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByEstiloArtContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where estiloArt contains DEFAULT_ESTILO_ART
        defaultArtistaShouldBeFound("estiloArt.contains=" + DEFAULT_ESTILO_ART);

        // Get all the artistaList where estiloArt contains UPDATED_ESTILO_ART
        defaultArtistaShouldNotBeFound("estiloArt.contains=" + UPDATED_ESTILO_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByEstiloArtNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where estiloArt does not contain DEFAULT_ESTILO_ART
        defaultArtistaShouldNotBeFound("estiloArt.doesNotContain=" + DEFAULT_ESTILO_ART);

        // Get all the artistaList where estiloArt does not contain UPDATED_ESTILO_ART
        defaultArtistaShouldBeFound("estiloArt.doesNotContain=" + UPDATED_ESTILO_ART);
    }

    @Test
    @Transactional
    void getAllArtistasByEpocaIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where epoca equals to DEFAULT_EPOCA
        defaultArtistaShouldBeFound("epoca.equals=" + DEFAULT_EPOCA);

        // Get all the artistaList where epoca equals to UPDATED_EPOCA
        defaultArtistaShouldNotBeFound("epoca.equals=" + UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void getAllArtistasByEpocaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where epoca not equals to DEFAULT_EPOCA
        defaultArtistaShouldNotBeFound("epoca.notEquals=" + DEFAULT_EPOCA);

        // Get all the artistaList where epoca not equals to UPDATED_EPOCA
        defaultArtistaShouldBeFound("epoca.notEquals=" + UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void getAllArtistasByEpocaIsInShouldWork() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where epoca in DEFAULT_EPOCA or UPDATED_EPOCA
        defaultArtistaShouldBeFound("epoca.in=" + DEFAULT_EPOCA + "," + UPDATED_EPOCA);

        // Get all the artistaList where epoca equals to UPDATED_EPOCA
        defaultArtistaShouldNotBeFound("epoca.in=" + UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void getAllArtistasByEpocaIsNullOrNotNull() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where epoca is not null
        defaultArtistaShouldBeFound("epoca.specified=true");

        // Get all the artistaList where epoca is null
        defaultArtistaShouldNotBeFound("epoca.specified=false");
    }

    @Test
    @Transactional
    void getAllArtistasByEpocaContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where epoca contains DEFAULT_EPOCA
        defaultArtistaShouldBeFound("epoca.contains=" + DEFAULT_EPOCA);

        // Get all the artistaList where epoca contains UPDATED_EPOCA
        defaultArtistaShouldNotBeFound("epoca.contains=" + UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void getAllArtistasByEpocaNotContainsSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        // Get all the artistaList where epoca does not contain DEFAULT_EPOCA
        defaultArtistaShouldNotBeFound("epoca.doesNotContain=" + DEFAULT_EPOCA);

        // Get all the artistaList where epoca does not contain UPDATED_EPOCA
        defaultArtistaShouldBeFound("epoca.doesNotContain=" + UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void getAllArtistasByObjArteIsEqualToSomething() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);
        ObjArte objArte = ObjArteResourceIT.createEntity(em);
        em.persist(objArte);
        em.flush();
        artista.addObjArte(objArte);
        artistaRepository.saveAndFlush(artista);
        Long objArteId = objArte.getId();

        // Get all the artistaList where objArte equals to objArteId
        defaultArtistaShouldBeFound("objArteId.equals=" + objArteId);

        // Get all the artistaList where objArte equals to (objArteId + 1)
        defaultArtistaShouldNotBeFound("objArteId.equals=" + (objArteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArtistaShouldBeFound(String filter) throws Exception {
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(artista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomArt").value(hasItem(DEFAULT_NOM_ART)))
            .andExpect(jsonPath("$.[*].fechNac").value(hasItem(DEFAULT_FECH_NAC)))
            .andExpect(jsonPath("$.[*].fechDefu").value(hasItem(DEFAULT_FECH_DEFU)))
            .andExpect(jsonPath("$.[*].paisOrigen").value(hasItem(DEFAULT_PAIS_ORIGEN)))
            .andExpect(jsonPath("$.[*].estiloArt").value(hasItem(DEFAULT_ESTILO_ART)))
            .andExpect(jsonPath("$.[*].epoca").value(hasItem(DEFAULT_EPOCA)));

        // Check, that the count call also returns 1
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArtistaShouldNotBeFound(String filter) throws Exception {
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArtistaMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingArtista() throws Exception {
        // Get the artista
        restArtistaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewArtista() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();

        // Update the artista
        Artista updatedArtista = artistaRepository.findById(artista.getId()).get();
        // Disconnect from session so that the updates on updatedArtista are not directly saved in db
        em.detach(updatedArtista);
        updatedArtista
            .nomArt(UPDATED_NOM_ART)
            .fechNac(UPDATED_FECH_NAC)
            .fechDefu(UPDATED_FECH_DEFU)
            .paisOrigen(UPDATED_PAIS_ORIGEN)
            .estiloArt(UPDATED_ESTILO_ART)
            .epoca(UPDATED_EPOCA);
        ArtistaDTO artistaDTO = artistaMapper.toDto(updatedArtista);

        restArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNomArt()).isEqualTo(UPDATED_NOM_ART);
        assertThat(testArtista.getFechNac()).isEqualTo(UPDATED_FECH_NAC);
        assertThat(testArtista.getFechDefu()).isEqualTo(UPDATED_FECH_DEFU);
        assertThat(testArtista.getPaisOrigen()).isEqualTo(UPDATED_PAIS_ORIGEN);
        assertThat(testArtista.getEstiloArt()).isEqualTo(UPDATED_ESTILO_ART);
        assertThat(testArtista.getEpoca()).isEqualTo(UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void putNonExistingArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, artistaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(artistaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateArtistaWithPatch() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();

        // Update the artista using partial update
        Artista partialUpdatedArtista = new Artista();
        partialUpdatedArtista.setId(artista.getId());

        partialUpdatedArtista.nomArt(UPDATED_NOM_ART).fechDefu(UPDATED_FECH_DEFU).paisOrigen(UPDATED_PAIS_ORIGEN);

        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtista))
            )
            .andExpect(status().isOk());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNomArt()).isEqualTo(UPDATED_NOM_ART);
        assertThat(testArtista.getFechNac()).isEqualTo(DEFAULT_FECH_NAC);
        assertThat(testArtista.getFechDefu()).isEqualTo(UPDATED_FECH_DEFU);
        assertThat(testArtista.getPaisOrigen()).isEqualTo(UPDATED_PAIS_ORIGEN);
        assertThat(testArtista.getEstiloArt()).isEqualTo(DEFAULT_ESTILO_ART);
        assertThat(testArtista.getEpoca()).isEqualTo(DEFAULT_EPOCA);
    }

    @Test
    @Transactional
    void fullUpdateArtistaWithPatch() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();

        // Update the artista using partial update
        Artista partialUpdatedArtista = new Artista();
        partialUpdatedArtista.setId(artista.getId());

        partialUpdatedArtista
            .nomArt(UPDATED_NOM_ART)
            .fechNac(UPDATED_FECH_NAC)
            .fechDefu(UPDATED_FECH_DEFU)
            .paisOrigen(UPDATED_PAIS_ORIGEN)
            .estiloArt(UPDATED_ESTILO_ART)
            .epoca(UPDATED_EPOCA);

        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedArtista.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedArtista))
            )
            .andExpect(status().isOk());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
        Artista testArtista = artistaList.get(artistaList.size() - 1);
        assertThat(testArtista.getNomArt()).isEqualTo(UPDATED_NOM_ART);
        assertThat(testArtista.getFechNac()).isEqualTo(UPDATED_FECH_NAC);
        assertThat(testArtista.getFechDefu()).isEqualTo(UPDATED_FECH_DEFU);
        assertThat(testArtista.getPaisOrigen()).isEqualTo(UPDATED_PAIS_ORIGEN);
        assertThat(testArtista.getEstiloArt()).isEqualTo(UPDATED_ESTILO_ART);
        assertThat(testArtista.getEpoca()).isEqualTo(UPDATED_EPOCA);
    }

    @Test
    @Transactional
    void patchNonExistingArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, artistaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamArtista() throws Exception {
        int databaseSizeBeforeUpdate = artistaRepository.findAll().size();
        artista.setId(count.incrementAndGet());

        // Create the Artista
        ArtistaDTO artistaDTO = artistaMapper.toDto(artista);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restArtistaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(artistaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Artista in the database
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteArtista() throws Exception {
        // Initialize the database
        artistaRepository.saveAndFlush(artista);

        int databaseSizeBeforeDelete = artistaRepository.findAll().size();

        // Delete the artista
        restArtistaMockMvc
            .perform(delete(ENTITY_API_URL_ID, artista.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Artista> artistaList = artistaRepository.findAll();
        assertThat(artistaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
