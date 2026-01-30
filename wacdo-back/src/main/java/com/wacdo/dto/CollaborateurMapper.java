package com.wacdo.dto;

import com.wacdo.entities.Collaborateur;

public class CollaborateurMapper {
    private CollaborateurMapper() {
    }

    /**
     * retour un Colaborateur simplifié
     * @param collaborateur
     * @return CollaborateurDto
     */
    public static CollaborateurDto toDto(Collaborateur collaborateur) {
        return new CollaborateurDto(
                collaborateur.getId(),
                collaborateur.getNom(),
                collaborateur.getPrenom(),
                collaborateur.getEmail(),
                collaborateur.isAdministrateur(),
                collaborateur.getRole() != null ? collaborateur.getRole().getName() : null
        );
    }
}
