package com.wacdo.controllers;

import com.wacdo.entities.Restaurant;
import com.wacdo.services.RestaurantService;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
@CrossOrigin
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping()
    List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    Restaurant getById(@Nonnull @PathVariable("id" ) Long id ) {
        return restaurantService.getById(id);
    }

    @PostMapping()
    public Restaurant create(@Nonnull @RequestBody Restaurant resto){
        return restaurantService.save(resto);
    }

    @PatchMapping
    public Restaurant update(@Nonnull @RequestBody Restaurant resto){
        return restaurantService.update(resto);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        restaurantService.deleteByID(id);
    }

    @DeleteMapping()
    public void delete(@Nonnull  @RequestBody Restaurant resto){
        restaurantService.delete(resto);
    }
}
