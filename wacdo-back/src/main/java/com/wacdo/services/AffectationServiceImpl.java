package com.wacdo.services;

import com.wacdo.entities.Affectation;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.repositories.AffectationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AffectationServiceImpl implements AffectationService {

    private final AffectationRepository affectationRepository;
    private final CollaborateurService collaborateurService;
    private final RestaurantService  restaurantService;
    private final FonctionService fonctionService;

    public AffectationServiceImpl(AffectationRepository affectationRepository, CollaborateurService collaborateurService, RestaurantService restaurantService, FonctionService fonctionService) {
        this.affectationRepository = affectationRepository;
        this.collaborateurService = collaborateurService;
        this.restaurantService = restaurantService;
        this.fonctionService = fonctionService;
    }

    @Override
    public Affectation save(Affectation affectation) {
        Collaborateur collaborateur = collaborateurService.getById(affectation.getCollaborateur().getId());
        Restaurant restaurant = restaurantService.getById(affectation.getRestaurant().getId());
        Fonction fonction = fonctionService.getById(affectation.getFonction().getId());

        Affectation affectationEnCoursExist = collaborateur.getAffectations().stream()
                                                            .filter(a -> a.getDateFin() == null)
                                                            .findFirst()
                                                            .orElse(null);
        // Voir pour thower le execption fonctionnelle

        if(affectationEnCoursExist == null && restaurant != null && fonction != null){
            affectation.setCollaborateur(collaborateur);
            affectation.setRestaurant(restaurant);
            affectation.setFonction(fonction);
            return affectationRepository.save(affectation);
        }
        return null;
    }

    @Override
    public Affectation update(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    @Override
    public Affectation getById(Long id) {
        return affectationRepository.findById(id).get();
    }

    @Override
    public List<Affectation> getAll() {
        return affectationRepository.findAll();
    }
}
