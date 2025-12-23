package com.wacdo.controllers;

import com.wacdo.entities.Restaurant;
import com.wacdo.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@CrossOrigin
public class RestaurantController {

    @Autowired
    RestaurantRepository restaurantRepository;

    @GetMapping()
    List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    Restaurant getAllRestaurantById(@PathVariable("id" ) Long id ) {
        return restaurantRepository.findById(id).get();
    }
}
