package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Artista;
import com.mycompany.myapp.repository.ArtistaRepository;
import com.mycompany.myapp.service.criteria.ArtistaCriteria;
import com.mycompany.myapp.service.dto.ArtistaDTO;
import com.mycompany.myapp.service.mapper.ArtistaMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Artista} entities in the database.
 * The main input is a {@link ArtistaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArtistaDTO} or a {@link Page} of {@link ArtistaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArtistaQueryService extends QueryService<Artista> {

    private final Logger log = LoggerFactory.getLogger(ArtistaQueryService.class);

    private final ArtistaRepository artistaRepository;

    private final ArtistaMapper artistaMapper;

    public ArtistaQueryService(ArtistaRepository artistaRepository, ArtistaMapper artistaMapper) {
        this.artistaRepository = artistaRepository;
        this.artistaMapper = artistaMapper;
    }

    /**
     * Return a {@link List} of {@link ArtistaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArtistaDTO> findByCriteria(ArtistaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Artista> specification = createSpecification(criteria);
        return artistaMapper.toDto(artistaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArtistaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArtistaDTO> findByCriteria(ArtistaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Artista> specification = createSpecification(criteria);
        return artistaRepository.findAll(specification, page).map(artistaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArtistaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Artista> specification = createSpecification(criteria);
        return artistaRepository.count(specification);
    }

    /**
     * Function to convert {@link ArtistaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Artista> createSpecification(ArtistaCriteria criteria) {
        Specification<Artista> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Artista_.id));
            }
            if (criteria.getNomArt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNomArt(), Artista_.nomArt));
            }
            if (criteria.getFechNac() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFechNac(), Artista_.fechNac));
            }
            if (criteria.getFechDefu() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFechDefu(), Artista_.fechDefu));
            }
            if (criteria.getPaisOrigen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaisOrigen(), Artista_.paisOrigen));
            }
            if (criteria.getEstiloArt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstiloArt(), Artista_.estiloArt));
            }
            if (criteria.getEpoca() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEpoca(), Artista_.epoca));
            }
            if (criteria.getObjArteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObjArteId(), root -> root.join(Artista_.objArtes, JoinType.LEFT).get(ObjArte_.id))
                    );
            }
        }
        return specification;
    }
}
