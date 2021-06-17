package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.ExhibicionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Exhibicion}.
 */
public interface ExhibicionService {
    /**
     * Save a exhibicion.
     *
     * @param exhibicionDTO the entity to save.
     * @return the persisted entity.
     */
    ExhibicionDTO save(ExhibicionDTO exhibicionDTO);

    /**
     * Partially updates a exhibicion.
     *
     * @param exhibicionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ExhibicionDTO> partialUpdate(ExhibicionDTO exhibicionDTO);

    /**
     * Get all the exhibicions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ExhibicionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" exhibicion.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ExhibicionDTO> findOne(Long id);

    /**
     * Delete the "id" exhibicion.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
