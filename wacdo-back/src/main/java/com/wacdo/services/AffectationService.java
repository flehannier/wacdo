package com.wacdo.services;

import com.wacdo.dto.AffectationRequest;
import com.wacdo.entities.Affectation;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import lombok.NonNull;

import java.util.List;

public interface AffectationService {
    Affectation create(@NonNull AffectationRequest affectation) throws FunctionalException, TechnicalException;
    Affectation update(@NonNull AffectationRequest affectation) throws FunctionalException, TechnicalException;
    Affectation getById(@NonNull Long id) throws FunctionalException;
    List<Affectation> getAll();
    void deleteById(@NonNull Long id);
}
