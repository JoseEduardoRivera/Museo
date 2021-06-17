package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ArtistaRepository;
import com.mycompany.myapp.service.ArtistaQueryService;
import com.mycompany.myapp.service.ArtistaService;
import com.mycompany.myapp.service.criteria.ArtistaCriteria;
import com.mycompany.myapp.service.dto.ArtistaDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Artista}.
 */
@RestController
@RequestMapping("/api")
public class ArtistaResource {

    private final Logger log = LoggerFactory.getLogger(ArtistaResource.class);

    private static final String ENTITY_NAME = "artista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArtistaService artistaService;

    private final ArtistaRepository artistaRepository;

    private final ArtistaQueryService artistaQueryService;

    public ArtistaResource(ArtistaService artistaService, ArtistaRepository artistaRepository, ArtistaQueryService artistaQueryService) {
        this.artistaService = artistaService;
        this.artistaRepository = artistaRepository;
        this.artistaQueryService = artistaQueryService;
    }

    /**
     * {@code POST  /artistas} : Create a new artista.
     *
     * @param artistaDTO the artistaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new artistaDTO, or with status {@code 400 (Bad Request)} if the artista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/artistas")
    public ResponseEntity<ArtistaDTO> createArtista(@Valid @RequestBody ArtistaDTO artistaDTO) throws URISyntaxException {
        log.debug("REST request to save Artista : {}", artistaDTO);
        if (artistaDTO.getId() != null) {
            throw new BadRequestAlertException("A new artista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ArtistaDTO result = artistaService.save(artistaDTO);
        return ResponseEntity
            .created(new URI("/api/artistas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /artistas/:id} : Updates an existing artista.
     *
     * @param id the id of the artistaDTO to save.
     * @param artistaDTO the artistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistaDTO,
     * or with status {@code 400 (Bad Request)} if the artistaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the artistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/artistas/{id}")
    public ResponseEntity<ArtistaDTO> updateArtista(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ArtistaDTO artistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Artista : {}, {}", id, artistaDTO);
        if (artistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ArtistaDTO result = artistaService.save(artistaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artistaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /artistas/:id} : Partial updates given fields of an existing artista, field will ignore if it is null
     *
     * @param id the id of the artistaDTO to save.
     * @param artistaDTO the artistaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated artistaDTO,
     * or with status {@code 400 (Bad Request)} if the artistaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the artistaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the artistaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/artistas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ArtistaDTO> partialUpdateArtista(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ArtistaDTO artistaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Artista partially : {}, {}", id, artistaDTO);
        if (artistaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, artistaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!artistaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ArtistaDTO> result = artistaService.partialUpdate(artistaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, artistaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /artistas} : get all the artistas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of artistas in body.
     */
    @GetMapping("/artistas")
    public ResponseEntity<List<ArtistaDTO>> getAllArtistas(ArtistaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Artistas by criteria: {}", criteria);
        Page<ArtistaDTO> page = artistaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /artistas/count} : count all the artistas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/artistas/count")
    public ResponseEntity<Long> countArtistas(ArtistaCriteria criteria) {
        log.debug("REST request to count Artistas by criteria: {}", criteria);
        return ResponseEntity.ok().body(artistaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /artistas/:id} : get the "id" artista.
     *
     * @param id the id of the artistaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the artistaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/artistas/{id}")
    public ResponseEntity<ArtistaDTO> getArtista(@PathVariable Long id) {
        log.debug("REST request to get Artista : {}", id);
        Optional<ArtistaDTO> artistaDTO = artistaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(artistaDTO);
    }

    /**
     * {@code DELETE  /artistas/:id} : delete the "id" artista.
     *
     * @param id the id of the artistaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/artistas/{id}")
    public ResponseEntity<Void> deleteArtista(@PathVariable Long id) {
        log.debug("REST request to delete Artista : {}", id);
        artistaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
