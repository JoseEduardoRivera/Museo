package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CollecPerma;
import com.mycompany.myapp.repository.CollecPermaRepository;
import com.mycompany.myapp.service.CollecPermaService;
import com.mycompany.myapp.service.dto.CollecPermaDTO;
import com.mycompany.myapp.service.mapper.CollecPermaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CollecPerma}.
 */
@Service
@Transactional
public class CollecPermaServiceImpl implements CollecPermaService {

    private final Logger log = LoggerFactory.getLogger(CollecPermaServiceImpl.class);

    private final CollecPermaRepository collecPermaRepository;

    private final CollecPermaMapper collecPermaMapper;

    public CollecPermaServiceImpl(CollecPermaRepository collecPermaRepository, CollecPermaMapper collecPermaMapper) {
        this.collecPermaRepository = collecPermaRepository;
        this.collecPermaMapper = collecPermaMapper;
    }

    @Override
    public CollecPermaDTO save(CollecPermaDTO collecPermaDTO) {
        log.debug("Request to save CollecPerma : {}", collecPermaDTO);
        CollecPerma collecPerma = collecPermaMapper.toEntity(collecPermaDTO);
        collecPerma = collecPermaRepository.save(collecPerma);
        return collecPermaMapper.toDto(collecPerma);
    }

    @Override
    public Optional<CollecPermaDTO> partialUpdate(CollecPermaDTO collecPermaDTO) {
        log.debug("Request to partially update CollecPerma : {}", collecPermaDTO);

        return collecPermaRepository
            .findById(collecPermaDTO.getId())
            .map(
                existingCollecPerma -> {
                    collecPermaMapper.partialUpdate(existingCollecPerma, collecPermaDTO);
                    return existingCollecPerma;
                }
            )
            .map(collecPermaRepository::save)
            .map(collecPermaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollecPermaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CollecPermas");
        return collecPermaRepository.findAll(pageable).map(collecPermaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CollecPermaDTO> findOne(Long id) {
        log.debug("Request to get CollecPerma : {}", id);
        return collecPermaRepository.findById(id).map(collecPermaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CollecPerma : {}", id);
        collecPermaRepository.deleteById(id);
    }
}
