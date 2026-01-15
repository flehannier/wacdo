package com.wacdo.services;

import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.repositories.RoleRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(@NonNull Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(@NonNull Long roleId) throws FunctionalException {
        return roleRepository.findById(roleId).orElseThrow(() -> new FunctionalException("Role introuvable"));
    }

    @Override
    public void deleteById(@NonNull Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByNameIgnoreCase(@NonNull String roleName) {
        return roleRepository.findByNameIgnoreCase(roleName);
    }
}
