package com.projet.equipement.dto.employe;

import com.projet.equipement.entity.Employe;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EmployeGetDto {
    private Long id;
    private String nom;
    private String prenom;
//    private String role;

    private LocalDateTime dateCreation;

    public EmployeGetDto(Employe employe) {
        this.id = employe.getId();
        this.nom = employe.getNom();
        this.prenom = employe.getPrenom();
        this.dateCreation = employe.getDateCreation();
    }
}
