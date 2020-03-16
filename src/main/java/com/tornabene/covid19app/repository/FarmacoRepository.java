package com.tornabene.covid19app.repository;

import com.tornabene.covid19app.domain.Farmaco;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Farmaco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FarmacoRepository extends JpaRepository<Farmaco, Long> {
}
