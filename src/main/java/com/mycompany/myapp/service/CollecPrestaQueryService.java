package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.CollecPresta;
import com.mycompany.myapp.repository.CollecPrestaRepository;
import com.mycompany.myapp.service.criteria.CollecPrestaCriteria;
import com.mycompany.myapp.service.dto.CollecPrestaDTO;
import com.mycompany.myapp.service.mapper.CollecPrestaMapper;
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
 * Service for executing complex queries for {@link CollecPresta} entities in the database.
 * The main input is a {@link CollecPrestaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CollecPrestaDTO} or a {@link Page} of {@link CollecPrestaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CollecPrestaQueryService extends QueryService<CollecPresta> {

    private final Logger log = LoggerFactory.getLogger(CollecPrestaQueryService.class);

    private final CollecPrestaRepository collecPrestaRepository;

    private final CollecPrestaMapper collecPrestaMapper;

    public CollecPrestaQueryService(CollecPrestaRepository collecPrestaRepository, CollecPrestaMapper collecPrestaMapper) {
        this.collecPrestaRepository = collecPrestaRepository;
        this.collecPrestaMapper = collecPrestaMapper;
    }

    /**
     * Return a {@link List} of {@link CollecPrestaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CollecPrestaDTO> findByCriteria(CollecPrestaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CollecPresta> specification = createSpecification(criteria);
        return collecPrestaMapper.toDto(collecPrestaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CollecPrestaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CollecPrestaDTO> findByCriteria(CollecPrestaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CollecPresta> specification = createSpecification(criteria);
        return collecPrestaRepository.findAll(specification, page).map(collecPrestaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CollecPrestaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CollecPresta> specification = createSpecification(criteria);
        return collecPrestaRepository.count(specification);
    }

    /**
     * Function to convert {@link CollecPrestaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CollecPresta> createSpecification(CollecPrestaCriteria criteria) {
        Specification<CollecPresta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CollecPresta_.id));
            }
            if (criteria.getInfPrest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInfPrest(), CollecPresta_.infPrest));
            }
            if (criteria.getFechPrest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFechPrest(), CollecPresta_.fechPrest));
            }
            if (criteria.getFechDev() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFechDev(), CollecPresta_.fechDev));
            }
            if (criteria.getObjArteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getObjArteId(),
                            root -> root.join(CollecPresta_.objArte, JoinType.LEFT).get(ObjArte_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
