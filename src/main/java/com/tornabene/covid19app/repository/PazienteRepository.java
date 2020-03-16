package com.tornabene.covid19app.repository;

import com.tornabene.covid19app.domain.Paziente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Paziente entity.
 */
@Repository
public interface PazienteRepository extends JpaRepository<Paziente, Long> {

    @Query(value = "select distinct paziente from Paziente paziente left join fetch paziente.sintomis left join fetch paziente.farmaciUsatis left join fetch paziente.altrePatologies",
        countQuery = "select count(distinct paziente) from Paziente paziente")
    Page<Paziente> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct paziente from Paziente paziente left join fetch paziente.sintomis left join fetch paziente.farmaciUsatis left join fetch paziente.altrePatologies")
    List<Paziente> findAllWithEagerRelationships();

    @Query("select paziente from Paziente paziente left join fetch paziente.sintomis left join fetch paziente.farmaciUsatis left join fetch paziente.altrePatologies where paziente.id =:id")
    Optional<Paziente> findOneWithEagerRelationships(@Param("id") Long id);
}
