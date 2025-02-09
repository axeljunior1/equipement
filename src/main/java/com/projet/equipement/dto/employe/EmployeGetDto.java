package com.projet.equipement.dto.employe;

import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
public class EmployeGetDto {
    private Long id;
    private String nom;
    private String prenom;
    private Boolean actif;
    private Set<String> roles;
//    private String role;

    private LocalDateTime dateCreation;

    public EmployeGetDto(Employe employe) {
        this.id = employe.getId();
        this.nom = employe.getNom();
        this.prenom = employe.getPrenom();
        this.dateCreation = employe.getDateCreation();
        this.actif = employe.getActif();
        for (Role role : employe.getRoles()) {
            roles.add(role.getNom());
        }
    }
}
