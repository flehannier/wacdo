package com.wacdo.services;

import com.wacdo.entities.Affectation;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
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
    public Affectation save(Affectation affectation) throws EntityNotFoundException,FunctionalException{
        log.debug("Sauvegarde d'une affectation");

        Collaborateur collaborateur = collaborateurService.getById(affectation.getCollaborateur().getId());
        Restaurant restaurant = restaurantService.getById(affectation.getRestaurant().getId());
        Fonction fonction = fonctionService.getById(affectation.getFonction().getId());

        if (restaurant == null || fonction == null) {
            log.error("Le restaurant ou la fonction n'existe pas");
            throw new EntityNotFoundException("Le restaurant ou la fonction n'existe pas");
        }

        Affectation affectationPosteEnCoursExist = collaborateur.getAffectations().stream()
                .filter(a -> a.getDateFin() == null)
                .findFirst()
                .orElse(null);

        // 1- Aucun poste en cours dans un restaurant
        if(affectationPosteEnCoursExist == null){

            //Mise à jours de la date qui correspond à sa toute première embauche
           if(collaborateur.getAffectations().isEmpty()) {
               log.debug("Mise à jours de la date d'embauche");
               collaborateur.setDatePremiereEmbauche(affectation.getDateDebut());
               collaborateurService.save(collaborateur);
           }

            affectation.setCollaborateur(collaborateur);
            affectation.setRestaurant(restaurant);
            affectation.setFonction(fonction);

            return affectationRepository.save(affectation);
        }

        // 2- Collaborateur affecté à un poste en cours pour un restaurant

        // contrôl sur les date de début
        // Un collaborateur peut posté seulement si la date de l'affectation est supérieur à celle en cours
        if(affectationPosteEnCoursExist.getDateDebut().isAfter(affectation.getDateDebut()) || affectationPosteEnCoursExist.getDateDebut().equals(affectation.getDateDebut())){
            throw new FunctionalException("Votre affectation souhaitée a une date de début antérieur a une affectation en cours");
        }

        // On clôture l'affectation pour le poste en cours
        affectationPosteEnCoursExist.setDateFin(LocalDate.now());
        affectationRepository.save(affectationPosteEnCoursExist);

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
