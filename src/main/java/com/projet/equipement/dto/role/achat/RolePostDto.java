package com.projet.equipement.dto.role.achat;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RolePostDto {

    private String nom;

    private String description;

    private Set<Long> autoritiesId;

}
