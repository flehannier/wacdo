package com.wacdo.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
//@NoArgsConstructor
@Entity
@ToString(exclude = "restaurant")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Collaborateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private Date datePremiereEmbauche;
    private boolean administrateur;

    @ManyToOne
    private Restaurant restaurant;

    public Collaborateur() {
    }

    public Collaborateur(String nom, String prenom, String email, Date datePremiereEmbauche, boolean administrateur) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.datePremiereEmbauche = datePremiereEmbauche;
        this.administrateur = administrateur;
    }
}
