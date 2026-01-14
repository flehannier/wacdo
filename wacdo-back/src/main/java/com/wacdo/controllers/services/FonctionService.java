package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Fonction;
import com.wacdo.controllers.exception.FunctionalException;
import lombok.NonNull;

import java.util.List;

public interface FonctionService {
    Fonction save(@NonNull Fonction fct);
    Fonction getById(@NonNull Long id) throws FunctionalException;
    List<Fonction> getAll();
    void deleteById(@NonNull Long id);
}
