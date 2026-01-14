package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Restaurant;
import com.wacdo.controllers.exception.FunctionalException;
import lombok.NonNull;

import java.util.List;

public interface RestaurantService {
    Restaurant save(@NonNull Restaurant resto);
    void deleteById(@NonNull Long id);
    Restaurant getById(@NonNull Long id) throws FunctionalException;
    List<Restaurant> getAll();
}
