package com.projet.equipement.dto.utilisateur;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class UtilisateurUpdateDto {
    private String nom;
    private String email;

    private Set<Long> roleIds;

    private LocalDateTime dateCreation;
}
