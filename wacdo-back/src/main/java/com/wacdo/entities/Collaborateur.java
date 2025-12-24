package com.wacdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "nomPrenomEmailConstraint", columnNames = { "nom", "prenom", "email" }),
        @UniqueConstraint(name = "emailConstraint", columnNames = { "email" })})
public class Collaborateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NonNull  // pour que @RequiredArgsConstructor fonctionne
    @Column(nullable = false)
    private String nom;

    @NonNull
    @Column(nullable = false)
    private String prenom;

    @NonNull
    @Column(nullable = false)
    private String motDePasse;

    @NonNull
    @Column(nullable = false)
    private String email;

    private Date datePremiereEmbauche;

    @NonNull
    private boolean administrateur;

    @OneToMany(mappedBy = "collaborateur")
    @JsonIgnore //Evite de boucler
    private List<Affectation> affectations;

}
