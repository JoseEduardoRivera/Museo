package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CollecPermaRepository;
import com.mycompany.myapp.service.CollecPermaQueryService;
import com.mycompany.myapp.service.CollecPermaService;
import com.mycompany.myapp.service.criteria.CollecPermaCriteria;
import com.mycompany.myapp.service.dto.CollecPermaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CollecPerma}.
 */
@RestController
@RequestMapping("/api")
public class CollecPermaResource {

    private final Logger log = LoggerFactory.getLogger(CollecPermaResource.class);

    private static final String ENTITY_NAME = "collecPerma";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CollecPermaService collecPermaService;

    private final CollecPermaRepository collecPermaRepository;

    private final CollecPermaQueryService collecPermaQueryService;

    public CollecPermaResource(
        CollecPermaService collecPermaService,
        CollecPermaRepository collecPermaRepository,
        CollecPermaQueryService collecPermaQueryService
    ) {
        this.collecPermaService = collecPermaService;
        this.collecPermaRepository = collecPermaRepository;
        this.collecPermaQueryService = collecPermaQueryService;
    }

    /**
     * {@code POST  /collec-permas} : Create a new collecPerma.
     *
     * @param collecPermaDTO the collecPermaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new collecPermaDTO, or with status {@code 400 (Bad Request)} if the collecPerma has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/collec-permas")
    public ResponseEntity<CollecPermaDTO> createCollecPerma(@Valid @RequestBody CollecPermaDTO collecPermaDTO) throws URISyntaxException {
        log.debug("REST request to save CollecPerma : {}", collecPermaDTO);
        if (collecPermaDTO.getId() != null) {
            throw new BadRequestAlertException("A new collecPerma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CollecPermaDTO result = collecPermaService.save(collecPermaDTO);
        return ResponseEntity
            .created(new URI("/api/collec-permas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /collec-permas/:id} : Updates an existing collecPerma.
     *
     * @param id the id of the collecPermaDTO to save.
     * @param collecPermaDTO the collecPermaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collecPermaDTO,
     * or with status {@code 400 (Bad Request)} if the collecPermaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the collecPermaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/collec-permas/{id}")
    public ResponseEntity<CollecPermaDTO> updateCollecPerma(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CollecPermaDTO collecPermaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CollecPerma : {}, {}", id, collecPermaDTO);
        if (collecPermaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collecPermaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collecPermaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CollecPermaDTO result = collecPermaService.save(collecPermaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collecPermaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /collec-permas/:id} : Partial updates given fields of an existing collecPerma, field will ignore if it is null
     *
     * @param id the id of the collecPermaDTO to save.
     * @param collecPermaDTO the collecPermaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated collecPermaDTO,
     * or with status {@code 400 (Bad Request)} if the collecPermaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the collecPermaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the collecPermaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/collec-permas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CollecPermaDTO> partialUpdateCollecPerma(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CollecPermaDTO collecPermaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CollecPerma partially : {}, {}", id, collecPermaDTO);
        if (collecPermaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, collecPermaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!collecPermaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CollecPermaDTO> result = collecPermaService.partialUpdate(collecPermaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, collecPermaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /collec-permas} : get all the collecPermas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of collecPermas in body.
     */
    @GetMapping("/collec-permas")
    public ResponseEntity<List<CollecPermaDTO>> getAllCollecPermas(CollecPermaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get CollecPermas by criteria: {}", criteria);
        Page<CollecPermaDTO> page = collecPermaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /collec-permas/count} : count all the collecPermas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/collec-permas/count")
    public ResponseEntity<Long> countCollecPermas(CollecPermaCriteria criteria) {
        log.debug("REST request to count CollecPermas by criteria: {}", criteria);
        return ResponseEntity.ok().body(collecPermaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /collec-permas/:id} : get the "id" collecPerma.
     *
     * @param id the id of the collecPermaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the collecPermaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/collec-permas/{id}")
    public ResponseEntity<CollecPermaDTO> getCollecPerma(@PathVariable Long id) {
        log.debug("REST request to get CollecPerma : {}", id);
        Optional<CollecPermaDTO> collecPermaDTO = collecPermaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(collecPermaDTO);
    }

    /**
     * {@code DELETE  /collec-permas/:id} : delete the "id" collecPerma.
     *
     * @param id the id of the collecPermaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/collec-permas/{id}")
    public ResponseEntity<Void> deleteCollecPerma(@PathVariable Long id) {
        log.debug("REST request to delete CollecPerma : {}", id);
        collecPermaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
