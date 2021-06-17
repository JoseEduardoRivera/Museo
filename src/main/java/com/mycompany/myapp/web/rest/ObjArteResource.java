package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ObjArteRepository;
import com.mycompany.myapp.service.ObjArteQueryService;
import com.mycompany.myapp.service.ObjArteService;
import com.mycompany.myapp.service.criteria.ObjArteCriteria;
import com.mycompany.myapp.service.dto.ObjArteDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ObjArte}.
 */
@RestController
@RequestMapping("/api")
public class ObjArteResource {

    private final Logger log = LoggerFactory.getLogger(ObjArteResource.class);

    private static final String ENTITY_NAME = "objArte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ObjArteService objArteService;

    private final ObjArteRepository objArteRepository;

    private final ObjArteQueryService objArteQueryService;

    public ObjArteResource(ObjArteService objArteService, ObjArteRepository objArteRepository, ObjArteQueryService objArteQueryService) {
        this.objArteService = objArteService;
        this.objArteRepository = objArteRepository;
        this.objArteQueryService = objArteQueryService;
    }

    /**
     * {@code POST  /obj-artes} : Create a new objArte.
     *
     * @param objArteDTO the objArteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new objArteDTO, or with status {@code 400 (Bad Request)} if the objArte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/obj-artes")
    public ResponseEntity<ObjArteDTO> createObjArte(@Valid @RequestBody ObjArteDTO objArteDTO) throws URISyntaxException {
        log.debug("REST request to save ObjArte : {}", objArteDTO);
        if (objArteDTO.getId() != null) {
            throw new BadRequestAlertException("A new objArte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ObjArteDTO result = objArteService.save(objArteDTO);
        return ResponseEntity
            .created(new URI("/api/obj-artes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /obj-artes/:id} : Updates an existing objArte.
     *
     * @param id the id of the objArteDTO to save.
     * @param objArteDTO the objArteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objArteDTO,
     * or with status {@code 400 (Bad Request)} if the objArteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the objArteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/obj-artes/{id}")
    public ResponseEntity<ObjArteDTO> updateObjArte(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ObjArteDTO objArteDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ObjArte : {}, {}", id, objArteDTO);
        if (objArteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objArteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objArteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ObjArteDTO result = objArteService.save(objArteDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, objArteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /obj-artes/:id} : Partial updates given fields of an existing objArte, field will ignore if it is null
     *
     * @param id the id of the objArteDTO to save.
     * @param objArteDTO the objArteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated objArteDTO,
     * or with status {@code 400 (Bad Request)} if the objArteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the objArteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the objArteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/obj-artes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ObjArteDTO> partialUpdateObjArte(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ObjArteDTO objArteDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ObjArte partially : {}, {}", id, objArteDTO);
        if (objArteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, objArteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!objArteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ObjArteDTO> result = objArteService.partialUpdate(objArteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, objArteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /obj-artes} : get all the objArtes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of objArtes in body.
     */
    @GetMapping("/obj-artes")
    public ResponseEntity<List<ObjArteDTO>> getAllObjArtes(ObjArteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ObjArtes by criteria: {}", criteria);
        Page<ObjArteDTO> page = objArteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /obj-artes/count} : count all the objArtes.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/obj-artes/count")
    public ResponseEntity<Long> countObjArtes(ObjArteCriteria criteria) {
        log.debug("REST request to count ObjArtes by criteria: {}", criteria);
        return ResponseEntity.ok().body(objArteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /obj-artes/:id} : get the "id" objArte.
     *
     * @param id the id of the objArteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the objArteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/obj-artes/{id}")
    public ResponseEntity<ObjArteDTO> getObjArte(@PathVariable Long id) {
        log.debug("REST request to get ObjArte : {}", id);
        Optional<ObjArteDTO> objArteDTO = objArteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(objArteDTO);
    }

    /**
     * {@code DELETE  /obj-artes/:id} : delete the "id" objArte.
     *
     * @param id the id of the objArteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/obj-artes/{id}")
    public ResponseEntity<Void> deleteObjArte(@PathVariable Long id) {
        log.debug("REST request to delete ObjArte : {}", id);
        objArteService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
