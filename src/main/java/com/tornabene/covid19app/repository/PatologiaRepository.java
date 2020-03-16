package com.tornabene.covid19app.repository;

import com.tornabene.covid19app.domain.Patologia;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Patologia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatologiaRepository extends JpaRepository<Patologia, Long> {
}
