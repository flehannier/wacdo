package com.wacdo.services;

import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import lombok.NonNull;

import java.util.List;

public interface RoleService {
    Role save(@NonNull Role role);
    List<Role> getAll();
    Role getById(@NonNull Long roleId) throws FunctionalException;
    void deleteById(@NonNull Long id);
    Role findByNameIgnoreCase(@NonNull String roleName);
}
