package com.wacdo.dto;

import java.time.LocalDate;

public record AffectationRequest(
        Long id,
        LocalDate dateDebut,
        LocalDate dateFin,
        Long collaborateurId,
        Long restaurantId,
        Long fonctionId
) {}