package com.wacdo.controllers.repositories;

import com.wacdo.controllers.entities.Collaborateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    List<Collaborateur> findByNom(String nom);
    List<Collaborateur> findByNomContains(String nom);

    @Query ("select c from Collaborateur c where c.nom like %:nom and prenom like %:prenom")
    List<Collaborateur> findByNomPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    List<Collaborateur> findByOrderByNomAsc();

    @Query ("select c from Collaborateur c order by c.nom ASC, c.prenom DESC")
    List<Collaborateur> trierNomPrenom();

    @Query("SELECT c FROM Collaborateur c WHERE c.email = :email")
    Collaborateur findByEmail(@Param("email") String email);
}
