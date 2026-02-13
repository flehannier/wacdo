package com.wacdo.services;

import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import com.wacdo.repositories.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private RestaurantServiceImpl service;

    // ===============================
    // SUCCESS CASE
    // ===============================

    @Test
    void getById_shouldReturnRestaurant_whenFound() throws Exception {

        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        Restaurant result = service.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(restaurantRepository).findById(1L);
    }

    // ===============================
    // EXCEPTION CASE
    // ===============================

    @Test
    void getById_shouldThrowFunctionalException_whenNotFound() {

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(FunctionalException.class,
                () -> service.getById(1L));

        verify(restaurantRepository).findById(1L);
    }
}
