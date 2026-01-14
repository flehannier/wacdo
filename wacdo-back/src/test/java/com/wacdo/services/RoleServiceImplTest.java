package com.wacdo.services;

import com.wacdo.controllers.WacdoApplication;
import com.wacdo.controllers.entities.Role;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.repositories.RoleRepository;
import com.wacdo.controllers.services.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WacdoApplication.class)
public class RoleServiceImplTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void shouldCreateRole() {
        Role role = new Role();
        role.setName("Manager");
        role.setDescription("");

        when(roleRepository.save(any(Role.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Role result = roleService.save(role);

        assertThat(result.getName()).isEqualTo("Manager");
    }

    @Test
    void shouldReturnRole() throws FunctionalException {
        Role role = new Role();
        role.setId(1L);

        when(roleRepository.findById(1L))
                .thenReturn(Optional.of(role));

        Role result = roleService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnList() {
        when(roleService.getAll())
                .thenReturn(List.of(new Role(), new Role()));

        List<Role> result = roleService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldThrowException_whenRoleNotFound() throws FunctionalException {
        when(roleRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> roleService.getById(1L))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Role introuvable");
    }
}