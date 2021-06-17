package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Pintura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Pintura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PinturaRepository extends JpaRepository<Pintura, Long>, JpaSpecificationExecutor<Pintura> {}
