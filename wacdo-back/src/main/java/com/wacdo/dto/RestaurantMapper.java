package com.wacdo.dto;

import com.wacdo.entities.Restaurant;
public class RestaurantMapper {
    /**
     * retour un Colaborateur simplifié
     * @param role
     * @return RoleDto
     */
    public static RestaurantDto toDto(Restaurant restaurant) {
        return new RestaurantDto(
                restaurant.getId(),
                restaurant.getNom()
        );
    }
}
