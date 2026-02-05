package com.wacdo.dto;

import com.wacdo.entities.Fonction;
public class FonctionMapper {
    /**
     * retour un Colaborateur simplifié
     * @param role
     * @return RoleDto
     */
    public static FonctionDto toDto(Fonction fonction) {
        return new FonctionDto(
                fonction.getId(),
                fonction.getIntitule()
        );
    }
}
