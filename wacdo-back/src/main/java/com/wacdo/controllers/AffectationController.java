package com.wacdo.controllers;

import com.wacdo.entities.Affectation;
import com.wacdo.entities.Restaurant;
import com.wacdo.services.AffectationService;
import com.wacdo.services.RestaurantService;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
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
    List<Affectation> getAll() {
        return affectationService.getAll();
    }

    @GetMapping("/{id}")
    Affectation getById(@Nonnull @PathVariable("id" ) Long id ) {
        return affectationService.getById(id);
    }

    @PostMapping()
    public Affectation create(@Nonnull @RequestBody Affectation affectation) throws EntityNotFoundException, Exception {
        return affectationService.save(affectation);
    }

    @PatchMapping
    public Affectation update(@Nonnull @RequestBody Affectation affectation){
        return affectationService.update(affectation);
    }
}
