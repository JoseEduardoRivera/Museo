package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CollecPerma;
import com.mycompany.myapp.repository.CollecPermaRepository;
import com.mycompany.myapp.service.criteria.CollecPermaCriteria;
import com.mycompany.myapp.service.dto.CollecPermaDTO;
import com.mycompany.myapp.service.mapper.CollecPermaMapper;
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
 * Service for executing complex queries for {@link CollecPerma} entities in the database.
 * The main input is a {@link CollecPermaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CollecPermaDTO} or a {@link Page} of {@link CollecPermaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CollecPermaQueryService extends QueryService<CollecPerma> {

    private final Logger log = LoggerFactory.getLogger(CollecPermaQueryService.class);

    private final CollecPermaRepository collecPermaRepository;

    private final CollecPermaMapper collecPermaMapper;

    public CollecPermaQueryService(CollecPermaRepository collecPermaRepository, CollecPermaMapper collecPermaMapper) {
        this.collecPermaRepository = collecPermaRepository;
        this.collecPermaMapper = collecPermaMapper;
    }

    /**
     * Return a {@link List} of {@link CollecPermaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CollecPermaDTO> findByCriteria(CollecPermaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CollecPerma> specification = createSpecification(criteria);
        return collecPermaMapper.toDto(collecPermaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CollecPermaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CollecPermaDTO> findByCriteria(CollecPermaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CollecPerma> specification = createSpecification(criteria);
        return collecPermaRepository.findAll(specification, page).map(collecPermaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CollecPermaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CollecPerma> specification = createSpecification(criteria);
        return collecPermaRepository.count(specification);
    }

    /**
     * Function to convert {@link CollecPermaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CollecPerma> createSpecification(CollecPermaCriteria criteria) {
        Specification<CollecPerma> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CollecPerma_.id));
            }
            if (criteria.getExhibiAlmacen() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExhibiAlmacen(), CollecPerma_.exhibiAlmacen));
            }
            if (criteria.getCosto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCosto(), CollecPerma_.costo));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFecha(), CollecPerma_.fecha));
            }
            if (criteria.getObjArteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObjArteId(), root -> root.join(CollecPerma_.objArte, JoinType.LEFT).get(ObjArte_.id))
                    );
            }
        }
        return specification;
    }
}
