package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import com.wacdo.repositories.AffectationRepository;
import com.wacdo.repositories.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final AffectationRepository affectationRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AffectationRepository affectationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.affectationRepository =  affectationRepository;
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
    public void deleteById(@NonNull Long id) throws FunctionalException {
        try {

            affectationRepository.findByRestaurantId(id).forEach(
                    a -> {
                            Collaborateur c = a.getCollaborateur();
                            c.getAffectations().remove(a);

                            Fonction f = a.getFonction();
                                f.getAffectations().remove(a);

                            Restaurant r = a.getRestaurant();
                            r.getAffectations().remove(a);

                             affectationRepository.deleteById(a.getId());
                    }
            );
            restaurantRepository.deleteById(id);
        } catch (Exception e) {
            throw  new FunctionalException("Suppression du restaurant impossible, liaison avec une affectation.");
        }
    }

    @Override
    public Restaurant getById(Long id) throws FunctionalException {
        return restaurantRepository.findById(id)
                .orElseThrow(() ->
                        new FunctionalException("Restaurant introuvable avec id : " + id));
    }

    @Override
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }
}
