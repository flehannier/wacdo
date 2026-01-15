package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import lombok.NonNull;

import java.util.List;

public interface CollaborateurService{
    Collaborateur save(Collaborateur collab) throws FunctionalException, TechnicalException;
    void deleteById(@NonNull Long id);
    void delete(@NonNull Collaborateur collaborateur);
    Collaborateur getById(@NonNull Long id) throws FunctionalException;
    List<Collaborateur> getAll();
    Collaborateur findByEmail(@NonNull String email) throws FunctionalException;
}
