package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ObjArteDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.ObjArte}.
 */
public interface ObjArteService {
    /**
     * Save a objArte.
     *
     * @param objArteDTO the entity to save.
     * @return the persisted entity.
     */
    ObjArteDTO save(ObjArteDTO objArteDTO);

    /**
     * Partially updates a objArte.
     *
     * @param objArteDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ObjArteDTO> partialUpdate(ObjArteDTO objArteDTO);

    /**
     * Get all the objArtes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObjArteDTO> findAll(Pageable pageable);

    /**
     * Get all the objArtes with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ObjArteDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" objArte.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ObjArteDTO> findOne(Long id);

    /**
     * Delete the "id" objArte.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
