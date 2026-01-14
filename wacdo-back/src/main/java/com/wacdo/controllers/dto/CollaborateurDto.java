package com.wacdo.controllers.dto;

import com.wacdo.controllers.entities.Affectation;

import java.util.List;

public record CollaborateurDto(
        Long id,
        String nom,
        String prenom,
        String email,
        boolean administrateur,
        String role,
        List<Affectation> affectations
) {}