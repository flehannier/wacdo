package com.wacdo.services;

import com.wacdo.controllers.WacdoApplication;
import com.wacdo.controllers.entities.Collaborateur;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import com.wacdo.controllers.repositories.CollaborateurRepository;
import com.wacdo.controllers.repositories.RoleRepository;
import com.wacdo.controllers.services.CollaborateurService;
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
        // GIVEN
        Collaborateur collab = new Collaborateur();
        collab.setNom("Doe");
        collab.setPrenom("John");
        collab.setEmail("john.doe@test.com");
        collab.setMotDePasse("Password123");

        when(passwordEncoder.encode(any()))
                .thenReturn("encoded-password");

        when(collaborateurRepository.save(any(Collaborateur.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));;

        Collaborateur result = collaborateurService.save(collab);

        assertThat(result.getMotDePasse()).isEqualTo("encoded-password");
    }

    @Test
    void shouldThrowException_whenPasswordIsNotStrong() {
        Collaborateur collab = new Collaborateur();
        collab.setEmail("test@test.com");
        collab.setMotDePasse("123");
        assertThatException()
                .isThrownBy(() -> collaborateurService.save(collab))
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

        Collaborateur result = collaborateurService.save(update);

        assertThat(result.getMotDePasse()).isEqualTo("encoded-old-password");
        assertThat(result.getNom()).isEqualTo("NewName");
        assertThat(result.getEmail()).isEqualTo("john.NewName@test.com");
    }

    @Test
    void shouldThrowException_whenUpdatingUnknownCollaborateur() {
        Collaborateur update = new Collaborateur();
        update.setId(99L);

        when(collaborateurRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> collaborateurService.save(update))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Collaborateur introuvable");
    }

    @Test
    void shouldReturnCollaborateur() {
        Collaborateur collab = new Collaborateur();
        collab.setId(1L);

        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.of(collab));

        Collaborateur result = collaborateurService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowException_whenNotFound() {
        when(collaborateurRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> collaborateurService.getById(1L))
                .isInstanceOf(FunctionalException.class);
    }

    @Test
    void shouldReturnList() {
        when(collaborateurRepository.findAll())
                .thenReturn(List.of(new Collaborateur(), new Collaborateur()));

        List<Collaborateur> result = collaborateurService.getAll();

        assertThat(result).hasSize(2);
    }
}