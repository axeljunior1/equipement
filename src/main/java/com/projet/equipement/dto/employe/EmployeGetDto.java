package com.projet.equipement.dto.employe;

import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmployeGetDto {
    private Long id;
    private String nom;
    private String prenom;
    private Boolean actif;
    private Set<Long> rolesIds = new HashSet<>();
    private Set<String> rolesNames = new HashSet<>();
    private Set<Role> roles = new HashSet<>();

//    private String role;

    private LocalDateTime dateCreation;

    public EmployeGetDto(Employe employe) {
        this.id = employe.getId();
        this.nom = employe.getNom();
        this.prenom = employe.getPrenom();
        this.dateCreation = employe.getDateCreation();
        this.actif = employe.getActif();
        if(employe.getRoles() != null) {
            this.roles = employe.getRoles();
        }
        if (employe.getRoles() != null) {
            for (Role role : employe.getRoles()) {
                rolesIds.add(role.getId());
            }
        }
        if (employe.getRoles() != null) {
            for (Role role : employe.getRoles()) {
                rolesNames.add(role.getNom());
            }
        }
    }
}
