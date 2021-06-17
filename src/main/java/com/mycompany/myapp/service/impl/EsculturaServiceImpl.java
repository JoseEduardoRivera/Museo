package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Escultura;
import com.mycompany.myapp.repository.EsculturaRepository;
import com.mycompany.myapp.service.EsculturaService;
import com.mycompany.myapp.service.dto.EsculturaDTO;
import com.mycompany.myapp.service.mapper.EsculturaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Escultura}.
 */
@Service
@Transactional
public class EsculturaServiceImpl implements EsculturaService {

    private final Logger log = LoggerFactory.getLogger(EsculturaServiceImpl.class);

    private final EsculturaRepository esculturaRepository;

    private final EsculturaMapper esculturaMapper;

    public EsculturaServiceImpl(EsculturaRepository esculturaRepository, EsculturaMapper esculturaMapper) {
        this.esculturaRepository = esculturaRepository;
        this.esculturaMapper = esculturaMapper;
    }

    @Override
    public EsculturaDTO save(EsculturaDTO esculturaDTO) {
        log.debug("Request to save Escultura : {}", esculturaDTO);
        Escultura escultura = esculturaMapper.toEntity(esculturaDTO);
        escultura = esculturaRepository.save(escultura);
        return esculturaMapper.toDto(escultura);
    }

    @Override
    public Optional<EsculturaDTO> partialUpdate(EsculturaDTO esculturaDTO) {
        log.debug("Request to partially update Escultura : {}", esculturaDTO);

        return esculturaRepository
            .findById(esculturaDTO.getId())
            .map(
                existingEscultura -> {
                    esculturaMapper.partialUpdate(existingEscultura, esculturaDTO);
                    return existingEscultura;
                }
            )
            .map(esculturaRepository::save)
            .map(esculturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EsculturaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Esculturas");
        return esculturaRepository.findAll(pageable).map(esculturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EsculturaDTO> findOne(Long id) {
        log.debug("Request to get Escultura : {}", id);
        return esculturaRepository.findById(id).map(esculturaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Escultura : {}", id);
        esculturaRepository.deleteById(id);
    }
}
