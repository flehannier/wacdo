package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Restaurant;
import com.wacdo.controllers.repositories.RestaurantRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(@NonNull Restaurant resto) {
        return restaurantRepository.save(resto);
    }

    @Override
    public Restaurant update(@NonNull Restaurant resto) {
        return restaurantRepository.save(resto);
    }

    @Override
    public void deleteByID(@NonNull Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public void delete(@NonNull Restaurant resto) {
        restaurantRepository.delete(resto);
    }

    @Override
    public Restaurant getById(@NonNull Long id) {
        return restaurantRepository.findById(id).get();
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
