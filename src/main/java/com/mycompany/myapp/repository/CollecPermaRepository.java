package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CollecPerma;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CollecPerma entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollecPermaRepository extends JpaRepository<CollecPerma, Long>, JpaSpecificationExecutor<CollecPerma> {}
