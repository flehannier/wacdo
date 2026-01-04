package com.wacdo.controllers;

import com.wacdo.entities.Role;
import com.wacdo.services.RoleService;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public Role save(@Nonnull @RequestBody Role role){
        return roleService.save(role);
    }
}
