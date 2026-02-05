package com.wacdo.repositories;

import com.wacdo.entities.Affectation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation, Long> {
    List<Affectation> findByCollaborateurId(Long id);
}
