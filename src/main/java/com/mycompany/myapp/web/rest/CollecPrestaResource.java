package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CollecPrestaRepository;
import com.mycompany.myapp.service.CollecPrestaQueryService;
import com.mycompany.myapp.service.CollecPrestaService;
import com.mycompany.myapp.service.criteria.CollecPrestaCriteria;
import com.mycompany.myapp.service.dto.CollecPrestaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CollecPresta}.
 */
@RestController
@RequestMapping("/api")
public class CollecPrestaResource {

    private final Logger log = LoggerFactory.getLogger(CollecPrestaResource.class);

    private static final String ENTITY_NAME = "collecPresta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollecPrestaService collecPrestaService;

    private final CollecPrestaRepository collecPrestaRepository;

    private final CollecPrestaQueryService collecPrestaQueryService;

    public CollecPrestaResource(
        CollecPrestaService collecPrestaService,
        CollecPrestaRepository collecPrestaRepository,
        CollecPrestaQueryService collecPrestaQueryService
    ) {
        this.collecPrestaService = collecPrestaService;
        this.collecPrestaRepository = collecPrestaRepository;
        this.collecPrestaQueryService = collecPrestaQueryService;
    }

    /**
     * {@code POST  /collec-prestas} : Create a new collecPresta.
     *
     * @param collecPrestaDTO the collecPrestaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collecPrestaDTO, or with status {@code 400 (Bad Request)} if the collecPresta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collec-prestas")
    public ResponseEntity<CollecPrestaDTO> createCollecPresta(@Valid @RequestBody CollecPrestaDTO collecPrestaDTO)
        throws URISyntaxException {
        log.debug("REST request to save CollecPresta : {}", collecPrestaDTO);
        if (collecPrestaDTO.getId() != null) {
            throw new BadRequestAlertException("A new collecPresta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollecPrestaDTO result = collecPrestaService.save(collecPrestaDTO);
        return ResponseEntity
            .created(new URI("/api/collec-prestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collec-prestas/:id} : Updates an existing collecPresta.
     *
     * @param id the id of the collecPrestaDTO to save.
     * @param collecPrestaDTO the collecPrestaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collecPrestaDTO,
     * or with status {@code 400 (Bad Request)} if the collecPrestaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collecPrestaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collec-prestas/{id}")
    public ResponseEntity<CollecPrestaDTO> updateCollecPresta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CollecPrestaDTO collecPrestaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CollecPresta : {}, {}", id, collecPrestaDTO);
        if (collecPrestaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collecPrestaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collecPrestaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollecPrestaDTO result = collecPrestaService.save(collecPrestaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collecPrestaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /collec-prestas/:id} : Partial updates given fields of an existing collecPresta, field will ignore if it is null
     *
     * @param id the id of the collecPrestaDTO to save.
     * @param collecPrestaDTO the collecPrestaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collecPrestaDTO,
     * or with status {@code 400 (Bad Request)} if the collecPrestaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the collecPrestaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the collecPrestaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collec-prestas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CollecPrestaDTO> partialUpdateCollecPresta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CollecPrestaDTO collecPrestaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CollecPresta partially : {}, {}", id, collecPrestaDTO);
        if (collecPrestaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collecPrestaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collecPrestaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollecPrestaDTO> result = collecPrestaService.partialUpdate(collecPrestaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collecPrestaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /collec-prestas} : get all the collecPrestas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collecPrestas in body.
     */
    @GetMapping("/collec-prestas")
    public ResponseEntity<List<CollecPrestaDTO>> getAllCollecPrestas(CollecPrestaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CollecPrestas by criteria: {}", criteria);
        Page<CollecPrestaDTO> page = collecPrestaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collec-prestas/count} : count all the collecPrestas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/collec-prestas/count")
    public ResponseEntity<Long> countCollecPrestas(CollecPrestaCriteria criteria) {
        log.debug("REST request to count CollecPrestas by criteria: {}", criteria);
        return ResponseEntity.ok().body(collecPrestaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /collec-prestas/:id} : get the "id" collecPresta.
     *
     * @param id the id of the collecPrestaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collecPrestaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collec-prestas/{id}")
    public ResponseEntity<CollecPrestaDTO> getCollecPresta(@PathVariable Long id) {
        log.debug("REST request to get CollecPresta : {}", id);
        Optional<CollecPrestaDTO> collecPrestaDTO = collecPrestaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collecPrestaDTO);
    }

    /**
     * {@code DELETE  /collec-prestas/:id} : delete the "id" collecPresta.
     *
     * @param id the id of the collecPrestaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collec-prestas/{id}")
    public ResponseEntity<Void> deleteCollecPresta(@PathVariable Long id) {
        log.debug("REST request to delete CollecPresta : {}", id);
        collecPrestaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
