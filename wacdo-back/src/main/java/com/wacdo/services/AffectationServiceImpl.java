package com.wacdo.services;

import com.wacdo.entities.Affectation;
import com.wacdo.repositories.AffectationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AffectationServiceImpl implements AffectationService {

    private final AffectationRepository affectationRepository;

    public AffectationServiceImpl(AffectationRepository affectationRepository) {
        this.affectationRepository = affectationRepository;
    }

    @Override
    public Affectation save(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    @Override
    public Affectation update(Affectation affectation) {
        return affectationRepository.save(affectation);
    }

    @Override
    public void deleteByID(Long id) {
        affectationRepository.deleteById(id);
    }

    @Override
    public void delete(Affectation affectation) {
        affectationRepository.delete(affectation);
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
