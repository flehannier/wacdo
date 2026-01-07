package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Restaurant;
import lombok.NonNull;

import java.util.List;

public interface RestaurantService {
    Restaurant save(@NonNull Restaurant resto);
    Restaurant update(@NonNull Restaurant resto);
    void deleteByID(@NonNull Long id);
    void delete(@NonNull Restaurant resto);
    Restaurant getById(@NonNull Long id);
    List<Restaurant> getAll();
}
