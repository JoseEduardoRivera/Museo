package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.CollecPermaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.CollecPerma}.
 */
public interface CollecPermaService {
    /**
     * Save a collecPerma.
     *
     * @param collecPermaDTO the entity to save.
     * @return the persisted entity.
     */
    CollecPermaDTO save(CollecPermaDTO collecPermaDTO);

    /**
     * Partially updates a collecPerma.
     *
     * @param collecPermaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CollecPermaDTO> partialUpdate(CollecPermaDTO collecPermaDTO);

    /**
     * Get all the collecPermas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CollecPermaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" collecPerma.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CollecPermaDTO> findOne(Long id);

    /**
     * Delete the "id" collecPerma.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
