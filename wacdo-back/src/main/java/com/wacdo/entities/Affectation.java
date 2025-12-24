package com.wacdo.entities;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "collaborateurRestaurantFonctionConstraint", columnNames = { "collaborateur", "restaurant", "fonction" })
})
public class Affectation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    @Column(nullable = false)
    private LocalDate dateDebut;

    private LocalDate dateFin;

    @ManyToOne()
    @JoinColumn(name = "collaborateur_id")
    private Collaborateur collaborateur;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne()
    @JoinColumn(name = "fonction_id")
    private Fonction fonction;
}
