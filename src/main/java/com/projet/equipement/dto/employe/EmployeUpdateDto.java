package com.projet.equipement.dto.employe;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmployeUpdateDto {
    private String nom;
    private String prenom;
    private String password;
    private LocalDateTime dateCreation;
    private Boolean actif;
    private Set<Long> rolesIds = new HashSet<>();
}
