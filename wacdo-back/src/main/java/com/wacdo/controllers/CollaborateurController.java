package com.wacdo.controllers;

import com.wacdo.dto.CollaborateurDto;
import com.wacdo.dto.CollaborateurMapper;
import com.wacdo.dto.CollaborateurRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.services.CollaborateurService;

import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(
            summary = "Liste des collaborateurs",
            description = "Retourne la liste de tous les collaborateur"
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<CollaborateurDto> getAll(){
        return collaborateurService.getAll().stream()
                .map(CollaborateurMapper::toDto)
                .toList();
    }

    @Operation(
            summary = "Collaborateur par identifiant",
            description = "Retourne le collaborateur selon son id"
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CollaborateurDto getById(@Nonnull @PathVariable("id") Long id) throws FunctionalException {
        return CollaborateurMapper.toDto(collaborateurService.getById(id));
    }

    @Operation(
            summary = "Collaborateur par nom",
            description = "Retourne le collaborateur selon son nom"
    )
    @GetMapping("/byUsername/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public CollaborateurDto getByEmail(@Nonnull @PathVariable("name") String name) throws FunctionalException {
        return CollaborateurMapper.toDto(collaborateurService.getByEmail(name));
    }
    
    @Operation(
            summary = "Création ou mise à jour",
            description = "Retourne le collaborateur créé ou mise à jour"
    )
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public CollaborateurDto createOrUpdate(@Nonnull @RequestBody CollaborateurRequest collab) throws FunctionalException, TechnicalException {
       return CollaborateurMapper.toDto(collaborateurService.save(collab));
    }

    @Operation(
            summary = "Suppression d'un collaborateur",
            description = "Suppression d'un collaborateur représenté par son identifiant"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id) throws FunctionalException{
        collaborateurService.deleteById(id);
    }

    @Operation(
            summary = "Suppression d'un collaborateur",
            description = "Suppression d'un collaborateur récupérer du body de la query"
    )
    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@Nonnull  @RequestBody Collaborateur collab){
        collaborateurService.delete(collab);
    }



}
