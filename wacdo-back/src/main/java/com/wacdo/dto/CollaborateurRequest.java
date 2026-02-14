package com.wacdo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollaborateurRequest {
    Long id;
    String nom;
    String prenom;
    String email;
    String motDePasse;
    boolean administrateur;
    Long roleId;
}