package com.wacdo.repositories;

import com.wacdo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

@RepositoryRestResource(path = "restaurant")
@CrossOrigin("*")
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
