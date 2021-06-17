package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Pintura;
import com.mycompany.myapp.repository.PinturaRepository;
import com.mycompany.myapp.service.criteria.PinturaCriteria;
import com.mycompany.myapp.service.dto.PinturaDTO;
import com.mycompany.myapp.service.mapper.PinturaMapper;
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
 * Service for executing complex queries for {@link Pintura} entities in the database.
 * The main input is a {@link PinturaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PinturaDTO} or a {@link Page} of {@link PinturaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PinturaQueryService extends QueryService<Pintura> {

    private final Logger log = LoggerFactory.getLogger(PinturaQueryService.class);

    private final PinturaRepository pinturaRepository;

    private final PinturaMapper pinturaMapper;

    public PinturaQueryService(PinturaRepository pinturaRepository, PinturaMapper pinturaMapper) {
        this.pinturaRepository = pinturaRepository;
        this.pinturaMapper = pinturaMapper;
    }

    /**
     * Return a {@link List} of {@link PinturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PinturaDTO> findByCriteria(PinturaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Pintura> specification = createSpecification(criteria);
        return pinturaMapper.toDto(pinturaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PinturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PinturaDTO> findByCriteria(PinturaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Pintura> specification = createSpecification(criteria);
        return pinturaRepository.findAll(specification, page).map(pinturaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PinturaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Pintura> specification = createSpecification(criteria);
        return pinturaRepository.count(specification);
    }

    /**
     * Function to convert {@link PinturaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Pintura> createSpecification(PinturaCriteria criteria) {
        Specification<Pintura> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Pintura_.id));
            }
            if (criteria.getTipoPintura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipoPintura(), Pintura_.tipoPintura));
            }
            if (criteria.getMaterialPintura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterialPintura(), Pintura_.materialPintura));
            }
            if (criteria.getEstiloPint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstiloPint(), Pintura_.estiloPint));
            }
            if (criteria.getObjArteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObjArteId(), root -> root.join(Pintura_.objArte, JoinType.LEFT).get(ObjArte_.id))
                    );
            }
        }
        return specification;
    }
}
