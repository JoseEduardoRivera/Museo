package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ExhibicionRepository;
import com.mycompany.myapp.service.ExhibicionQueryService;
import com.mycompany.myapp.service.ExhibicionService;
import com.mycompany.myapp.service.criteria.ExhibicionCriteria;
import com.mycompany.myapp.service.dto.ExhibicionDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Exhibicion}.
 */
@RestController
@RequestMapping("/api")
public class ExhibicionResource {

    private final Logger log = LoggerFactory.getLogger(ExhibicionResource.class);

    private static final String ENTITY_NAME = "exhibicion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ExhibicionService exhibicionService;

    private final ExhibicionRepository exhibicionRepository;

    private final ExhibicionQueryService exhibicionQueryService;

    public ExhibicionResource(
        ExhibicionService exhibicionService,
        ExhibicionRepository exhibicionRepository,
        ExhibicionQueryService exhibicionQueryService
    ) {
        this.exhibicionService = exhibicionService;
        this.exhibicionRepository = exhibicionRepository;
        this.exhibicionQueryService = exhibicionQueryService;
    }

    /**
     * {@code POST  /exhibicions} : Create a new exhibicion.
     *
     * @param exhibicionDTO the exhibicionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new exhibicionDTO, or with status {@code 400 (Bad Request)} if the exhibicion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/exhibicions")
    public ResponseEntity<ExhibicionDTO> createExhibicion(@Valid @RequestBody ExhibicionDTO exhibicionDTO) throws URISyntaxException {
        log.debug("REST request to save Exhibicion : {}", exhibicionDTO);
        if (exhibicionDTO.getId() != null) {
            throw new BadRequestAlertException("A new exhibicion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExhibicionDTO result = exhibicionService.save(exhibicionDTO);
        return ResponseEntity
            .created(new URI("/api/exhibicions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /exhibicions/:id} : Updates an existing exhibicion.
     *
     * @param id the id of the exhibicionDTO to save.
     * @param exhibicionDTO the exhibicionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exhibicionDTO,
     * or with status {@code 400 (Bad Request)} if the exhibicionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the exhibicionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/exhibicions/{id}")
    public ResponseEntity<ExhibicionDTO> updateExhibicion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ExhibicionDTO exhibicionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Exhibicion : {}, {}", id, exhibicionDTO);
        if (exhibicionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exhibicionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exhibicionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ExhibicionDTO result = exhibicionService.save(exhibicionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exhibicionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /exhibicions/:id} : Partial updates given fields of an existing exhibicion, field will ignore if it is null
     *
     * @param id the id of the exhibicionDTO to save.
     * @param exhibicionDTO the exhibicionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated exhibicionDTO,
     * or with status {@code 400 (Bad Request)} if the exhibicionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the exhibicionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the exhibicionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/exhibicions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ExhibicionDTO> partialUpdateExhibicion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ExhibicionDTO exhibicionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Exhibicion partially : {}, {}", id, exhibicionDTO);
        if (exhibicionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, exhibicionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!exhibicionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ExhibicionDTO> result = exhibicionService.partialUpdate(exhibicionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, exhibicionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /exhibicions} : get all the exhibicions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of exhibicions in body.
     */
    @GetMapping("/exhibicions")
    public ResponseEntity<List<ExhibicionDTO>> getAllExhibicions(ExhibicionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Exhibicions by criteria: {}", criteria);
        Page<ExhibicionDTO> page = exhibicionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /exhibicions/count} : count all the exhibicions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/exhibicions/count")
    public ResponseEntity<Long> countExhibicions(ExhibicionCriteria criteria) {
        log.debug("REST request to count Exhibicions by criteria: {}", criteria);
        return ResponseEntity.ok().body(exhibicionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /exhibicions/:id} : get the "id" exhibicion.
     *
     * @param id the id of the exhibicionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the exhibicionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/exhibicions/{id}")
    public ResponseEntity<ExhibicionDTO> getExhibicion(@PathVariable Long id) {
        log.debug("REST request to get Exhibicion : {}", id);
        Optional<ExhibicionDTO> exhibicionDTO = exhibicionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(exhibicionDTO);
    }

    /**
     * {@code DELETE  /exhibicions/:id} : delete the "id" exhibicion.
     *
     * @param id the id of the exhibicionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/exhibicions/{id}")
    public ResponseEntity<Void> deleteExhibicion(@PathVariable Long id) {
        log.debug("REST request to delete Exhibicion : {}", id);
        exhibicionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
