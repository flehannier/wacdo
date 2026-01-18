package com.wacdo.dto;

import java.time.LocalDate;

public record AffectationSimpleDto(
        Long id,
        LocalDate dateDebut,
        LocalDate dateFin,
        RestaurantDto restaurant,
        FonctionDto fonction
) {}
