package com.projet.equipement.dto.employe;

import com.projet.equipement.entity.Role;
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
    private Boolean actif;
    private Set<Long> rolesIds = new HashSet<>();
    private Set<String> rolesNoms = new HashSet<>();
}
