package com.wacdo.services;

import com.wacdo.dto.AffectationDto;
import com.wacdo.entities.Affectation;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.repositories.AffectationRepository;
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
    public Affectation save(@NonNull AffectationDto affectation) throws FunctionalException, TechnicalException {
        log.debug("Sauvegarde d'une affectation");

        Collaborateur collaborateur = collaborateurService.getById(affectation.collaborateur().id());
        Restaurant restaurant = restaurantService.getById(affectation.restaurant().id());
        Fonction fonction = fonctionService.getById(affectation.fonction().id());

        if (collaborateur == null  || restaurant == null || fonction == null) {
            log.error("Le collaborateur ou restaurant ou la fonction n'existe pas");
            throw new FunctionalException("Le collaborateur ou restaurant ou la fonction n'existe pas");
        }

        Affectation affectationPosteEnCoursExist = collaborateur.getAffectations().stream()
                .filter(a -> a.getDateFin() == null)
                .findFirst()
                .orElse(null);

        Affectation newAffectation = new Affectation();

        // 1- Aucun poste en cours dans un restaurant
        if(affectationPosteEnCoursExist == null){

            //Mise à jours de la date qui correspond à sa toute première embauche
           if(collaborateur.getAffectations().isEmpty()) {
               log.debug("Mise à jours de la date d'embauche");
               collaborateur.setDatePremiereEmbauche(affectation.dateDebut());
               collaborateurService.save(collaborateur);
           }

            newAffectation.setDateDebut(affectation.dateDebut());
            newAffectation.setCollaborateur(collaborateur);
            newAffectation.setRestaurant(restaurant);
            newAffectation.setFonction(fonction);

            return affectationRepository.save(newAffectation);
        }

        // 2- Collaborateur affecté à un poste en cours pour un restaurant

        // contrôl sur les date de début
        // Un collaborateur peut posté seulement si la date de l'affectation est supérieur à celle en cours
        if(affectationPosteEnCoursExist.getDateDebut().isAfter(affectation.dateDebut()) || affectationPosteEnCoursExist.getDateDebut().equals(affectation.dateDebut())){
            throw new FunctionalException("Votre affectation souhaitée a une date de début égale ou antérieur a une affectation en cours");
        }

        // On clôture l'affectation pour le poste en cours
        affectationPosteEnCoursExist.setDateFin(LocalDate.now());
        affectationRepository.save(affectationPosteEnCoursExist);

        newAffectation.setDateDebut(affectation.dateDebut());
        newAffectation.setCollaborateur(collaborateur);
        newAffectation.setRestaurant(restaurant);
        newAffectation.setFonction(fonction);

        return affectationRepository.save(newAffectation);
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
