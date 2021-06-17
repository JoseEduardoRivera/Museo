package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.EsculturaRepository;
import com.mycompany.myapp.service.EsculturaQueryService;
import com.mycompany.myapp.service.EsculturaService;
import com.mycompany.myapp.service.criteria.EsculturaCriteria;
import com.mycompany.myapp.service.dto.EsculturaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Escultura}.
 */
@RestController
@RequestMapping("/api")
public class EsculturaResource {

    private final Logger log = LoggerFactory.getLogger(EsculturaResource.class);

    private static final String ENTITY_NAME = "escultura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EsculturaService esculturaService;

    private final EsculturaRepository esculturaRepository;

    private final EsculturaQueryService esculturaQueryService;

    public EsculturaResource(
        EsculturaService esculturaService,
        EsculturaRepository esculturaRepository,
        EsculturaQueryService esculturaQueryService
    ) {
        this.esculturaService = esculturaService;
        this.esculturaRepository = esculturaRepository;
        this.esculturaQueryService = esculturaQueryService;
    }

    /**
     * {@code POST  /esculturas} : Create a new escultura.
     *
     * @param esculturaDTO the esculturaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new esculturaDTO, or with status {@code 400 (Bad Request)} if the escultura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/esculturas")
    public ResponseEntity<EsculturaDTO> createEscultura(@Valid @RequestBody EsculturaDTO esculturaDTO) throws URISyntaxException {
        log.debug("REST request to save Escultura : {}", esculturaDTO);
        if (esculturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new escultura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EsculturaDTO result = esculturaService.save(esculturaDTO);
        return ResponseEntity
            .created(new URI("/api/esculturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /esculturas/:id} : Updates an existing escultura.
     *
     * @param id the id of the esculturaDTO to save.
     * @param esculturaDTO the esculturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esculturaDTO,
     * or with status {@code 400 (Bad Request)} if the esculturaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the esculturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/esculturas/{id}")
    public ResponseEntity<EsculturaDTO> updateEscultura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EsculturaDTO esculturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Escultura : {}, {}", id, esculturaDTO);
        if (esculturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, esculturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!esculturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EsculturaDTO result = esculturaService.save(esculturaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esculturaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /esculturas/:id} : Partial updates given fields of an existing escultura, field will ignore if it is null
     *
     * @param id the id of the esculturaDTO to save.
     * @param esculturaDTO the esculturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated esculturaDTO,
     * or with status {@code 400 (Bad Request)} if the esculturaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the esculturaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the esculturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/esculturas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EsculturaDTO> partialUpdateEscultura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EsculturaDTO esculturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Escultura partially : {}, {}", id, esculturaDTO);
        if (esculturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, esculturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!esculturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EsculturaDTO> result = esculturaService.partialUpdate(esculturaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, esculturaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /esculturas} : get all the esculturas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of esculturas in body.
     */
    @GetMapping("/esculturas")
    public ResponseEntity<List<EsculturaDTO>> getAllEsculturas(EsculturaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Esculturas by criteria: {}", criteria);
        Page<EsculturaDTO> page = esculturaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /esculturas/count} : count all the esculturas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/esculturas/count")
    public ResponseEntity<Long> countEsculturas(EsculturaCriteria criteria) {
        log.debug("REST request to count Esculturas by criteria: {}", criteria);
        return ResponseEntity.ok().body(esculturaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /esculturas/:id} : get the "id" escultura.
     *
     * @param id the id of the esculturaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the esculturaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/esculturas/{id}")
    public ResponseEntity<EsculturaDTO> getEscultura(@PathVariable Long id) {
        log.debug("REST request to get Escultura : {}", id);
        Optional<EsculturaDTO> esculturaDTO = esculturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esculturaDTO);
    }

    /**
     * {@code DELETE  /esculturas/:id} : delete the "id" escultura.
     *
     * @param id the id of the esculturaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/esculturas/{id}")
    public ResponseEntity<Void> deleteEscultura(@PathVariable Long id) {
        log.debug("REST request to delete Escultura : {}", id);
        esculturaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
