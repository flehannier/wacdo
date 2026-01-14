package com.wacdo.controllers.controllers;

import com.wacdo.controllers.entities.Role;
import com.wacdo.controllers.services.RoleService;
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

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Role createOrUpdate(@Nonnull @RequestBody Role role){
        return roleService.save(role);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        roleService.deleteById(id);
    }
}
