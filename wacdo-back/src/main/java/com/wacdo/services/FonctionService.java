package com.wacdo.services;

import com.wacdo.entities.Fonction;

import java.util.List;

public interface FonctionService {
    Fonction save(Fonction fct);
    Fonction getById(Long id);
    List<Fonction> getAll();
}
