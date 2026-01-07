package com.wacdo.controllers.controllers;

import com.wacdo.controllers.entities.Restaurant;
import com.wacdo.controllers.services.RestaurantService;
import jakarta.annotation.Nonnull;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    List<Restaurant> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    Restaurant getById(@Nonnull @PathVariable("id" ) Long id ) {
        return restaurantService.getById(id);
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant create(@Nonnull @RequestBody Restaurant resto){
        return restaurantService.save(resto);
    }

    @PatchMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Restaurant update(@Nonnull @RequestBody Restaurant resto){
        return restaurantService.update(resto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteById(@Nonnull @PathVariable("id") Long id){
        restaurantService.deleteByID(id);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@Nonnull  @RequestBody Restaurant resto){
        restaurantService.delete(resto);
    }
}
