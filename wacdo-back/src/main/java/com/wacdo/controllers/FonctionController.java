package com.wacdo.controllers;

import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.services.FonctionService;
import jakarta.annotation.Nonnull;
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
    List<Fonction> getAll() {
        return fonctionService.getAll();
    }

    @GetMapping("/{id}")
    Fonction getById(@Nonnull @PathVariable("id" ) Long id ) {
        return fonctionService.getById(id);
    }

    @PostMapping()
    public Fonction save(@Nonnull @RequestBody Fonction fct){
        return fonctionService.save(fct);
    }
}
