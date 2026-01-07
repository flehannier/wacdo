package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Role;
import lombok.NonNull;

public interface RoleService {
    Role save(@NonNull Role role);
}
