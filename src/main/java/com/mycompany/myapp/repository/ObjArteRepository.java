package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ObjArte;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ObjArte entity.
 */
@Repository
public interface ObjArteRepository extends JpaRepository<ObjArte, Long>, JpaSpecificationExecutor<ObjArte> {
    @Query(
        value = "select distinct objArte from ObjArte objArte left join fetch objArte.artistas",
        countQuery = "select count(distinct objArte) from ObjArte objArte"
    )
    Page<ObjArte> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct objArte from ObjArte objArte left join fetch objArte.artistas")
    List<ObjArte> findAllWithEagerRelationships();

    @Query("select objArte from ObjArte objArte left join fetch objArte.artistas where objArte.id =:id")
    Optional<ObjArte> findOneWithEagerRelationships(@Param("id") Long id);
}
