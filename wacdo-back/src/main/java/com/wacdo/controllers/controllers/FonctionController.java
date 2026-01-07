package com.wacdo.controllers.controllers;

import com.wacdo.controllers.entities.Fonction;
import com.wacdo.controllers.services.FonctionService;
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
    Fonction getById(@Nonnull @PathVariable("id" ) Long id ) {
        return fonctionService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Fonction save(@Nonnull @RequestBody Fonction fct){
        return fonctionService.save(fct);
    }
}
