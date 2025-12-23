package com.wacdo.services;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Restaurant;

import java.util.List;

public interface CollaborateurService {
    Collaborateur saveCollaborateur(Collaborateur collab);
    Collaborateur updateCollaboreteur(Collaborateur collab);
    void deleteCollaborateur(Long id);
    void deleteCollaborateur(Collaborateur collaborateur);
    Collaborateur getCollaborateur(Long id);
    List<Collaborateur> getAllCollaborateur();
    List<Collaborateur> findByNom(String nom);
    List<Collaborateur> findByNomContains(String nom);
    List<Collaborateur> findByNomPrenom(String nom,String prenom);
    List<Collaborateur> findByRestaurant(Restaurant restaurant);
    List<Collaborateur> findByRestaurantId(Long id);
    List<Collaborateur> findByOrderByNomAsc();
    List<Collaborateur> trierNomPrenom();
}
