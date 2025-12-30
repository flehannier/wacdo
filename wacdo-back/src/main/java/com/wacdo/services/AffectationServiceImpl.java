package com.wacdo.services;

import com.wacdo.entities.Affectation;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.repositories.AffectationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
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
    public Affectation save(Affectation affectation) throws EntityNotFoundException {
        log.info("Sauvegarde d'une affectation");

        Collaborateur collaborateur = collaborateurService.getById(affectation.getCollaborateur().getId());
        Restaurant restaurant = restaurantService.getById(affectation.getRestaurant().getId());
        Fonction fonction = fonctionService.getById(affectation.getFonction().getId());

        if (restaurant == null || fonction == null) {
            log.error("Le restaurant ou la fonction n'existe pas");
            throw new EntityNotFoundException("Le restaurant ou la fonction n'existe pas");
        }

        Affectation affectationEnCoursExist = collaborateur.getAffectations().stream()
                .filter(a -> a.getDateFin() == null)
                .findFirst()
                .orElse(null);
        // vérifier si restaurant diff
        if(affectationEnCoursExist == null /*&& !affectationEnCoursExist.getRestaurant().getId().equals(restaurant.getId())*/){
            //Mise à jours date première embauche
           if(collaborateur.getAffectations().isEmpty()) {
               collaborateur.setDatePremiereEmbauche(affectation.getDateDebut());
               collaborateurService.save(collaborateur);
           }

            affectation.setCollaborateur(collaborateur);
            affectation.setRestaurant(restaurant);
            affectation.setFonction(fonction);
            return affectationRepository.save(affectation);
        }

        // On clôture l'affectation courante
        affectationEnCoursExist.setDateFin(LocalDate.now());
        affectationRepository.save(affectationEnCoursExist);

        // Mise à jours date d'embauche
        collaborateur.setDatePremiereEmbauche(affectation.getDateDebut());
        collaborateurService.save(collaborateur);

        affectation.setCollaborateur(collaborateur);
        affectation.setRestaurant(restaurant);
        affectation.setFonction(fonction);
        return affectationRepository.save(affectation);
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
