package com.wacdo.services;

import com.wacdo.dto.AffectationDto;
import com.wacdo.dto.AffectationMapper;
import com.wacdo.dto.AffectationRequest;
import com.wacdo.dto.CollaborateurRequest;
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

import java.beans.Transient;
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
     * Mise à jours d'une affectation
     * @param affectation
     * @return Affectation
     * @throws FunctionalException
     * @throws TechnicalException
     */
    @Override
    @Transactional
    public Affectation update(@NonNull AffectationRequest affectation) throws FunctionalException, TechnicalException {
/*
        Affectation affectationPosteEnCoursExist = affectationRepository.findByCollaborateurId(affectation.collaborateurId()).stream()
                .filter(a -> a.getDateFin() == null)
                .findFirst()
                .orElse(null);
 */        
        Collaborateur collaborateur = collaborateurService.getById(affectation.collaborateurId());
        Restaurant restaurant = restaurantService.getById(affectation.restaurantId());
        Fonction fonction = fonctionService.getById(affectation.fonctionId());

        Affectation  aff = affectationRepository.findById(affectation.id()).get();
        if ( !aff.getDateDebut().equals(affectation.dateDebut()) && affectation.dateDebut().isAfter(affectation.dateDebut()) ) {
            throw new FunctionalException("Votre affectation souhaitée a une date de début égale ou antérieur a une affectation en cours");
        }

        if (null != affectation.dateFin() && affectation.dateFin().isBefore(affectation.dateDebut())) {
            throw new FunctionalException("Votre affectation souhaitée a une date de fin antérieure à la date de début");
        }

        aff.setId(affectation.id());
        aff.setCollaborateur(collaborateur);
        aff.setRestaurant(restaurant);
        aff.setFonction(fonction);
        aff.setDateDebut(affectation.dateDebut());
        aff.setDateFin(affectation.dateFin());

        return affectationRepository.save(aff);
    }
    /**
     * Creation à jours d'une affectation
     * @param affectation
     * @return Affectation
     * @throws FunctionalException
     * @throws TechnicalException
     */
    @Override
    @Transactional
    public Affectation create(@NonNull AffectationRequest affectation) throws FunctionalException, TechnicalException {
        log.debug("Sauvegarde d'une affectation");

        Collaborateur collaborateur = collaborateurService.getById(affectation.collaborateurId());
        Restaurant restaurant = restaurantService.getById(affectation.restaurantId());
        Fonction fonction = fonctionService.getById(affectation.fonctionId());

        if (collaborateur == null  || restaurant == null || fonction == null) {
            log.error("Le collaborateur ou restaurant ou la fonction n'existe pas");
            throw new FunctionalException("Le collaborateur ou restaurant ou la fonction n'existe pas");
        }

        Affectation affectationPosteEnCoursExist = null;
        if(collaborateur.getAffectations() != null){
            affectationPosteEnCoursExist = collaborateur.getAffectations().stream()
                    .filter(a -> a.getDateFin() == null)
                    .findFirst()
                    .orElse(null);
        }

        Affectation newAffectation = new Affectation();

        // 1- Aucun poste en cours dans un restaurant
        if(affectationPosteEnCoursExist == null){

            //Mise à jours de la date qui correspond à sa toute première embauche
           if(collaborateur.getAffectations() == null || collaborateur.getAffectations().isEmpty()) {
               log.debug("Mise à jours de la date d'embauche");
               collaborateur.setDatePremiereEmbauche(affectation.dateDebut());

               CollaborateurRequest collab = new CollaborateurRequest(
                  collaborateur.getId(),
                  collaborateur.getNom(),
                  collaborateur.getPrenom(),
                  collaborateur.getEmail(),
                  collaborateur.getMotDePasse(),
                  collaborateur.isAdministrateur(),
                  collaborateur.getRole().getId()
               );
               collaborateurService.save(collab);
           }

            newAffectation.setDateDebut(affectation.dateDebut());
            newAffectation.setCollaborateur(collaborateur);
            newAffectation.setRestaurant(restaurant);
            newAffectation.setFonction(fonction);

            return affectationRepository.save(newAffectation);
        }

        // 2- Collaborateur affecté à un poste en cours pour un restaurant
        // contrôl sur les date de début et fin
        // Un collaborateur peut posté seulement si la date de l'affectation est supérieur à celle en cours
        if (affectationPosteEnCoursExist.getDateDebut().isAfter(affectation.dateDebut()) || 
            affectationPosteEnCoursExist.getDateDebut().equals(affectation.dateDebut())) {
            throw new FunctionalException("Votre affectation souhaitée a une date de début égale ou antérieur a une affectation en cours");
        }

        if (null != affectation.dateFin() && affectation.dateFin().isBefore(affectation.dateDebut())) {
            throw new FunctionalException("Votre affectation souhaitée a une date de fin antérieure à la date de début");
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
    public List<AffectationDto> getAll() {
        List<AffectationDto> list = affectationRepository.findAll().stream()
            .map(AffectationMapper::toDto)
            .toList();

        return list;
    }

    @Override
    @Transactional
    public void deleteById(@NonNull Long id) {
        Affectation affectation = affectationRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Affectation introuvable"));
        affectationRepository.delete(affectation);
    }
}
