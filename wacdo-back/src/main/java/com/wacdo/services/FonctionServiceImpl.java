package com.wacdo.services;

import com.wacdo.entities.Fonction;
import com.wacdo.repositories.FonctionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FonctionServiceImpl implements FonctionService {

    private final FonctionRepository fonctionRepository;

    public FonctionServiceImpl(FonctionRepository fonctionRepository) {
        this.fonctionRepository = fonctionRepository;
    }

    @Override
    public Fonction save(Fonction fct) {
        return fonctionRepository.save(fct);
    }

    @Override
    public Fonction getById(Long id) {
        return fonctionRepository.findById(id).get();
    }

    @Override
    public List<Fonction> getAll() {
        return fonctionRepository.findAll();
    }
}
