package com.wacdo.services;

import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import lombok.NonNull;

import java.util.List;

public interface RestaurantService {
    Restaurant save(@NonNull Restaurant resto);
    void deleteById(@NonNull Long id);
    Restaurant getById(@NonNull Long id) throws FunctionalException;
    List<Restaurant> getAll();
}
