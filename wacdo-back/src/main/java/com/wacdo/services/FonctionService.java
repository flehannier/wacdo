package com.wacdo.services;

import com.wacdo.entities.Fonction;
import com.wacdo.exception.FunctionalException;
import lombok.NonNull;

import java.util.List;

public interface FonctionService {
    Fonction save(@NonNull Fonction fct);
    Fonction getById(@NonNull Long id) throws FunctionalException;
    List<Fonction> getAll();
    void deleteById(@NonNull Long id);
}
