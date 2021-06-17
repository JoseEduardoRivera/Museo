package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Artista;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Artista entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtistaRepository extends JpaRepository<Artista, Long>, JpaSpecificationExecutor<Artista> {}
