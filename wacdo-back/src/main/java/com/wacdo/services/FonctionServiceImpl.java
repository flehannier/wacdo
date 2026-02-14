package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Fonction;
import com.wacdo.entities.Restaurant;
import com.wacdo.exception.FunctionalException;
import com.wacdo.repositories.AffectationRepository;
import com.wacdo.repositories.FonctionRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FonctionServiceImpl implements FonctionService {

    private final FonctionRepository fonctionRepository;
    private final AffectationRepository affectationRepository;

    public FonctionServiceImpl(FonctionRepository fonctionRepository, AffectationRepository affectationRepository) {
        this.fonctionRepository = fonctionRepository;
        this.affectationRepository = affectationRepository;
    }

    @Override
    @Transactional
    public Fonction save(@NonNull Fonction fct) {
        return fonctionRepository.save(fct);
    }

    @Override
    public Fonction getById(@NonNull Long id) throws FunctionalException {
        return fonctionRepository.findById(id).orElseThrow(() -> new FunctionalException("Fonction introuvable"));
    }

    @Override
    public List<Fonction> getAll() {
        return fonctionRepository.findAll();
    }

    @Override
    public void deleteById(@NonNull Long id)  throws FunctionalException {
        try {
            affectationRepository.findByFonctionId(id).forEach(
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
            fonctionRepository.deleteById(id);
        } catch ( Exception e ) {
            throw new FunctionalException("Suppression de la fonction impossible, liaison avec une affectation.");
        }
    }
}
