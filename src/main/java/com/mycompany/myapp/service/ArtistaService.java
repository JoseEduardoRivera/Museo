package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ArtistaDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Artista}.
 */
public interface ArtistaService {
    /**
     * Save a artista.
     *
     * @param artistaDTO the entity to save.
     * @return the persisted entity.
     */
    ArtistaDTO save(ArtistaDTO artistaDTO);

    /**
     * Partially updates a artista.
     *
     * @param artistaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ArtistaDTO> partialUpdate(ArtistaDTO artistaDTO);

    /**
     * Get all the artistas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArtistaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" artista.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArtistaDTO> findOne(Long id);

    /**
     * Delete the "id" artista.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
