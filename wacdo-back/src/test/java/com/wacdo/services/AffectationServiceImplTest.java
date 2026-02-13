package com.wacdo.services;

import com.wacdo.dto.AffectationRequest;
import com.wacdo.dto.CollaborateurRequest;
import com.wacdo.entities.*;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.repositories.AffectationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AffectationServiceImplTest {

    @Mock private AffectationRepository affectationRepository;
    @Mock private CollaborateurService collaborateurService;
    @Mock private RestaurantService restaurantService;
    @Mock private FonctionService fonctionService;

    @InjectMocks private AffectationServiceImpl service;

    private Collaborateur collaborateur;
    private Restaurant restaurant;
    private Fonction fonction;

    @BeforeEach
    void setup() {
        Role role = new Role();
        role.setId(1L);
        role.setName("USER");

        collaborateur = new Collaborateur();
        collaborateur.setId(1L);
        collaborateur.setNom("Doe");
        collaborateur.setPrenom("John");
        collaborateur.setEmail("john@mail.com");
        collaborateur.setMotDePasse("Password1");
        collaborateur.setAdministrateur(false);
        collaborateur.setRole(role);
        collaborateur.setAffectations(new ArrayList<>());

        restaurant = new Restaurant();
        restaurant.setId(1L);

        fonction = new Fonction();
        fonction.setId(1L);
    }

    // ===============================
    // CREATE - PREMIERE AFFECTATION
    // ===============================
    @Test
    void create_shouldCreateFirstAffectation_whenNoCurrentAffectation() throws Exception {
        AffectationRequest request = mock(AffectationRequest.class);
        when(request.collaborateurId()).thenReturn(1L);
        when(request.restaurantId()).thenReturn(1L);
        when(request.fonctionId()).thenReturn(1L);
        when(request.dateDebut()).thenReturn(LocalDate.now());

        when(collaborateurService.getById(1L)).thenReturn(collaborateur);
        when(restaurantService.getById(1L)).thenReturn(restaurant);
        when(fonctionService.getById(1L)).thenReturn(fonction);
        when(collaborateurService.save(any())).thenReturn(collaborateur);
        when(affectationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Affectation result = service.create(request);

        assertNotNull(result);
        verify(affectationRepository).save(any());
    }

    // ===============================
    // CREATE - POSTE EN COURS À CLÔTURER
    // ===============================
    @Test
    void create_shouldCloseCurrentAndCreateNew_whenValid() throws Exception {
        Affectation current = new Affectation();
        current.setDateDebut(LocalDate.of(2024,1,1));
        current.setDateFin(null);

        collaborateur.setAffectations(new ArrayList<>(List.of(current)));

        AffectationRequest request = mock(AffectationRequest.class);
        when(request.collaborateurId()).thenReturn(1L);
        when(request.restaurantId()).thenReturn(1L);
        when(request.fonctionId()).thenReturn(1L);
        when(request.dateDebut()).thenReturn(LocalDate.of(2025,1,1));

        when(collaborateurService.getById(1L)).thenReturn(collaborateur);
        when(restaurantService.getById(1L)).thenReturn(restaurant);
        when(fonctionService.getById(1L)).thenReturn(fonction);
        when(affectationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Affectation result = service.create(request);

        assertNotNull(result);
        assertNotNull(current.getDateFin());
        verify(affectationRepository, times(2)).save(any());
    }

    // ===============================
    // CREATE - EXCEPTION DATE DEBUT
    // ===============================
    @Test
    void create_shouldThrowException_whenDateDebutBeforeCurrent() throws Exception {
        Affectation current = new Affectation();
        current.setDateDebut(LocalDate.of(2024,1,1));
        current.setDateFin(null);

        collaborateur.setAffectations(List.of(current));

        AffectationRequest request = mock(AffectationRequest.class);
        when(request.collaborateurId()).thenReturn(1L);
        when(request.restaurantId()).thenReturn(1L);
        when(request.fonctionId()).thenReturn(1L);
        when(request.dateDebut()).thenReturn(LocalDate.of(2023,1,1));

        when(collaborateurService.getById(1L)).thenReturn(collaborateur);
        when(restaurantService.getById(1L)).thenReturn(restaurant);
        when(fonctionService.getById(1L)).thenReturn(fonction);

        assertThrows(FunctionalException.class, () -> service.create(request));
        verify(affectationRepository, never()).save(any());
    }

    // ===============================
    // UPDATE
    // ===============================
    @Test
    void update_shouldUpdateAffectation_whenValid() throws Exception {
        Affectation existing = new Affectation();
        existing.setId(1L);
        existing.setDateDebut(LocalDate.of(2024,1,1));
        existing.setCollaborateur(collaborateur);
        existing.setRestaurant(restaurant);
        existing.setFonction(fonction);

        AffectationRequest request = mock(AffectationRequest.class);
        when(request.id()).thenReturn(1L);
        when(request.collaborateurId()).thenReturn(1L);
        when(request.restaurantId()).thenReturn(1L);
        when(request.fonctionId()).thenReturn(1L);
        when(request.dateDebut()).thenReturn(LocalDate.of(2024,1,1));
        when(request.dateFin()).thenReturn(LocalDate.of(2024,12,31));

        when(collaborateurService.getById(1L)).thenReturn(collaborateur);
        when(restaurantService.getById(1L)).thenReturn(restaurant);
        when(fonctionService.getById(1L)).thenReturn(fonction);
        when(affectationRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(affectationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Affectation result = service.update(request);

        assertEquals(LocalDate.of(2024,12,31), result.getDateFin());
        verify(affectationRepository).save(existing);
    }

    // ===============================
    // GET BY ID
    // ===============================
    @Test
    void getById_shouldReturnAffectation_whenExists() throws Exception {
        Affectation aff = new Affectation();
        aff.setId(1L);

        when(affectationRepository.findById(1L)).thenReturn(Optional.of(aff));

        Affectation result = service.getById(1L);
        assertEquals(1L, result.getId());
    }

    // ===============================
    // DELETE
    // ===============================
    @Test
    void deleteById_shouldDelete_whenExists() {
        Affectation aff = new Affectation();
        aff.setId(1L);

        when(affectationRepository.findById(1L)).thenReturn(Optional.of(aff));

        service.deleteById(1L);

        verify(affectationRepository).delete(aff);
    }
}
