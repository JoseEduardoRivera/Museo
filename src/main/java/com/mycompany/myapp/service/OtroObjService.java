package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.OtroObjDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.OtroObj}.
 */
public interface OtroObjService {
    /**
     * Save a otroObj.
     *
     * @param otroObjDTO the entity to save.
     * @return the persisted entity.
     */
    OtroObjDTO save(OtroObjDTO otroObjDTO);

    /**
     * Partially updates a otroObj.
     *
     * @param otroObjDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OtroObjDTO> partialUpdate(OtroObjDTO otroObjDTO);

    /**
     * Get all the otroObjs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OtroObjDTO> findAll(Pageable pageable);

    /**
     * Get the "id" otroObj.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OtroObjDTO> findOne(Long id);

    /**
     * Delete the "id" otroObj.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
