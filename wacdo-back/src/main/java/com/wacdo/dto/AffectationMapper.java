package com.wacdo.dto;

import com.wacdo.entities.Affectation;

public class AffectationMapper {

    private AffectationMapper() {
        // utilitaire
    }

    /**
     * retour une Affectation simplifié
     * @param a
     * @return AffectationDto
     */
    public static AffectationDto toDto(Affectation a) {
        return new AffectationDto(
                a.getId(),
                a.getDateDebut(),
                a.getDateFin(),
                new CollaborateurSimpleDto(
                        a.getCollaborateur().getId(),
                        a.getCollaborateur().getNom(),
                        a.getCollaborateur().getPrenom(),
                        a.getCollaborateur().getEmail(),
                        a.getCollaborateur().isAdministrateur(),
                        a.getCollaborateur().getRole().getName()
                ),
                new RestaurantDto(a.getRestaurant().getId(), a.getRestaurant().getNom()),
                new FonctionDto(a.getFonction().getId(), a.getFonction().getIntitule())
        );
    }
}
