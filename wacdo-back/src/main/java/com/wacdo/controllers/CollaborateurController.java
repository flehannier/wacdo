package com.wacdo.controllers;

import com.wacdo.dto.CollaborateurDto;
import com.wacdo.entities.Collaborateur;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.services.CollaborateurService;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
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
