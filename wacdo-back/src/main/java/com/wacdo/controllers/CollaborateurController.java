package com.wacdo.controllers;

import com.wacdo.entities.Collaborateur;
import com.wacdo.services.CollaborateurService;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collaborateur")
@CrossOrigin
public class CollaborateurController {

    //@Autowired
    private final CollaborateurService collaborateurService;

    public CollaborateurController(CollaborateurService collaborateurService) {
        this.collaborateurService = collaborateurService;
    }

    @GetMapping()
    public List<Collaborateur> getAllCollaborateurs(){
        return collaborateurService.getAllCollaborateur();
    }

    @GetMapping("/{id}")
    public Collaborateur getCollaborateurById(@Nonnull @PathVariable("id") Long id){
        return collaborateurService.getCollaborateur(id);
    }

    @PostMapping()
    public Collaborateur createCollaborateur(@Nonnull @RequestBody Collaborateur collaborateur){
        return collaborateurService.saveCollaborateur(collaborateur);
    }

    @PutMapping()
    public Collaborateur updateCollaborateur(@Nonnull @RequestBody Collaborateur collaborateur){
        return collaborateurService.updateCollaboreteur(collaborateur);
    }

    @DeleteMapping("/{id}")
    public void deleteCollaborateur(@Nonnull @PathVariable("id") Long id){
        collaborateurService.deleteCollaborateur(id);
    }

    @GetMapping("/restaurant/{id}")
    public List<Collaborateur> getCollaborateurByRestaurantId(@Nonnull @PathVariable("id") Long id){
        return collaborateurService.findByRestaurantId(id);
    }

    @GetMapping("/byName/{nom}")
    public List<Collaborateur> fondByNomCollaborateur(@Nonnull @PathVariable("nom") String nom){
        return collaborateurService.findByNomContains(nom);
    }
}
