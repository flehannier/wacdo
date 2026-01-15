package com.wacdo.services;

import com.wacdo.dto.RegisterRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    private final CollaborateurService collaborateurService;

    private final RoleService roleService;

    public RegisterServiceImpl(CollaborateurService collaborateurService, RoleService roleService) {
        this.collaborateurService = collaborateurService;
        this.roleService = roleService;
    }

    @Override
    public Collaborateur register(@NonNull RegisterRequest request) throws FunctionalException, TechnicalException {
        Collaborateur collaborateur = new Collaborateur();

        collaborateur.setEmail(request.getEmail());
        collaborateur.setNom(request.getNom());
        collaborateur.setPrenom(request.getPrenom());
        collaborateur.setMotDePasse(request.getMotDePasse());

        Role role = roleService.findByNameIgnoreCase("USER");
        if(role == null){
            throw new FunctionalException("Le role USER n'existe pas");
        }
        collaborateur.setRole(role);

        return collaborateurService.save(collaborateur);
    }
}
