package com.wacdo.dto;

public record CollaborateurDto(
        Long id,
        String nom,
        String prenom,
        String email,
        boolean administrateur,
        String role
) {}