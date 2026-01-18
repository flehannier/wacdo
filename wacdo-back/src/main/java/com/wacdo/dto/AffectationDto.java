package com.wacdo.dto;

import java.time.LocalDate;

public record AffectationDto(
        Long id,
        LocalDate dateDebut,
        LocalDate dateFin,
        CollaborateurDto collaborateur,
        RestaurantDto restaurant,
        FonctionDto fonction
) {}
