package com.wacdo.controllers.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
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
    @JsonProperty(value = "motDePasse", access = JsonProperty.Access.WRITE_ONLY)
    private String motDePasse;

    @NonNull
    @Column(nullable = false)
    private String email;

    private LocalDate datePremiereEmbauche;

    @Column(nullable = false)
    private boolean administrateur;

    @OneToMany(mappedBy = "collaborateur")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Affectation> affectations;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private Role role;
}
