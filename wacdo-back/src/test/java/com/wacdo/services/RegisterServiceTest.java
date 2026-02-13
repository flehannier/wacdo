package com.wacdo.services;

import com.wacdo.dto.CollaborateurRequest;
import com.wacdo.dto.RegisterRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterServiceImplTest {

    @Mock
    private CollaborateurService collaborateurService;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private RegisterServiceImpl registerService;

    @Test
    void register_shouldCreateUserWithDefaultUserRole() throws Exception {

        // GIVEN
        RegisterRequest request = new RegisterRequest();
        request.setNom("Doe");
        request.setPrenom("John");
        request.setEmail("john@mail.com");
        request.setMotDePasse("Password1");

        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("USER");

        when(roleService.findByNameIgnoreCase("USER")).thenReturn(userRole);

        Collaborateur saved = new Collaborateur();
        saved.setId(1L);

        when(collaborateurService.save(any(CollaborateurRequest.class)))
                .thenReturn(saved);

        // WHEN
        Collaborateur result = registerService.register(request);

        // THEN
        assertNotNull(result);
        assertEquals(1L, result.getId());

        ArgumentCaptor<CollaborateurRequest> captor =
                ArgumentCaptor.forClass(CollaborateurRequest.class);

        verify(collaborateurService).save(captor.capture());

        CollaborateurRequest sentRequest = captor.getValue();

        assertEquals("Doe", sentRequest.nom());
        assertEquals("John", sentRequest.prenom());
        assertEquals("john@mail.com", sentRequest.email());
        assertEquals("Password1", sentRequest.motDePasse());
        assertEquals(2L, sentRequest.roleId());
    }

    @Test
    void register_shouldThrowFunctionalException_whenUserRoleNotFound() throws TechnicalException, FunctionalException {

        // GIVEN
        RegisterRequest request = new RegisterRequest();
        request.setNom("Doe");
        request.setPrenom("John");
        request.setEmail("john@mail.com");
        request.setMotDePasse("Password1");

        when(roleService.findByNameIgnoreCase("USER")).thenReturn(null);

        // WHEN / THEN
        assertThrows(FunctionalException.class,
                () -> registerService.register(request));

        verify(collaborateurService, never()).save(any());
    }

    @Test
    void register_shouldPropagateFunctionalException_fromCollaborateurService() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setNom("Doe");
        request.setPrenom("John");
        request.setEmail("john@mail.com");
        request.setMotDePasse("Password1");

        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("USER");

        when(roleService.findByNameIgnoreCase("USER")).thenReturn(userRole);

        when(collaborateurService.save(any()))
                .thenThrow(new FunctionalException("Erreur métier"));

        assertThrows(FunctionalException.class,
                () -> registerService.register(request));
    }

    @Test
    void register_shouldPropagateTechnicalException_fromCollaborateurService() throws Exception {

        RegisterRequest request = new RegisterRequest();
        request.setNom("Doe");
        request.setPrenom("John");
        request.setEmail("john@mail.com");
        request.setMotDePasse("Password1");

        Role userRole = new Role();
        userRole.setId(2L);
        userRole.setName("USER");

        when(roleService.findByNameIgnoreCase("USER")).thenReturn(userRole);

        when(collaborateurService.save(any()))
                .thenThrow(new TechnicalException("Erreur technique"));

        assertThrows(TechnicalException.class,
                () -> registerService.register(request));
    }
}
