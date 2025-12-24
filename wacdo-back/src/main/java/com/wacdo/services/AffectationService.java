package com.wacdo.services;

import com.wacdo.entities.Affectation;

import java.util.List;

public interface AffectationService {
    Affectation save(Affectation affectation);
    Affectation update(Affectation affectation);
    Affectation getById(Long id);
    List<Affectation> getAll();
}
