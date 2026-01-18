package com.wacdo.dto;

import com.wacdo.entities.Affectation;

import java.util.List;

public record CollaborateurDto(
        Long id,
        String nom,
        String prenom,
        String email,
        boolean administrateur,
        String role,
        List<AffectationDto> affectations
) {}