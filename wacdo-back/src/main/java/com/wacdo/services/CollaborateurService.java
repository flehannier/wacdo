package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Restaurant;

import java.util.List;

public interface CollaborateurService {
    Collaborateur save(Collaborateur collab);
    Collaborateur update(Collaborateur collab);
    void deleteById(Long id);
    void delete(Collaborateur collaborateur);
    Collaborateur getById(Long id);
    List<Collaborateur> getAll();
}
