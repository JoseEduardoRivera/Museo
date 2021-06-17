package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.ObjArteRepository;
import com.mycompany.myapp.service.ObjArteService;
import com.mycompany.myapp.service.dto.ObjArteDTO;
import com.mycompany.myapp.service.mapper.ObjArteMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ObjArte}.
 */
@Service
@Transactional
public class ObjArteServiceImpl implements ObjArteService {

    private final Logger log = LoggerFactory.getLogger(ObjArteServiceImpl.class);

    private final ObjArteRepository objArteRepository;

    private final ObjArteMapper objArteMapper;

    public ObjArteServiceImpl(ObjArteRepository objArteRepository, ObjArteMapper objArteMapper) {
        this.objArteRepository = objArteRepository;
        this.objArteMapper = objArteMapper;
    }

    @Override
    public ObjArteDTO save(ObjArteDTO objArteDTO) {
        log.debug("Request to save ObjArte : {}", objArteDTO);
        ObjArte objArte = objArteMapper.toEntity(objArteDTO);
        objArte = objArteRepository.save(objArte);
        return objArteMapper.toDto(objArte);
    }

    @Override
    public Optional<ObjArteDTO> partialUpdate(ObjArteDTO objArteDTO) {
        log.debug("Request to partially update ObjArte : {}", objArteDTO);

        return objArteRepository
            .findById(objArteDTO.getId())
            .map(
                existingObjArte -> {
                    objArteMapper.partialUpdate(existingObjArte, objArteDTO);
                    return existingObjArte;
                }
            )
            .map(objArteRepository::save)
            .map(objArteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ObjArteDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ObjArtes");
        return objArteRepository.findAll(pageable).map(objArteMapper::toDto);
    }

    public Page<ObjArteDTO> findAllWithEagerRelationships(Pageable pageable) {
        return objArteRepository.findAllWithEagerRelationships(pageable).map(objArteMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ObjArteDTO> findOne(Long id) {
        log.debug("Request to get ObjArte : {}", id);
        return objArteRepository.findOneWithEagerRelationships(id).map(objArteMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ObjArte : {}", id);
        objArteRepository.deleteById(id);
    }
}
