package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ObjArte;
import com.mycompany.myapp.repository.ObjArteRepository;
import com.mycompany.myapp.service.criteria.ObjArteCriteria;
import com.mycompany.myapp.service.dto.ObjArteDTO;
import com.mycompany.myapp.service.mapper.ObjArteMapper;
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
 * Service for executing complex queries for {@link ObjArte} entities in the database.
 * The main input is a {@link ObjArteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ObjArteDTO} or a {@link Page} of {@link ObjArteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ObjArteQueryService extends QueryService<ObjArte> {

    private final Logger log = LoggerFactory.getLogger(ObjArteQueryService.class);

    private final ObjArteRepository objArteRepository;

    private final ObjArteMapper objArteMapper;

    public ObjArteQueryService(ObjArteRepository objArteRepository, ObjArteMapper objArteMapper) {
        this.objArteRepository = objArteRepository;
        this.objArteMapper = objArteMapper;
    }

    /**
     * Return a {@link List} of {@link ObjArteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ObjArteDTO> findByCriteria(ObjArteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ObjArte> specification = createSpecification(criteria);
        return objArteMapper.toDto(objArteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ObjArteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ObjArteDTO> findByCriteria(ObjArteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ObjArte> specification = createSpecification(criteria);
        return objArteRepository.findAll(specification, page).map(objArteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ObjArteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ObjArte> specification = createSpecification(criteria);
        return objArteRepository.count(specification);
    }

    /**
     * Function to convert {@link ObjArteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ObjArte> createSpecification(ObjArteCriteria criteria) {
        Specification<ObjArte> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ObjArte_.id));
            }
            if (criteria.getIdObjArt() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdObjArt(), ObjArte_.idObjArt));
            }
            if (criteria.getPaisCultura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPaisCultura(), ObjArte_.paisCultura));
            }
            if (criteria.getAnio() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnio(), ObjArte_.anio));
            }
            if (criteria.getTituloObj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTituloObj(), ObjArte_.tituloObj));
            }
            if (criteria.getDescObj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescObj(), ObjArte_.descObj));
            }
            if (criteria.getEpocaObj() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEpocaObj(), ObjArte_.epocaObj));
            }
            if (criteria.getCollecPermaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCollecPermaId(),
                            root -> root.join(ObjArte_.collecPermas, JoinType.LEFT).get(CollecPerma_.id)
                        )
                    );
            }
            if (criteria.getCollecPrestaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCollecPrestaId(),
                            root -> root.join(ObjArte_.collecPrestas, JoinType.LEFT).get(CollecPresta_.id)
                        )
                    );
            }
            if (criteria.getPinturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPinturaId(), root -> root.join(ObjArte_.pinturas, JoinType.LEFT).get(Pintura_.id))
                    );
            }
            if (criteria.getEsculturaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEsculturaId(),
                            root -> root.join(ObjArte_.esculturas, JoinType.LEFT).get(Escultura_.id)
                        )
                    );
            }
            if (criteria.getOtroObjId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getOtroObjId(), root -> root.join(ObjArte_.otroObjs, JoinType.LEFT).get(OtroObj_.id))
                    );
            }
            if (criteria.getArtistaId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getArtistaId(), root -> root.join(ObjArte_.artistas, JoinType.LEFT).get(Artista_.id))
                    );
            }
            if (criteria.getExhibicionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getExhibicionId(),
                            root -> root.join(ObjArte_.exhibicion, JoinType.LEFT).get(Exhibicion_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
