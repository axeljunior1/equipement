package com.projet.equipement.dto.utilisateur;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UtilisateurPostDto {
    private String nom;
    private String email;

    private Set<Long> roleIds;

    private LocalDateTime dateCreation;
}
