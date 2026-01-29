package com.wacdo.services;

import com.wacdo.WacdoApplication;
import com.wacdo.dto.RegisterRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.repositories.CollaborateurRepository;
import com.wacdo.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@AutoConfigureMockMvc
@SpringBootTest(classes = WacdoApplication.class)
class RegisterServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RegisterService registerService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private CollaborateurRepository collaborateurRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void shouldRegisterUser() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setNom("Collaborateur");
        registerRequest.setPrenom("Prenom");
        registerRequest.setEmail("test@email.com");
        registerRequest.setMotDePasse("MotDePasse1");

        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        when(roleService.findByNameIgnoreCase("USER"))
                .thenReturn(role);

        when(roleRepository.findByNameIgnoreCase("USER"))
                .thenReturn(role);

        when(collaborateurRepository.save(any(Collaborateur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(roleRepository.findById(1L))
                .thenReturn(Optional.of(role));

        Collaborateur col = registerService.register(registerRequest);

        assertThat(col).isNotNull();
        assertThat(col.getNom()).isEqualTo("Collaborateur");
        assertThat(col.getPrenom()).isEqualTo("Prenom");
        assertThat(col.getEmail()).isEqualTo("test@email.com");
        assertThat(col.getRole().getName()).isEqualTo("USER");
        assertThat(col.isAdministrateur()).isFalse();
        assertThat(col.getMotDePasse()).isNotBlank(); // password encodé

    }
}
