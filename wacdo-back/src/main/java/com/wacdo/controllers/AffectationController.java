package com.wacdo.controllers;

import com.wacdo.dto.*;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.services.AffectationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/affectation")
@CrossOrigin
public class AffectationController {

    private final AffectationService affectationService;

    public AffectationController(AffectationService affectationService) {
        this.affectationService = affectationService;
    }
    
    @Operation(
            summary = "Liste des affectations",
            description = "Retourne les affectations"
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    List<AffectationDto> getAll() {
        return affectationService.getAll();
    }

    @Operation(
            summary = "Retourne une affectation",
            description = "Retourne une affectation selon l'identifiant en paramètre"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    AffectationDto getById(@Nonnull @PathVariable("id" ) Long id ) throws FunctionalException {
        return AffectationMapper.toDto(affectationService.getById(id));
    }

    @Operation(
            summary = "Mise à jour ou creation d'une affectation",
            description = "Retourne l'affectation mise à jour ou crée"
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public AffectationDto createOrUpdate(@RequestBody AffectationRequest request) throws FunctionalException, TechnicalException {
        if(request.id() != null) {
            return AffectationMapper.toDto(affectationService.update(request));
        } else {
            return AffectationMapper.toDto(affectationService.create(request));
        }
    }

    @Operation(
            summary = "Suppression d'une affectation",
            description = "Retourne d'une affectation"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        affectationService.deleteById(id);
    }
}
