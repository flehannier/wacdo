package com.wacdo.services;

import com.wacdo.WacdoApplication;
import com.wacdo.dto.AffectationMapper;
import com.wacdo.entities.*;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.repositories.AffectationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WacdoApplication.class)
public class AffectationServiceImplTest{

    @Autowired
    private AffectationService affectationService;

    @MockBean
    private CollaborateurService collaborateurService;

    @MockBean
    private FonctionService fonctionService;

    @MockBean
    private RestaurantService restaurantService;

    @MockBean
    private AffectationRepository affectationRepository;
    
    @Test
    void shouldReturnAffectation() throws FunctionalException {
        Affectation affectation = new Affectation();
        affectation.setId(1L);
    
        when(affectationRepository.findById(1L))
                .thenReturn(Optional.of(affectation));
    
        Affectation result = affectationService.getById(1L);
    
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldThrowException_whenAffectationNotFound() throws FunctionalException {
        when(affectationRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> affectationService.getById(1L))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Affectation introuvable");
    }

    @Test
    void shouldReturnList() {
        when(affectationService.getAll())
                .thenReturn(List.of(new Affectation(), new Affectation()));
    
        List<Affectation> result = affectationService.getAll();
    
        assertThat(result).hasSize(2);
    }

    @Test
    void shouldCreateAnAffectation() throws TechnicalException, FunctionalException {
        Affectation affectationCollab = new Affectation();
        affectationCollab.setDateDebut(LocalDate.now().plusDays(1L));
        Collaborateur collaborateur = new Collaborateur();
        collaborateur.setId(1L);
        collaborateur.setRole(new Role(1L, "ADMIN", "ADMIN", new ArrayList<>()));
        collaborateur.setAffectations(
                List.of(affectationCollab)
        );

        when(collaborateurService.getById(1L))
                .thenReturn(collaborateur);

        Fonction fonction = new Fonction();
        fonction.setId(1L);
        when(fonctionService.getById(1L))
                .thenReturn(fonction);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        when(restaurantService.getById(1L))
                .thenReturn(restaurant);

        Affectation affectation = new Affectation();
        affectation.setDateDebut(LocalDate.now());
        affectation.setCollaborateur(collaborateur);
        affectation.setFonction(fonction);
        affectation.setRestaurant(restaurant);

        assertThatException().isThrownBy(() -> affectationService.save(AffectationMapper.toDto(affectation)))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Votre affectation souhaitée a une date de début égale ou antérieur");
    }

    @Test
    void shouldThrowAnException_whenDateError() throws TechnicalException, FunctionalException {
        Affectation affectationCollab = new Affectation();
        affectationCollab.setDateDebut(LocalDate.now());

        Collaborateur collaborateur = new Collaborateur();
        collaborateur.setId(1L);
        collaborateur.setRole(new Role(1L, "ADMIN", "ADMIN", new ArrayList<>()));
        collaborateur.setAffectations(
                List.of(affectationCollab)
        );

        when(collaborateurService.getById(1L))
                .thenReturn(collaborateur);

        Fonction fonction = new Fonction();
        fonction.setId(1L);
        when(fonctionService.getById(1L))
                .thenReturn(fonction);

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        when(restaurantService.getById(1L))
                .thenReturn(restaurant);

        Affectation affectation = new Affectation();
        affectation.setDateDebut(LocalDate.now());
        affectation.setCollaborateur(collaborateur);
        affectation.setFonction(fonction);
        affectation.setRestaurant(restaurant);

        assertThatException().isThrownBy(() -> affectationService.save(AffectationMapper.toDto(affectation)))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Votre affectation souhaitée a une date de début égale ou antérieur a une affectation en cours");
    }
}