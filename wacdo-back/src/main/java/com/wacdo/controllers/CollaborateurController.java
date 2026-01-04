package com.wacdo.controllers;

import com.wacdo.entities.Collaborateur;
import com.wacdo.services.CollaborateurService;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @PreAuthorize("hasRole('ADMIN')")
    public Collaborateur create(@Nonnull @RequestBody Collaborateur collab) throws RuntimeException{
       return collaborateurService.save(collab);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        collaborateurService.deleteById(id);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@Nonnull  @RequestBody Collaborateur collab){
        collaborateurService.delete(collab);
    }
}
