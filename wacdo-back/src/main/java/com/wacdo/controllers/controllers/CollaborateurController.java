package com.wacdo.controllers.controllers;

import com.wacdo.controllers.dto.CollaborateurDto;
import com.wacdo.controllers.entities.Affectation;
import com.wacdo.controllers.entities.Collaborateur;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import com.wacdo.controllers.services.CollaborateurService;
import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    @PreAuthorize("hasRole('ADMIN')")
    public List<CollaborateurDto> getAll(){
        return collaborateurService.getAll().stream()
                .map(this::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CollaborateurDto getById(@Nonnull @PathVariable("id") Long id) throws FunctionalException {
        return toDto(collaborateurService.getById(id));

    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Collaborateur createOrUpdate(@Nonnull @RequestBody Collaborateur collab) throws FunctionalException, TechnicalException {
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


    /**
     * retour un Colaborateur simplifié
     * @param collaborateur
     * @return CollaborateurDto
     */
    private CollaborateurDto toDto(Collaborateur collaborateur) {
        return new CollaborateurDto(
                collaborateur.getId(),
                collaborateur.getNom(),
                collaborateur.getPrenom(),
                collaborateur.getEmail(),
                collaborateur.isAdministrateur(),
                collaborateur.getRole() != null ? collaborateur.getRole().getName() : null,
                !collaborateur.getAffectations().isEmpty() ? collaborateur.getAffectations() :  null
        );
    }
}
