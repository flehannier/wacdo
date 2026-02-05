package com.wacdo.services;

import com.wacdo.WacdoApplication;
import com.wacdo.dto.CollaborateurRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.repositories.CollaborateurRepository;
import com.wacdo.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = WacdoApplication.class)
class CollaborateurServiceImplTest {

    @Autowired
    private CollaborateurService collaborateurService;

    @MockBean
    private CollaborateurRepository collaborateurRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldCreateCollaborateur_withStrongPassword() throws FunctionalException, TechnicalException {
        Collaborateur collab = new Collaborateur();
        collab.setNom("Doe");
        collab.setPrenom("John");
        collab.setEmail("john.doe@test.com");
        collab.setMotDePasse("Password123");

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        collab.setRole(role);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded-password");

        when(collaborateurRepository.save(any(Collaborateur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));;
        CollaborateurRequest col = new CollaborateurRequest(
                collab.getId(),
                collab.getNom(),
                collab.getPrenom(),
                collab.getEmail(),
                collab.getMotDePasse(),
                collab.isAdministrateur(),
                collab.getRole().getId()
                );
        Collaborateur result = collaborateurService.save(col);

        assertThat(result.getMotDePasse()).isEqualTo("encoded-password");
    }

    @Test
    void shouldThrowException_whenPasswordIsNotStrong() {
        Collaborateur collab = new Collaborateur();
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        collab.setRole(role);

        collab.setEmail("test@test.com");
        collab.setMotDePasse("123");

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
     CollaborateurRequest col = new CollaborateurRequest(
                collab.getId(),
                collab.getNom(),
                collab.getPrenom(),
                collab.getEmail(),
                collab.getMotDePasse(),
                collab.isAdministrateur(),
                collab.getRole().getId()
                );
        assertThatException()
                .isThrownBy(() -> collaborateurService.save(col))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("mot de passe");
    }

    @Test
    void shouldUpdateCollaborateur_withoutChangingPassword() throws FunctionalException, TechnicalException {
        Collaborateur existing = new Collaborateur();
        existing.setId(1L);
        existing.setNom("Doe");
        existing.setPrenom("John");
        existing.setEmail("john.doe@test.com");
        existing.setMotDePasse("encoded-old-password");

        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(collaborateurRepository.save(any(Collaborateur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Collaborateur update = new Collaborateur();
        update.setId(1L);
        update.setNom("NewName");
        update.setPrenom("John");
        update.setEmail("john.NewName@test.com");
        update.setMotDePasse(""); // pas de changement

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        update.setRole(role);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
     CollaborateurRequest col = new CollaborateurRequest(
                update.getId(),
                update.getNom(),
                update.getPrenom(),
                update.getEmail(),
                update.getMotDePasse(),
                update.isAdministrateur(),
                update.getRole().getId()
                );
        Collaborateur result = collaborateurService.save(col);

        assertThat(result).isNotNull();
        assertThat(result.getMotDePasse()).isEqualTo("encoded-old-password");
        assertThat(result.getNom()).isEqualTo("NewName");
        assertThat(result.getEmail()).isEqualTo("john.NewName@test.com");
    }

    @Test
    void shouldThrowException_whenUpdatingUnknownCollaborateur() throws FunctionalException, TechnicalException {
        Collaborateur update = new Collaborateur();
        update.setId(99L);
        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        update.setRole(role);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        when(collaborateurRepository.findById(99L))
                .thenReturn(Optional.empty());

                CollaborateurRequest col = new CollaborateurRequest(
                update.getId(),
                update.getNom(),
                update.getPrenom(),
                update.getEmail(),
                update.getMotDePasse(),
                update.isAdministrateur(),
                update.getRole().getId()
                );

        assertThatException().isThrownBy(() -> collaborateurService.save(col))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Collaborateur introuvable");
    }

    @Test
    void shouldCreateCollabroteur_withRoleAdmin() throws FunctionalException, TechnicalException {
        Collaborateur collaborateur = new Collaborateur();
        Role role = new Role("testRole");
        role.setId(1L);
        role.setName("ADMIN");
        collaborateur.setRole(role);
        collaborateur.setId(99L);
        collaborateur.setNom("Test");
        collaborateur.setPrenom("Test");
        collaborateur.setEmail("Test@test.fr");
        collaborateur.setMotDePasse("Admin123");

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded-password");

        when(collaborateurRepository.save(any(Collaborateur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        when(collaborateurRepository.findById(99L))
                .thenReturn(Optional.of(collaborateur));
CollaborateurRequest col = new CollaborateurRequest(
                collaborateur.getId(),
                collaborateur.getNom(),
                collaborateur.getPrenom(),
                collaborateur.getEmail(),
                collaborateur.getMotDePasse(),
                collaborateur.isAdministrateur(),
                collaborateur.getRole().getId()
                );
        Collaborateur result = collaborateurService.save(col);

        assertThat(result).isNotNull();
        assertThat(result.getRole().getName()).isEqualTo("ADMIN");
        assertThat(result.isAdministrateur()).isTrue();
    }

    @Test
    void shouldReturnCollaborateur() throws FunctionalException {
        Collaborateur collab = new Collaborateur();
        collab.setId(1L);

        Role role = new Role();
        role.setId(1L);
        role.setName("ADMIN");
        collab.setRole(role);

        when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));

        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.of(collab));

        Collaborateur result = collaborateurService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowException_whenCollaborateurNotFound() {
        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> collaborateurService.getById(1L))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Collaborateur introuvable");
    }

    @Test
    void shouldReturnList() {
        when(collaborateurRepository.findAll())
                .thenReturn(List.of(new Collaborateur(), new Collaborateur()));

        List<Collaborateur> result = collaborateurService.getAll();

        assertThat(result).hasSize(2);
    }
}