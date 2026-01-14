package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Affectation;
import com.wacdo.controllers.entities.Collaborateur;
import com.wacdo.controllers.entities.Fonction;
import com.wacdo.controllers.entities.Restaurant;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.exception.TechnicalException;
import com.wacdo.controllers.repositories.AffectationRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
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

    /**
     * Creation ou mise à jours d'une affectation
     * @param affectation
     * @return Affectation
     * @throws FunctionalException
     * @throws TechnicalException
     */
    @Override
    @Transactional
    public Affectation save(@NonNull Affectation affectation) throws FunctionalException, TechnicalException {
        log.debug("Sauvegarde d'une affectation");

        Collaborateur collaborateur = collaborateurService.getById(affectation.getCollaborateur().getId());
        Restaurant restaurant = restaurantService.getById(affectation.getRestaurant().getId());
        Fonction fonction = fonctionService.getById(affectation.getFonction().getId());

        if (restaurant == null || fonction == null) {
            log.error("Le restaurant ou la fonction n'existe pas");
            throw new FunctionalException("Le restaurant ou la fonction n'existe pas");
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
            throw new FunctionalException("Votre affectation souhaitée a une date de début égale ou antérieur a une affectation en cours");
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
    public Affectation getById(@NonNull Long id) throws FunctionalException {
        return affectationRepository.findById(id).orElseThrow(() -> new FunctionalException("Affectation introuvable"));
    }

    @Override
    public List<Affectation> getAll() {
        return affectationRepository.findAll();
    }

    @Override
    public void deleteById(@NonNull Long id) {
        affectationRepository.deleteById(id);
    }
}
