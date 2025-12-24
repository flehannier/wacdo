package com.wacdo.services;

import com.wacdo.entities.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant save(Restaurant resto);
    Restaurant update(Restaurant resto);
    void deleteByID(Long id);
    void delete(Restaurant resto);
    Restaurant getById(Long id);
    List<Restaurant> getAll();
}
