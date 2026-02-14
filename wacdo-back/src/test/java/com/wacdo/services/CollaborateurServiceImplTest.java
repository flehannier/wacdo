package com.wacdo.services;

import com.wacdo.dto.CollaborateurRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.repositories.CollaborateurRepository;
import com.wacdo.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollaborateurServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private CollaborateurRepository collaborateurRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CollaborateurServiceImpl service;

    private Role adminRole;
    private Role userRole;

    @BeforeEach
    void setup() {
        adminRole = new Role();
        adminRole.setId(1L);
        adminRole.setName("ADMIN");

        userRole = new Role();
        userRole.setId(2L);
        userRole.setName("USER");
    }

    // ======================================================
    // CREATE
    // ======================================================

    @Test
    void save_shouldCreateUserSuccessfully() throws Exception {

        CollaborateurRequest request = mock(CollaborateurRequest.class);

        when(request.getId()).thenReturn(null);
        when(request.getNom()).thenReturn("Doe");
        when(request.getPrenom()).thenReturn("John");
        when(request.getEmail()).thenReturn("john@mail.com");
        when(request.getMotDePasse()).thenReturn("Password1");
        when(request.getRoleId()).thenReturn(2L);

        when(collaborateurRepository.findByEmail("john@mail.com")).thenReturn(null);
        when(roleRepository.findAll()).thenReturn(List.of(adminRole, userRole));
        when(roleRepository.findByNameIgnoreCase("USER")).thenReturn(userRole);
        when(passwordEncoder.encode("Password1")).thenReturn("encodedPassword");
        when(collaborateurRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Collaborateur result = service.save(request);

        assertNotNull(result);
        assertEquals("Doe", result.getNom());
        assertEquals("encodedPassword", result.getMotDePasse());
        assertFalse(result.isAdministrateur());
    }

    @Test
    void save_shouldCreateAdminSuccessfully() throws Exception {

        CollaborateurRequest request = mock(CollaborateurRequest.class);

        when(request.getId()).thenReturn(null);
        when(request.getNom()).thenReturn("Admin");
        when(request.getPrenom()).thenReturn("Super");
        when(request.getEmail()).thenReturn("admin@mail.com");
        when(request.getMotDePasse()).thenReturn("Password1");
        when(request.getRoleId()).thenReturn(1L);

        when(collaborateurRepository.findByEmail("admin@mail.com")).thenReturn(null);
        when(roleRepository.findAll()).thenReturn(List.of(adminRole, userRole));
        when(roleRepository.findByNameIgnoreCase("ADMIN")).thenReturn(adminRole);
        when(passwordEncoder.encode("Password1")).thenReturn("encodedPassword");
        when(collaborateurRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Collaborateur result = service.save(request);

        assertTrue(result.isAdministrateur());
        assertEquals(adminRole, result.getRole());
    }

    @Test
    void save_shouldThrowException_whenEmailAlreadyExists() {

        CollaborateurRequest request = mock(CollaborateurRequest.class);

        when(request.getId()).thenReturn(null);
        when(request.getEmail()).thenReturn("existing@mail.com");

        when(collaborateurRepository.findByEmail("existing@mail.com"))
                .thenReturn(new Collaborateur());

        assertThrows(FunctionalException.class,
                () -> service.save(request));
    }

    @Test
    void save_shouldThrowException_whenPasswordWeak() {

        CollaborateurRequest request = mock(CollaborateurRequest.class);

        when(request.getId()).thenReturn(null);
        when(request.getNom()).thenReturn("Doe");
        when(request.getPrenom()).thenReturn("John");
        when(request.getEmail()).thenReturn("john@mail.com");
        when(request.getMotDePasse()).thenReturn("weak");
        when(request.getRoleId()).thenReturn(2L);

        when(collaborateurRepository.findByEmail(any())).thenReturn(null);
        when(roleRepository.findAll()).thenReturn(List.of(adminRole, userRole));
        when(roleRepository.findByNameIgnoreCase("USER")).thenReturn(userRole);

        assertThrows(FunctionalException.class,
                () -> service.save(request));
    }

    @Test
    void save_shouldThrowTechnicalException_whenEncodingFails() {

        CollaborateurRequest request = mock(CollaborateurRequest.class);

        when(request.getId()).thenReturn(null);
        when(request.getNom()).thenReturn("Doe");
        when(request.getPrenom()).thenReturn("John");
        when(request.getEmail()).thenReturn("john@mail.com");
        when(request.getMotDePasse()).thenReturn("Password1");
        when(request.getRoleId()).thenReturn(2L);

        when(collaborateurRepository.findByEmail(any())).thenReturn(null);
        when(roleRepository.findAll()).thenReturn(List.of(adminRole, userRole));
        when(roleRepository.findByNameIgnoreCase("USER")).thenReturn(userRole);
        when(passwordEncoder.encode(any())).thenReturn("");

        assertThrows(TechnicalException.class,
                () -> service.save(request));
    }

    // ======================================================
    // UPDATE
    // ======================================================

    @Test
    void save_shouldUpdateSuccessfully() throws Exception {

        Collaborateur existing = new Collaborateur();
        existing.setId(10L);

        CollaborateurRequest request = mock(CollaborateurRequest.class);

        when(request.getId()).thenReturn(10L);
        when(request.getNom()).thenReturn("Updated");
        when(request.getPrenom()).thenReturn("User");
        when(request.getEmail()).thenReturn("updated@mail.com");
        when(request.getMotDePasse()).thenReturn(null);
        when(request.getRoleId()).thenReturn(2L);

        when(collaborateurRepository.findById(10L))
                .thenReturn(Optional.of(existing));

        when(roleRepository.findAll()).thenReturn(List.of(adminRole, userRole));
        when(roleRepository.findByNameIgnoreCase("USER")).thenReturn(userRole);
        when(collaborateurRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Collaborateur result = service.save(request);

        assertEquals("Updated", result.getNom());
    }

    @Test
    void save_shouldThrowException_whenUpdateNotFound() {

        CollaborateurRequest request = mock(CollaborateurRequest.class);
        when(request.getId()).thenReturn(99L);

        when(collaborateurRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(FunctionalException.class,
                () -> service.save(request));
    }

    // ======================================================
    // GET
    // ======================================================

    @Test
    void getById_shouldReturnCollaborateur() throws Exception {

        Collaborateur collab = new Collaborateur();
        collab.setId(1L);

        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.of(collab));

        Collaborateur result = service.getById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void getById_shouldThrowException_whenNotFound() {

        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(FunctionalException.class,
                () -> service.getById(1L));
    }

    @Test
    void getAll_shouldReturnList() {

        when(collaborateurRepository.findAll())
                .thenReturn(List.of(new Collaborateur()));

        assertEquals(1, service.getAll().size());
    }

    // ======================================================
    // DELETE
    // ======================================================

    @Test
    void deleteById_shouldCallRepository() throws FunctionalException {

        service.deleteById(1L);

        verify(collaborateurRepository).deleteById(1L);
    }

    @Test
    void delete_shouldCallRepository() {

        Collaborateur collab = new Collaborateur();
        collab.setId(1L);

        service.delete(collab);

        verify(collaborateurRepository).deleteById(1L);
    }
}
