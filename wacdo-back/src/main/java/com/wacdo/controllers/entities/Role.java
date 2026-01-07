package com.wacdo.controllers.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private String name;

    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    @ToString.Exclude       // Exclut du toString() pour éviter la boucle infinie
    @EqualsAndHashCode.Exclude // Exclut de equals()/hashCode()
    private List<Collaborateur> collaborateurs;
}
