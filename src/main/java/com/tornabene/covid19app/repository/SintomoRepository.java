package com.tornabene.covid19app.repository;

import com.tornabene.covid19app.domain.Sintomo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Sintomo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SintomoRepository extends JpaRepository<Sintomo, Long> {
}
