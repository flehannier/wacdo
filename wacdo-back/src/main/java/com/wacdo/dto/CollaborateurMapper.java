package com.wacdo.dto;

import com.wacdo.entities.Collaborateur;
public class CollaborateurMapper {

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
                RoleMapper.toDto(collaborateur.getRole()),
                collaborateur.getAffectations()
                    .stream()
                    .map(affectation -> affectation.getFonction())
                    .findFirst()
                    .map(FonctionMapper::toDto)
                    .orElse(null), // Returns null if the list was empty,
                collaborateur.getAffectations()
                    .stream()
                    .map(affectation -> affectation.getRestaurant())
                    .findFirst()
                    .map(RestaurantMapper::toDto)
                    .orElse(null)

        );
    }
}
