package com.wacdo.controllers.controllers;

import com.wacdo.controllers.entities.Affectation;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import com.wacdo.controllers.services.AffectationService;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
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
    List<Affectation> getAll() {
        return affectationService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    Affectation getById(@Nonnull @PathVariable("id" ) Long id ) throws FunctionalException {
        return affectationService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Affectation createOrUpdate(@Nonnull @RequestBody Affectation affectation) throws FunctionalException, TechnicalException {
        return affectationService.save(affectation);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        affectationService.deleteById(id);
    }
}
