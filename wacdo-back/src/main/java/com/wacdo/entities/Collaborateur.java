package com.wacdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
public class Collaborateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    @NonNull  // pour que @RequiredArgsConstructor fonctionne
    @Column(nullable = false)
    private String nom;

    @NonNull  // pour que @RequiredArgsConstructor fonctionne
    @Column(nullable = false)
    private String prenom;

    @NonNull  // pour que @RequiredArgsConstructor fonctionne
    @Column(nullable = false)
    private String email;

    private Date datePremiereEmbauche;
    private boolean administrateur;

    @OneToMany(mappedBy = "collaborateur")
    @JsonIgnore //Evite de boucler
    private List<Affectation> affectations;

    // constructeur personnalisé pour tests
    public Collaborateur(String nom, String prenom, String email, Date dateEmbauche, boolean administrateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.datePremiereEmbauche = dateEmbauche;
        this.administrateur = administrateur;
        this.affectations = new ArrayList<>();
    }
}
