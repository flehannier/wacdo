package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface CollaborateurService{
    Collaborateur save(Collaborateur collab) throws RuntimeException;
    void deleteById(Long id);
    void delete(Collaborateur collaborateur);
    Collaborateur getById(Long id);
    List<Collaborateur> getAll();
    Role addRole(Role role);
}
