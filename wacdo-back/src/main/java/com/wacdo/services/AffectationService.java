package com.wacdo.services;

import com.wacdo.entities.Affectation;
import com.wacdo.exception.FunctionalException;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface AffectationService {
    Affectation save(Affectation affectation) throws EntityNotFoundException, FunctionalException;
    Affectation update(Affectation affectation);
    Affectation getById(Long id);
    List<Affectation> getAll();
}
