package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.CollecPresta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CollecPresta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CollecPrestaRepository extends JpaRepository<CollecPresta, Long>, JpaSpecificationExecutor<CollecPresta> {}
