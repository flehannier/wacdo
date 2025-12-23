package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Restaurant;
import com.wacdo.repositories.CollaborateurRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class CollaborateurServiceImpl implements CollaborateurService {

    @Autowired
    CollaborateurRepository collaborateurRepository;

    @Override
    public Collaborateur saveCollaborateur(Collaborateur collab) {
        return collaborateurRepository.save(collab);
    }

    @Override
    public Collaborateur updateCollaboreteur(Collaborateur collab) {
        return collaborateurRepository.save(collab);
    }

    @Override
    public void deleteCollaborateur(Long id) {
        collaborateurRepository.deleteById(id);
    }

    @Override
    public void deleteCollaborateur(Collaborateur collaborateur) {
        collaborateurRepository.delete(collaborateur);

    }

    @Override
    public Collaborateur getCollaborateur(Long id) {
       return collaborateurRepository.findById(id).get();
    }

    @Override
    public List<Collaborateur> getAllCollaborateur() {
        return collaborateurRepository.findAll();
    }

    @Override
    public List<Collaborateur> findByNom(String nom) {
        return collaborateurRepository.findByNom(nom);
    }

    @Override
    public List<Collaborateur> findByNomContains(String nom) {
        return collaborateurRepository.findByNomContains(nom);
    }

    @Override
    public List<Collaborateur> findByNomPrenom(String nom, String prenom) {
        return collaborateurRepository.findByNomPrenom(nom,prenom);
    }

    @Override
    public List<Collaborateur> findByRestaurant(Restaurant restaurant) {
        return collaborateurRepository.findByRestaurant(restaurant);
    }

    @Override
    public List<Collaborateur> findByRestaurantId(Long id) {
        return collaborateurRepository.findByRestaurantId(id);
    }

    @Override
    public List<Collaborateur> findByOrderByNomAsc() {
        return collaborateurRepository.findByOrderByNomAsc();
    }

    @Override
    public List<Collaborateur> trierNomPrenom() {
        return collaborateurRepository.trierNomPrenom();
    }
}
