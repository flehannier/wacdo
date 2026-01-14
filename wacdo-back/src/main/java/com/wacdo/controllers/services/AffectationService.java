package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Affectation;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;

import java.util.List;

public interface AffectationService {
    Affectation save(@NonNull Affectation affectation) throws FunctionalException, TechnicalException;
    Affectation getById(@NonNull Long id) throws FunctionalException;
    List<Affectation> getAll();
    void deleteById(@NonNull Long id);
}
