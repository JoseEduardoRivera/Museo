package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.PinturaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Pintura}.
 */
public interface PinturaService {
    /**
     * Save a pintura.
     *
     * @param pinturaDTO the entity to save.
     * @return the persisted entity.
     */
    PinturaDTO save(PinturaDTO pinturaDTO);

    /**
     * Partially updates a pintura.
     *
     * @param pinturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PinturaDTO> partialUpdate(PinturaDTO pinturaDTO);

    /**
     * Get all the pinturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PinturaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" pintura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PinturaDTO> findOne(Long id);

    /**
     * Delete the "id" pintura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
