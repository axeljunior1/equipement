package com.projet.equipement.dto.role.achat;

import com.projet.equipement.entity.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleGetDto {


    private Long id ;

    private String nom;

    private String description;




    public RoleGetDto(Role role) {
        this.id = role.getId();
        this.nom = role.getNom();
        this.description = role.getDescription();
    }
}
