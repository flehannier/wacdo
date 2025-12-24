package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.repositories.CollaborateurRepository;
import com.wacdo.repositories.RestaurantRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollaborateurServiceImpl implements CollaborateurService {

    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurServiceImpl(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    @Override
    public Collaborateur save(Collaborateur collab) {
        return collaborateurRepository.save(collab);
    }

    @Override
    public Collaborateur update(Collaborateur collab) {
        return collaborateurRepository.save(collab);
    }

    @Override
    public void deleteById(Long id) {
        collaborateurRepository.deleteById(id);
    }

    @Override
    public void delete(Collaborateur collab) {
        collaborateurRepository.delete(collab);
    }

    @Override
    public Collaborateur getById(Long id) {
       return collaborateurRepository.findById(id).get();
    }

    @Override
    public List<Collaborateur> getAll() {
        return collaborateurRepository.findAll();
    }
}
