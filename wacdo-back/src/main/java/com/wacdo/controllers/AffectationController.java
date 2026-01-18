package com.wacdo.controllers;

import com.wacdo.dto.*;
import com.wacdo.entities.Affectation;
import com.wacdo.entities.Collaborateur;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.services.AffectationService;
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

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    List<AffectationDto> getAll() {
        return affectationService.getAll().stream()
                .map(AffectationMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    AffectationDto getById(@Nonnull @PathVariable("id" ) Long id ) throws FunctionalException {
        return AffectationMapper.toDto(affectationService.getById(id));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public AffectationDto createOrUpdate(@Nonnull @RequestBody AffectationDto affectation) throws FunctionalException, TechnicalException {
        return AffectationMapper.toDto(affectationService.save(affectation));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        affectationService.deleteById(id);
    }
}
