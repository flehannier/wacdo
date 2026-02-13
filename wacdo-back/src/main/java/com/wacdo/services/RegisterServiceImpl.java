package com.wacdo.services;

import com.wacdo.dto.CollaborateurRequest;
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
    public Collaborateur register(@NonNull RegisterRequest request)
            throws FunctionalException, TechnicalException {

        Role defaultRole = roleService.findByNameIgnoreCase("USER");

        if (defaultRole == null) {
            throw new FunctionalException("Rôle USER introuvable");
        }

        CollaborateurRequest collab = new CollaborateurRequest(
                null,
                request.getNom(),
                request.getPrenom(),
                request.getEmail(),
                request.getMotDePasse(),
                false,
                defaultRole.getId()
        );

        return collaborateurService.save(collab);
    }
}
