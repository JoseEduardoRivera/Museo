package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.EsculturaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Escultura}.
 */
public interface EsculturaService {
    /**
     * Save a escultura.
     *
     * @param esculturaDTO the entity to save.
     * @return the persisted entity.
     */
    EsculturaDTO save(EsculturaDTO esculturaDTO);

    /**
     * Partially updates a escultura.
     *
     * @param esculturaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EsculturaDTO> partialUpdate(EsculturaDTO esculturaDTO);

    /**
     * Get all the esculturas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EsculturaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" escultura.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EsculturaDTO> findOne(Long id);

    /**
     * Delete the "id" escultura.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
