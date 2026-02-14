package com.wacdo.controllers;

import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import com.wacdo.services.RestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @Operation(
            summary = "Liste des restaurants",
            description = "Retourne la liste de tous les fonctions"
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @Operation(
            summary = "Fonction par identifiant",
            description = "Retourne le restaurant selon son id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    Restaurant getById(@Nonnull @PathVariable("id" ) Long id ) throws FunctionalException {
        return restaurantService.getById(id);
    }

    @Operation(
            summary = "Création ou mise à jour",
            description = "Retourne le restaurant créé ou mise à jour"
    )
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant createOrUpdate(@Nonnull @RequestBody Restaurant resto) throws FunctionalException {
        return restaurantService.save(resto);
    }

    @Operation(
            summary = "Suppression d'un restaurant",
            description = "Suppression d'un restaurant représenté par son identifiant"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id)  throws FunctionalException {
        restaurantService.deleteById(id);
    }
}
