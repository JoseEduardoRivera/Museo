package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Artista;
import com.mycompany.myapp.repository.ArtistaRepository;
import com.mycompany.myapp.service.ArtistaService;
import com.mycompany.myapp.service.dto.ArtistaDTO;
import com.mycompany.myapp.service.mapper.ArtistaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Artista}.
 */
@Service
@Transactional
public class ArtistaServiceImpl implements ArtistaService {

    private final Logger log = LoggerFactory.getLogger(ArtistaServiceImpl.class);

    private final ArtistaRepository artistaRepository;

    private final ArtistaMapper artistaMapper;

    public ArtistaServiceImpl(ArtistaRepository artistaRepository, ArtistaMapper artistaMapper) {
        this.artistaRepository = artistaRepository;
        this.artistaMapper = artistaMapper;
    }

    @Override
    public ArtistaDTO save(ArtistaDTO artistaDTO) {
        log.debug("Request to save Artista : {}", artistaDTO);
        Artista artista = artistaMapper.toEntity(artistaDTO);
        artista = artistaRepository.save(artista);
        return artistaMapper.toDto(artista);
    }

    @Override
    public Optional<ArtistaDTO> partialUpdate(ArtistaDTO artistaDTO) {
        log.debug("Request to partially update Artista : {}", artistaDTO);

        return artistaRepository
            .findById(artistaDTO.getId())
            .map(
                existingArtista -> {
                    artistaMapper.partialUpdate(existingArtista, artistaDTO);
                    return existingArtista;
                }
            )
            .map(artistaRepository::save)
            .map(artistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArtistaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Artistas");
        return artistaRepository.findAll(pageable).map(artistaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ArtistaDTO> findOne(Long id) {
        log.debug("Request to get Artista : {}", id);
        return artistaRepository.findById(id).map(artistaMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Artista : {}", id);
        artistaRepository.deleteById(id);
    }
}
