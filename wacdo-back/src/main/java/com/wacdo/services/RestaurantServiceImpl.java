package com.wacdo.services;

import com.wacdo.entities.Restaurant;
import com.wacdo.repositories.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant resto) {
        return restaurantRepository.save(resto);
    }

    @Override
    public Restaurant update(Restaurant resto) {
        return restaurantRepository.save(resto);
    }

    @Override
    public void deleteByID(Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public void delete(Restaurant resto) {
        restaurantRepository.delete(resto);
    }

    @Override
    public Restaurant getById(Long id) {
        return restaurantRepository.findById(id).get();
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
