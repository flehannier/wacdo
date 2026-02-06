package com.wacdo.dto;

import com.wacdo.entities.Collaborateur;
public class CollaborateurMapper {

    /**
     * retour un Colaborateur simplifié
     * @param collaborateur
     * @return CollaborateurDto
     */
    public static CollaborateurDto toDto(Collaborateur collaborateur) {
        FonctionDto fonctionDto = null;
        if(collaborateur.getAffectations() != null) {
           fonctionDto = collaborateur.getAffectations()
                    .stream()
                    .map(aff -> aff.getFonction())
                    .findFirst()
                    .map(FonctionMapper::toDto)
                    .orElse(null);
        }
        RestaurantDto restaurantDto = null; 
         if(collaborateur.getAffectations() != null) {
            restaurantDto = collaborateur.getAffectations()
                    .stream()
                    .map(affectation -> affectation.getRestaurant())
                    .findFirst()
                    .map(RestaurantMapper::toDto)
                    .orElse(null);
         }
        return new CollaborateurDto(
                collaborateur.getId(),
                collaborateur.getNom(),
                collaborateur.getPrenom(),
                collaborateur.getEmail(),
                "",
                collaborateur.isAdministrateur(),
                RoleMapper.toDto(collaborateur.getRole()),
                fonctionDto,
                restaurantDto
        );
    }
}
