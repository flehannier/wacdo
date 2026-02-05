package com.wacdo.controllers;

import com.wacdo.entities.Role;
import com.wacdo.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    

    @Operation(
            summary = "Liste des roles",
            description = "Retourne la liste de tous les fonctions"
    )
    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @Operation(
            summary = "Création ou mise à jour",
            description = "Retourne le role créé ou mise à jour"
    )
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Role createOrUpdate(@Nonnull @RequestBody Role role){
        return roleService.save(role);
    }

    @Operation(
            summary = "Suppression d'un role",
            description = "Suppression d'un role représenté par son identifiant"
    )
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        roleService.deleteById(id);
    }
}
