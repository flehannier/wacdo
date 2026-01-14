package com.wacdo.services;

import com.wacdo.controllers.WacdoApplication;
import com.wacdo.controllers.entities.Restaurant;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.repositories.RestaurantRepository;
import com.wacdo.controllers.services.RestaurantService;
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
public class RestaurantServiceImplTest {

    @Autowired
    private RestaurantService restaurantService;

    @MockBean
    private RestaurantRepository restaurantRepository;

    @Test
    void shouldCreateRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setNom("Dominos");

        when(restaurantRepository.save(any(Restaurant.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Restaurant result = restaurantService.save(restaurant);

        assertThat(result.getNom()).isEqualTo("Dominos");
    }

    @Test
    void shouldReturnRestaurant() throws FunctionalException {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.getById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void shouldReturnList() {
        when(restaurantService.getAll())
                .thenReturn(List.of(new Restaurant(), new Restaurant()));

        List<Restaurant> result = restaurantService.getAll();

        assertThat(result).hasSize(2);
    }

    @Test
    void shouldThrowException_whenRestaurantNotFound() throws FunctionalException {
        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatException().isThrownBy(() -> restaurantService.getById(1L))
                .isInstanceOf(FunctionalException.class)
                .withMessageContaining("Restaurant introuvable");
    }
}