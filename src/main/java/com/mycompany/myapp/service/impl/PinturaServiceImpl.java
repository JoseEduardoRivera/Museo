package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Pintura;
import com.mycompany.myapp.repository.PinturaRepository;
import com.mycompany.myapp.service.PinturaService;
import com.mycompany.myapp.service.dto.PinturaDTO;
import com.mycompany.myapp.service.mapper.PinturaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Pintura}.
 */
@Service
@Transactional
public class PinturaServiceImpl implements PinturaService {

    private final Logger log = LoggerFactory.getLogger(PinturaServiceImpl.class);

    private final PinturaRepository pinturaRepository;

    private final PinturaMapper pinturaMapper;

    public PinturaServiceImpl(PinturaRepository pinturaRepository, PinturaMapper pinturaMapper) {
        this.pinturaRepository = pinturaRepository;
        this.pinturaMapper = pinturaMapper;
    }

    @Override
    public PinturaDTO save(PinturaDTO pinturaDTO) {
        log.debug("Request to save Pintura : {}", pinturaDTO);
        Pintura pintura = pinturaMapper.toEntity(pinturaDTO);
        pintura = pinturaRepository.save(pintura);
        return pinturaMapper.toDto(pintura);
    }

    @Override
    public Optional<PinturaDTO> partialUpdate(PinturaDTO pinturaDTO) {
        log.debug("Request to partially update Pintura : {}", pinturaDTO);

        return pinturaRepository
            .findById(pinturaDTO.getId())
            .map(
                existingPintura -> {
                    pinturaMapper.partialUpdate(existingPintura, pinturaDTO);
                    return existingPintura;
                }
            )
            .map(pinturaRepository::save)
            .map(pinturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PinturaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Pinturas");
        return pinturaRepository.findAll(pageable).map(pinturaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PinturaDTO> findOne(Long id) {
        log.debug("Request to get Pintura : {}", id);
        return pinturaRepository.findById(id).map(pinturaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pintura : {}", id);
        pinturaRepository.deleteById(id);
    }
}
