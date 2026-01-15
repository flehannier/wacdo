package com.wacdo.controllers;

import com.wacdo.entities.Fonction;
import com.wacdo.exception.FunctionalException;
import com.wacdo.services.FonctionService;
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

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    List<Fonction> getAll() {
        return fonctionService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    Fonction getById(@Nonnull @PathVariable("id" ) Long id ) throws FunctionalException {
        return fonctionService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Fonction save(@Nonnull @RequestBody Fonction fct){
        return fonctionService.save(fct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        fonctionService.deleteById(id);
    }
}
