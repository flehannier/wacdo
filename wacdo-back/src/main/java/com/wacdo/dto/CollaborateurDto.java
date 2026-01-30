package com.wacdo.dto;

import com.wacdo.entities.Role;

public record CollaborateurDto(
        Long id,
        String nom,
        String prenom,
        String email,
        boolean administrateur,
        Role role
) {}