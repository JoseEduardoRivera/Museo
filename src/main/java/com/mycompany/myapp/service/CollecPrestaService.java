package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CollecPrestaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CollecPresta}.
 */
public interface CollecPrestaService {
    /**
     * Save a collecPresta.
     *
     * @param collecPrestaDTO the entity to save.
     * @return the persisted entity.
     */
    CollecPrestaDTO save(CollecPrestaDTO collecPrestaDTO);

    /**
     * Partially updates a collecPresta.
     *
     * @param collecPrestaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollecPrestaDTO> partialUpdate(CollecPrestaDTO collecPrestaDTO);

    /**
     * Get all the collecPrestas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollecPrestaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" collecPresta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollecPrestaDTO> findOne(Long id);

    /**
     * Delete the "id" collecPresta.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
