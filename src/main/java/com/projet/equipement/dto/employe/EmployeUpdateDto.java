package com.projet.equipement.dto.employe;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class EmployeUpdateDto {
    private String nom;
    private String prenom;
    private LocalDateTime dateCreation;
}
