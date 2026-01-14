package com.wacdo.services;

import com.wacdo.controllers.WacdoApplication;
import com.wacdo.controllers.entities.Fonction;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import com.wacdo.controllers.repositories.FonctionRepository;
import com.wacdo.controllers.services.FonctionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = WacdoApplication.class)
public class FonctionServiceImplTest {

    @Autowired
    private FonctionService fonctionService;

    @MockBean
    private FonctionRepository fonctionRepository;

    @Test
    void shouldCreateFonction() throws FunctionalException, TechnicalException {
        Fonction fonction = new Fonction();
        fonction.setIntitule("Intitule");
        fonction.setAffectations(new ArrayList<>());

        when(fonctionRepository.save(any(Fonction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Fonction result = fonctionService.save(fonction);

        assertThat(result.getIntitule()).isEqualTo("Intitule");
    }

    @Test
    void shouldReturnFonction() throws FunctionalException {
        Fonction fonction = new Fonction();
        fonction.setId(1L);

        when(fonctionRepository.findById(1L))
                .thenReturn(Optional.of(fonction));

        Fonction result = fonctionService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnList() {
        when(fonctionRepository.findAll())
                .thenReturn(List.of(new Fonction(), new Fonction()));

        List<Fonction> result = fonctionService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldThrowException_whenFonctionNotFound() throws FunctionalException {
        when(fonctionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> fonctionService.getById(1L))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Fonction introuvable");
    }
}