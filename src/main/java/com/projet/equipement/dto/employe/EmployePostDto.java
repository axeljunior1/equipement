package com.projet.equipement.dto.employe;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class EmployePostDto {
    private String nom;
    private String pronom;

    private LocalDateTime dateCreation;
}
