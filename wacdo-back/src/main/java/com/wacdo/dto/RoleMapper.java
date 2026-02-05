package com.wacdo.dto;

import com.wacdo.entities.Role;
public class RoleMapper {
    /**
     * retour un Colaborateur simplifié
     * @param role
     * @return RoleDto
     */
    public static RoletDto toDto(Role role) {
        return new RoletDto(
                role.getId(),
                role.getName()
        );
    }
}
