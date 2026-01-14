package com.wacdo.controllers.services;

import com.wacdo.controllers.dto.CollaborateurDto;
import com.wacdo.controllers.entities.Collaborateur;
import com.wacdo.controllers.entities.Role;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface CollaborateurService{
    Collaborateur save(Collaborateur collab) throws FunctionalException, TechnicalException;
    void deleteById(@NonNull Long id);
    void delete(@NonNull Collaborateur collaborateur);
    Collaborateur getById(@NonNull Long id) throws FunctionalException;
    List<Collaborateur> getAll();
}
