package com.projet.equipement.dto.employe;

import com.projet.equipement.entity.RoleEmploye;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmployePostDto {
    @NotBlank(message = "Entre le Nom")
    private String nom;
    private String prenom;
    @NotBlank(message = "Entre le mot de passe")
    private String password;

    private Set<Long> rolesIds = new HashSet<>();
    private LocalDateTime dateCreation;
}
