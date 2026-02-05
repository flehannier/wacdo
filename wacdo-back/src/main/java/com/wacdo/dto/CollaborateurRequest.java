package com.wacdo.dto;

public record CollaborateurRequest(
        Long id,
        String nom,
        String prenom,
        String email,
        String motDePasse,
        boolean administrateur,
        Long roleId
) {}