package com.wacdo.controllers;

import com.wacdo.entities.Collaborateur;
import com.wacdo.services.CollaborateurService;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collaborateur")
@CrossOrigin
public class CollaborateurController {

    private final CollaborateurService collaborateurService;

    public CollaborateurController(CollaborateurService collaborateurService) {
        this.collaborateurService = collaborateurService;
    }

    @GetMapping()
    public List<Collaborateur> getAll(){
        return collaborateurService.getAll();
    }

    @GetMapping("/{id}")
    public Collaborateur getById(@Nonnull @PathVariable("id") Long id){
        return collaborateurService.getById(id);
    }

    @PostMapping()
    public Collaborateur create(@Nonnull @RequestBody Collaborateur collab){
        return collaborateurService.save(collab);
    }

    @PatchMapping()
    public Collaborateur update(@Nonnull @RequestBody Collaborateur collab){
        return collaborateurService.update(collab);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        collaborateurService.deleteById(id);
    }

    @DeleteMapping()
    public void delete(@Nonnull  @RequestBody Collaborateur collab){
        collaborateurService.delete(collab);
    }
}
