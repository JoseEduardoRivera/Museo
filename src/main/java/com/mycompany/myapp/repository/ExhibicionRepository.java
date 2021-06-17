package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Exhibicion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Exhibicion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExhibicionRepository extends JpaRepository<Exhibicion, Long>, JpaSpecificationExecutor<Exhibicion> {}
