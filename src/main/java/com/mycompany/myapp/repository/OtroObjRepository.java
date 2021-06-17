package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.OtroObj;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OtroObj entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OtroObjRepository extends JpaRepository<OtroObj, Long>, JpaSpecificationExecutor<OtroObj> {}
