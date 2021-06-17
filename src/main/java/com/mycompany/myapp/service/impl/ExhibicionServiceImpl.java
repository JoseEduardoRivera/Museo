package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Exhibicion;
import com.mycompany.myapp.repository.ExhibicionRepository;
import com.mycompany.myapp.service.ExhibicionService;
import com.mycompany.myapp.service.dto.ExhibicionDTO;
import com.mycompany.myapp.service.mapper.ExhibicionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Exhibicion}.
 */
@Service
@Transactional
public class ExhibicionServiceImpl implements ExhibicionService {

    private final Logger log = LoggerFactory.getLogger(ExhibicionServiceImpl.class);

    private final ExhibicionRepository exhibicionRepository;

    private final ExhibicionMapper exhibicionMapper;

    public ExhibicionServiceImpl(ExhibicionRepository exhibicionRepository, ExhibicionMapper exhibicionMapper) {
        this.exhibicionRepository = exhibicionRepository;
        this.exhibicionMapper = exhibicionMapper;
    }

    @Override
    public ExhibicionDTO save(ExhibicionDTO exhibicionDTO) {
        log.debug("Request to save Exhibicion : {}", exhibicionDTO);
        Exhibicion exhibicion = exhibicionMapper.toEntity(exhibicionDTO);
        exhibicion = exhibicionRepository.save(exhibicion);
        return exhibicionMapper.toDto(exhibicion);
    }

    @Override
    public Optional<ExhibicionDTO> partialUpdate(ExhibicionDTO exhibicionDTO) {
        log.debug("Request to partially update Exhibicion : {}", exhibicionDTO);

        return exhibicionRepository
            .findById(exhibicionDTO.getId())
            .map(
                existingExhibicion -> {
                    exhibicionMapper.partialUpdate(existingExhibicion, exhibicionDTO);
                    return existingExhibicion;
                }
            )
            .map(exhibicionRepository::save)
            .map(exhibicionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ExhibicionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Exhibicions");
        return exhibicionRepository.findAll(pageable).map(exhibicionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExhibicionDTO> findOne(Long id) {
        log.debug("Request to get Exhibicion : {}", id);
        return exhibicionRepository.findById(id).map(exhibicionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Exhibicion : {}", id);
        exhibicionRepository.deleteById(id);
    }
}
