package com.wacdo.dto;

public record CollaborateurSimpleDto(
        Long id,
        String nom,
        String prenom,
        String email,
        boolean administrateur,
        String role) {}