package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.OtroObj;
import com.mycompany.myapp.repository.OtroObjRepository;
import com.mycompany.myapp.service.criteria.OtroObjCriteria;
import com.mycompany.myapp.service.dto.OtroObjDTO;
import com.mycompany.myapp.service.mapper.OtroObjMapper;
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
 * Service for executing complex queries for {@link OtroObj} entities in the database.
 * The main input is a {@link OtroObjCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OtroObjDTO} or a {@link Page} of {@link OtroObjDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OtroObjQueryService extends QueryService<OtroObj> {

    private final Logger log = LoggerFactory.getLogger(OtroObjQueryService.class);

    private final OtroObjRepository otroObjRepository;

    private final OtroObjMapper otroObjMapper;

    public OtroObjQueryService(OtroObjRepository otroObjRepository, OtroObjMapper otroObjMapper) {
        this.otroObjRepository = otroObjRepository;
        this.otroObjMapper = otroObjMapper;
    }

    /**
     * Return a {@link List} of {@link OtroObjDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OtroObjDTO> findByCriteria(OtroObjCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<OtroObj> specification = createSpecification(criteria);
        return otroObjMapper.toDto(otroObjRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OtroObjDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OtroObjDTO> findByCriteria(OtroObjCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<OtroObj> specification = createSpecification(criteria);
        return otroObjRepository.findAll(specification, page).map(otroObjMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OtroObjCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<OtroObj> specification = createSpecification(criteria);
        return otroObjRepository.count(specification);
    }

    /**
     * Function to convert {@link OtroObjCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<OtroObj> createSpecification(OtroObjCriteria criteria) {
        Specification<OtroObj> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), OtroObj_.id));
            }
            if (criteria.getTipo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTipo(), OtroObj_.tipo));
            }
            if (criteria.getEstilo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEstilo(), OtroObj_.estilo));
            }
            if (criteria.getObjArteId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getObjArteId(), root -> root.join(OtroObj_.objArte, JoinType.LEFT).get(ObjArte_.id))
                    );
            }
        }
        return specification;
    }
}
