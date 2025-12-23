package com.wacdo.repositories;

import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(path = "collaborateur")
public interface CollaborateurRepository extends JpaRepository<Collaborateur, Long> {
    List<Collaborateur> findByNom(String nom);
    List<Collaborateur> findByNomContains(String nom);

    @Query ("select c from Collaborateur c where c.nom like %:nom and prenom like %:prenom")
    List<Collaborateur> findByNomPrenom(@Param("nom") String nom, @Param("prenom") String prenom);

    @Query ("select c from Collaborateur c where c.restaurant = ?1")
    List<Collaborateur> findByRestaurant(Restaurant restaurant);

    List<Collaborateur> findByRestaurantId(Long id);

    List<Collaborateur> findByOrderByNomAsc();

    @Query ("select c from Collaborateur c order by c.nom ASC, c.prenom DESC")
    List<Collaborateur> trierNomPrenom();
}
