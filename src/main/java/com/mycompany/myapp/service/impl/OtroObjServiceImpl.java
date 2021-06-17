package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.OtroObj;
import com.mycompany.myapp.repository.OtroObjRepository;
import com.mycompany.myapp.service.OtroObjService;
import com.mycompany.myapp.service.dto.OtroObjDTO;
import com.mycompany.myapp.service.mapper.OtroObjMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OtroObj}.
 */
@Service
@Transactional
public class OtroObjServiceImpl implements OtroObjService {

    private final Logger log = LoggerFactory.getLogger(OtroObjServiceImpl.class);

    private final OtroObjRepository otroObjRepository;

    private final OtroObjMapper otroObjMapper;

    public OtroObjServiceImpl(OtroObjRepository otroObjRepository, OtroObjMapper otroObjMapper) {
        this.otroObjRepository = otroObjRepository;
        this.otroObjMapper = otroObjMapper;
    }

    @Override
    public OtroObjDTO save(OtroObjDTO otroObjDTO) {
        log.debug("Request to save OtroObj : {}", otroObjDTO);
        OtroObj otroObj = otroObjMapper.toEntity(otroObjDTO);
        otroObj = otroObjRepository.save(otroObj);
        return otroObjMapper.toDto(otroObj);
    }

    @Override
    public Optional<OtroObjDTO> partialUpdate(OtroObjDTO otroObjDTO) {
        log.debug("Request to partially update OtroObj : {}", otroObjDTO);

        return otroObjRepository
            .findById(otroObjDTO.getId())
            .map(
                existingOtroObj -> {
                    otroObjMapper.partialUpdate(existingOtroObj, otroObjDTO);
                    return existingOtroObj;
                }
            )
            .map(otroObjRepository::save)
            .map(otroObjMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OtroObjDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OtroObjs");
        return otroObjRepository.findAll(pageable).map(otroObjMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OtroObjDTO> findOne(Long id) {
        log.debug("Request to get OtroObj : {}", id);
        return otroObjRepository.findById(id).map(otroObjMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OtroObj : {}", id);
        otroObjRepository.deleteById(id);
    }
}
