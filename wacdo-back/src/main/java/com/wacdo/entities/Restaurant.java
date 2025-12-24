package com.wacdo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "nomAddrCpVilleConstraint", columnNames = { "nom", "adresse", "codePostal", "ville"})
})
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull  // pour que @RequiredArgsConstructor fonctionne
    @Column(nullable = false)
    private String nom;

    @NonNull
    @Column(nullable = false)
    private String adresse;

    @NonNull
    @Column(nullable = false)
    private String codePostal;

    @NonNull
    @Column(nullable = false)
    private String ville;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore //Evite de boucler
    private List<Affectation> affectations;
}
