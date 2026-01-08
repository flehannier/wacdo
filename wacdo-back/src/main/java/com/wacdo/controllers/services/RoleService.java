package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Collaborateur;
import com.wacdo.controllers.entities.Role;
import com.wacdo.controllers.exception.FunctionalException;
import lombok.NonNull;

import java.util.List;

public interface RoleService {
    Role save(@NonNull Role role);
    List<Role> getAll();
    Role getById(@NonNull Long roleId) throws FunctionalException;
}
