package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Escultura;
import com.mycompany.myapp.repository.EsculturaRepository;
import com.mycompany.myapp.service.criteria.EsculturaCriteria;
import com.mycompany.myapp.service.dto.EsculturaDTO;
import com.mycompany.myapp.service.mapper.EsculturaMapper;
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
 * Service for executing complex queries for {@link Escultura} entities in the database.
 * The main input is a {@link EsculturaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EsculturaDTO} or a {@link Page} of {@link EsculturaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EsculturaQueryService extends QueryService<Escultura> {

    private final Logger log = LoggerFactory.getLogger(EsculturaQueryService.class);

    private final EsculturaRepository esculturaRepository;

    private final EsculturaMapper esculturaMapper;

    public EsculturaQueryService(EsculturaRepository esculturaRepository, EsculturaMapper esculturaMapper) {
        this.esculturaRepository = esculturaRepository;
        this.esculturaMapper = esculturaMapper;
    }

    /**
     * Return a {@link List} of {@link EsculturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EsculturaDTO> findByCriteria(EsculturaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Escultura> specification = createSpecification(criteria);
        return esculturaMapper.toDto(esculturaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EsculturaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EsculturaDTO> findByCriteria(EsculturaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Escultura> specification = createSpecification(criteria);
        return esculturaRepository.findAll(specification, page).map(esculturaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EsculturaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Escultura> specification = createSpecification(criteria);
        return esculturaRepository.count(specification);
    }

    /**
     * Function to convert {@link EsculturaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Escultura> createSpecification(EsculturaCriteria criteria) {
        Specification<Escultura> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Escultura_.id));
            }
            if (criteria.getAltura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltura(), Escultura_.altura));
            }
            if (criteria.getMaterial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMaterial(), Escultura_.material));
            }
            if (criteria.getEstilo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstilo(), Escultura_.estilo));
            }
            if (criteria.getPeso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPeso(), Escultura_.peso));
            }
            if (criteria.getObjArteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObjArteId(), root -> root.join(Escultura_.objArte, JoinType.LEFT).get(ObjArte_.id))
                    );
            }
        }
        return specification;
    }
}
