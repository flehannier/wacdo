package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Fonction;
import lombok.NonNull;

import java.util.List;

public interface FonctionService {
    Fonction save(@NonNull Fonction fct);
    Fonction getById(@NonNull Long id);
    List<Fonction> getAll();
}
