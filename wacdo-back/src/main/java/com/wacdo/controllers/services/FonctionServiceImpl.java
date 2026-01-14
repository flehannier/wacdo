package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Fonction;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.repositories.FonctionRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FonctionServiceImpl implements FonctionService {

    private final FonctionRepository fonctionRepository;

    public FonctionServiceImpl(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
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
    public void deleteById(@NonNull Long id) {
        fonctionRepository.deleteById(id);
    }
}
