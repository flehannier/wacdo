package com.wacdo;

import com.wacdo.entities.Collaborateur;
import com.wacdo.repositories.CollaborateurRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class CollaborateurnTests {

    @Autowired
    private CollaborateurRepository collaborateurRepository;

    @Test
    void testCreationCollaboration() {
        Collaborateur collaborateur = new Collaborateur("John", "Doe", "j.doe@gmail.com", new Date(), false);
        collaborateurRepository.save(collaborateur);
    }

    @Test
    void testFIndCollaboration() {
        Optional<Collaborateur> collaborateur = collaborateurRepository.findById(2L);
        Assertions.assertTrue(collaborateur.isPresent());
        System.out.println(collaborateur.get());
    }

    @Test
    void testUpdateCollaboration() {
      /*  Collaborateur collaborateur = collaborateurRepository.findById(2L).get();
        collaborateur.setAdministrateur(true);
        collaborateurRepository.save(collaborateur);

        System.out.println(collaborateur);*/
    }

    @Test
    void testDeleteCollaboration() {
    //    collaborateurRepository.deleteById(1L);
    }

    @Test
    void testFindAllCollaboration() {
        List<Collaborateur> collaborateurs = collaborateurRepository.findAll();

        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

    @Test
    void testFIndCollaborationByNom() {
        List<Collaborateur> collaborateurs = collaborateurRepository.findByNom("Doe");
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

    @Test
    void testFIndCollaborationContains() {
        List<Collaborateur> collaborateurs = collaborateurRepository.findByNomContains("oh");
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

    @Test
    void testFIndCollaborationByNomPrenom() {
        List<Collaborateur> collaborateurs = collaborateurRepository.findByNomPrenom("John", "oe");
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

    @Test
    @Transactional
    void testFindByRestaurant(){
       /* Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        List<Collaborateur> collaborateurs = collaborateurRepository.findByRestaurant(restaurant);
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }*/
    }

    @Test
    @Transactional
    void testFindByRestaurantId(){
        List<Collaborateur> collaborateurs = collaborateurRepository.findByRestaurantId(1L);
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

    @Test
    @Transactional
    void testFindByCollaborateurOrderByAsc(){
        List<Collaborateur> collaborateurs = collaborateurRepository.findByOrderByNomAsc();
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

    @Test
    @Transactional
    void testTrierNomPrenom(){
        List<Collaborateur> collaborateurs = collaborateurRepository.trierNomPrenom();
        for (Collaborateur collaborateur : collaborateurs) {
            System.out.println(collaborateur);
        }
    }

}
