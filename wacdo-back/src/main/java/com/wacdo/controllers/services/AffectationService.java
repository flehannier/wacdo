package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Affectation;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;

import java.util.List;

public interface AffectationService {
    Affectation save(@NonNull Affectation affectation) throws FunctionalException, TechnicalException;
    Affectation update(@NonNull Affectation affectation);
    Affectation getById(@NonNull Long id);
    List<Affectation> getAll();
}
