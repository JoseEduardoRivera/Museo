package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.PinturaRepository;
import com.mycompany.myapp.service.PinturaQueryService;
import com.mycompany.myapp.service.PinturaService;
import com.mycompany.myapp.service.criteria.PinturaCriteria;
import com.mycompany.myapp.service.dto.PinturaDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Pintura}.
 */
@RestController
@RequestMapping("/api")
public class PinturaResource {

    private final Logger log = LoggerFactory.getLogger(PinturaResource.class);

    private static final String ENTITY_NAME = "pintura";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PinturaService pinturaService;

    private final PinturaRepository pinturaRepository;

    private final PinturaQueryService pinturaQueryService;

    public PinturaResource(PinturaService pinturaService, PinturaRepository pinturaRepository, PinturaQueryService pinturaQueryService) {
        this.pinturaService = pinturaService;
        this.pinturaRepository = pinturaRepository;
        this.pinturaQueryService = pinturaQueryService;
    }

    /**
     * {@code POST  /pinturas} : Create a new pintura.
     *
     * @param pinturaDTO the pinturaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new pinturaDTO, or with status {@code 400 (Bad Request)} if the pintura has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pinturas")
    public ResponseEntity<PinturaDTO> createPintura(@Valid @RequestBody PinturaDTO pinturaDTO) throws URISyntaxException {
        log.debug("REST request to save Pintura : {}", pinturaDTO);
        if (pinturaDTO.getId() != null) {
            throw new BadRequestAlertException("A new pintura cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PinturaDTO result = pinturaService.save(pinturaDTO);
        return ResponseEntity
            .created(new URI("/api/pinturas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pinturas/:id} : Updates an existing pintura.
     *
     * @param id the id of the pinturaDTO to save.
     * @param pinturaDTO the pinturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pinturaDTO,
     * or with status {@code 400 (Bad Request)} if the pinturaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the pinturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pinturas/{id}")
    public ResponseEntity<PinturaDTO> updatePintura(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PinturaDTO pinturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Pintura : {}, {}", id, pinturaDTO);
        if (pinturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pinturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pinturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PinturaDTO result = pinturaService.save(pinturaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pinturaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /pinturas/:id} : Partial updates given fields of an existing pintura, field will ignore if it is null
     *
     * @param id the id of the pinturaDTO to save.
     * @param pinturaDTO the pinturaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated pinturaDTO,
     * or with status {@code 400 (Bad Request)} if the pinturaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the pinturaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the pinturaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/pinturas/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<PinturaDTO> partialUpdatePintura(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PinturaDTO pinturaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Pintura partially : {}, {}", id, pinturaDTO);
        if (pinturaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, pinturaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!pinturaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PinturaDTO> result = pinturaService.partialUpdate(pinturaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, pinturaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /pinturas} : get all the pinturas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pinturas in body.
     */
    @GetMapping("/pinturas")
    public ResponseEntity<List<PinturaDTO>> getAllPinturas(PinturaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Pinturas by criteria: {}", criteria);
        Page<PinturaDTO> page = pinturaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pinturas/count} : count all the pinturas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/pinturas/count")
    public ResponseEntity<Long> countPinturas(PinturaCriteria criteria) {
        log.debug("REST request to count Pinturas by criteria: {}", criteria);
        return ResponseEntity.ok().body(pinturaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /pinturas/:id} : get the "id" pintura.
     *
     * @param id the id of the pinturaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the pinturaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pinturas/{id}")
    public ResponseEntity<PinturaDTO> getPintura(@PathVariable Long id) {
        log.debug("REST request to get Pintura : {}", id);
        Optional<PinturaDTO> pinturaDTO = pinturaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pinturaDTO);
    }

    /**
     * {@code DELETE  /pinturas/:id} : delete the "id" pintura.
     *
     * @param id the id of the pinturaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pinturas/{id}")
    public ResponseEntity<Void> deletePintura(@PathVariable Long id) {
        log.debug("REST request to delete Pintura : {}", id);
        pinturaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
