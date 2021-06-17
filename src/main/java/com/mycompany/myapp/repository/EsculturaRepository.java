package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Escultura;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Escultura entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EsculturaRepository extends JpaRepository<Escultura, Long>, JpaSpecificationExecutor<Escultura> {}
