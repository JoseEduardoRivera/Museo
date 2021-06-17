package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.OtroObjRepository;
import com.mycompany.myapp.service.OtroObjQueryService;
import com.mycompany.myapp.service.OtroObjService;
import com.mycompany.myapp.service.criteria.OtroObjCriteria;
import com.mycompany.myapp.service.dto.OtroObjDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.OtroObj}.
 */
@RestController
@RequestMapping("/api")
public class OtroObjResource {

    private final Logger log = LoggerFactory.getLogger(OtroObjResource.class);

    private static final String ENTITY_NAME = "otroObj";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OtroObjService otroObjService;

    private final OtroObjRepository otroObjRepository;

    private final OtroObjQueryService otroObjQueryService;

    public OtroObjResource(OtroObjService otroObjService, OtroObjRepository otroObjRepository, OtroObjQueryService otroObjQueryService) {
        this.otroObjService = otroObjService;
        this.otroObjRepository = otroObjRepository;
        this.otroObjQueryService = otroObjQueryService;
    }

    /**
     * {@code POST  /otro-objs} : Create a new otroObj.
     *
     * @param otroObjDTO the otroObjDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new otroObjDTO, or with status {@code 400 (Bad Request)} if the otroObj has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/otro-objs")
    public ResponseEntity<OtroObjDTO> createOtroObj(@Valid @RequestBody OtroObjDTO otroObjDTO) throws URISyntaxException {
        log.debug("REST request to save OtroObj : {}", otroObjDTO);
        if (otroObjDTO.getId() != null) {
            throw new BadRequestAlertException("A new otroObj cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OtroObjDTO result = otroObjService.save(otroObjDTO);
        return ResponseEntity
            .created(new URI("/api/otro-objs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /otro-objs/:id} : Updates an existing otroObj.
     *
     * @param id the id of the otroObjDTO to save.
     * @param otroObjDTO the otroObjDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otroObjDTO,
     * or with status {@code 400 (Bad Request)} if the otroObjDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the otroObjDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/otro-objs/{id}")
    public ResponseEntity<OtroObjDTO> updateOtroObj(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody OtroObjDTO otroObjDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OtroObj : {}, {}", id, otroObjDTO);
        if (otroObjDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otroObjDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otroObjRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OtroObjDTO result = otroObjService.save(otroObjDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otroObjDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /otro-objs/:id} : Partial updates given fields of an existing otroObj, field will ignore if it is null
     *
     * @param id the id of the otroObjDTO to save.
     * @param otroObjDTO the otroObjDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated otroObjDTO,
     * or with status {@code 400 (Bad Request)} if the otroObjDTO is not valid,
     * or with status {@code 404 (Not Found)} if the otroObjDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the otroObjDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/otro-objs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OtroObjDTO> partialUpdateOtroObj(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody OtroObjDTO otroObjDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OtroObj partially : {}, {}", id, otroObjDTO);
        if (otroObjDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, otroObjDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!otroObjRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OtroObjDTO> result = otroObjService.partialUpdate(otroObjDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, otroObjDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /otro-objs} : get all the otroObjs.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of otroObjs in body.
     */
    @GetMapping("/otro-objs")
    public ResponseEntity<List<OtroObjDTO>> getAllOtroObjs(OtroObjCriteria criteria, Pageable pageable) {
        log.debug("REST request to get OtroObjs by criteria: {}", criteria);
        Page<OtroObjDTO> page = otroObjQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /otro-objs/count} : count all the otroObjs.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/otro-objs/count")
    public ResponseEntity<Long> countOtroObjs(OtroObjCriteria criteria) {
        log.debug("REST request to count OtroObjs by criteria: {}", criteria);
        return ResponseEntity.ok().body(otroObjQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /otro-objs/:id} : get the "id" otroObj.
     *
     * @param id the id of the otroObjDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the otroObjDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/otro-objs/{id}")
    public ResponseEntity<OtroObjDTO> getOtroObj(@PathVariable Long id) {
        log.debug("REST request to get OtroObj : {}", id);
        Optional<OtroObjDTO> otroObjDTO = otroObjService.findOne(id);
        return ResponseUtil.wrapOrNotFound(otroObjDTO);
    }

    /**
     * {@code DELETE  /otro-objs/:id} : delete the "id" otroObj.
     *
     * @param id the id of the otroObjDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/otro-objs/{id}")
    public ResponseEntity<Void> deleteOtroObj(@PathVariable Long id) {
        log.debug("REST request to delete OtroObj : {}", id);
        otroObjService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
