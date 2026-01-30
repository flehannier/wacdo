package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import com.wacdo.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
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
    @Transactional
    public Restaurant save(@NonNull Restaurant resto) throws FunctionalException {
        if (resto.getId() != null) {
            Restaurant existing = restaurantRepository.findById(resto.getId())
                    .orElseThrow(() -> new FunctionalException("Restaurant introuvable"));

            existing.setNom(resto.getNom());
            existing.setAdresse(resto.getAdresse());
            existing.setCodePostal(resto.getCodePostal());
            existing.setVille(resto.getVille());

            //update fait à la fin de la transaction
            return existing;
        }

        return restaurantRepository.save(resto);
    }

    @Override
    public void deleteById(@NonNull Long id) {
        restaurantRepository.deleteById(id);
    }

    @Override
    public Restaurant getById(@NonNull Long id) throws FunctionalException {
        return restaurantRepository.findById(id).orElseThrow(() -> new FunctionalException("Restaurant introuvable"));
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
