package com.wacdo.controllers;

import com.wacdo.entities.Fonction;
import com.wacdo.exception.FunctionalException;
import com.wacdo.services.FonctionService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fonction")
@CrossOrigin
public class FonctionController {

    private final FonctionService  fonctionService;

    public FonctionController(FonctionService fonctionService) {
        this.fonctionService = fonctionService;
    }

    @Operation(
            summary = "Liste des fonctions",
            description = "Retourne la liste de tous les fonctions"
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    List<Fonction> getAll() {
        return fonctionService.getAll();
    }

    @Operation(
            summary = "Fonction par identifiant",
            description = "Retourne la Fonction selon son id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    Fonction getById(@Nonnull @PathVariable("id" ) Long id ) throws FunctionalException {
        return fonctionService.getById(id);
    }

    @Operation(
            summary = "Création ou mise à jour",
            description = "Retourne la fonction créé ou mise à jour"
    )
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Fonction createOrUpdate(@Nonnull @RequestBody Fonction fct){
        return fonctionService.save(fct);
    }

    @Operation(
            summary = "Suppression d'une fonction",
            description = "Suppression d'une fonction représenté par son identifiant"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        fonctionService.deleteById(id);
    }
}
