package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CollecPresta;
import com.mycompany.myapp.repository.CollecPrestaRepository;
import com.mycompany.myapp.service.CollecPrestaService;
import com.mycompany.myapp.service.dto.CollecPrestaDTO;
import com.mycompany.myapp.service.mapper.CollecPrestaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CollecPresta}.
 */
@Service
@Transactional
public class CollecPrestaServiceImpl implements CollecPrestaService {

    private final Logger log = LoggerFactory.getLogger(CollecPrestaServiceImpl.class);

    private final CollecPrestaRepository collecPrestaRepository;

    private final CollecPrestaMapper collecPrestaMapper;

    public CollecPrestaServiceImpl(CollecPrestaRepository collecPrestaRepository, CollecPrestaMapper collecPrestaMapper) {
        this.collecPrestaRepository = collecPrestaRepository;
        this.collecPrestaMapper = collecPrestaMapper;
    }

    @Override
    public CollecPrestaDTO save(CollecPrestaDTO collecPrestaDTO) {
        log.debug("Request to save CollecPresta : {}", collecPrestaDTO);
        CollecPresta collecPresta = collecPrestaMapper.toEntity(collecPrestaDTO);
        collecPresta = collecPrestaRepository.save(collecPresta);
        return collecPrestaMapper.toDto(collecPresta);
    }

    @Override
    public Optional<CollecPrestaDTO> partialUpdate(CollecPrestaDTO collecPrestaDTO) {
        log.debug("Request to partially update CollecPresta : {}", collecPrestaDTO);

        return collecPrestaRepository
            .findById(collecPrestaDTO.getId())
            .map(
                existingCollecPresta -> {
                    collecPrestaMapper.partialUpdate(existingCollecPresta, collecPrestaDTO);
                    return existingCollecPresta;
                }
            )
            .map(collecPrestaRepository::save)
            .map(collecPrestaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollecPrestaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CollecPrestas");
        return collecPrestaRepository.findAll(pageable).map(collecPrestaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollecPrestaDTO> findOne(Long id) {
        log.debug("Request to get CollecPresta : {}", id);
        return collecPrestaRepository.findById(id).map(collecPrestaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CollecPresta : {}", id);
        collecPrestaRepository.deleteById(id);
    }
}
