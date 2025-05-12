package com.projet.equipement.dto.role;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RolePostDto {

    private String nom;

    private String description;

    private Set<Long> autoritiesId;

    private Set<String> authoritiesNoms = new HashSet<String>();


}
