package com.wacdo.services;

import com.wacdo.entities.Affectation;

import java.util.List;

public interface AffectationService {
    Affectation save(Affectation affectation);
    Affectation update(Affectation affectation);
    void deleteByID(Long id);
    void delete(Affectation affectation);
    Affectation getById(Long id);
    List<Affectation> getAll();
}
