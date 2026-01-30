package com.wacdo.dto;

import com.wacdo.entities.Affectation;
import com.wacdo.entities.Collaborateur;

import java.util.stream.Collectors;

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
                "",
                collaborateur.isAdministrateur(),
                collaborateur.getRole().getName(),
                collaborateur.getAffectations()
                        .stream()
                        .map(affectation -> affectation.getFonction().getIntitule())
                        .findFirst()
                        .orElse(null)
                ,
                collaborateur.getAffectations()
                        .stream()
                        .map(affectation -> affectation.getRestaurant().getNom())
                        .findFirst()
                        .orElse(null)

        );
    }
}
