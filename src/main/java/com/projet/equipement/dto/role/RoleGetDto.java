package com.projet.equipement.dto.role;

import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class RoleGetDto {


    private Long id ;

    private String nom;

    private String description;

    private Set<String> authorityNoms = new HashSet<String>();
    private Set<Long> authorityIds = new HashSet<Long>();
    private Set<Authority> authorities = new HashSet<Authority>();




    public RoleGetDto(Role role) {
        this.id = role.getId();
        this.nom = role.getNom();
        this.authorityNoms = role.getAuthorities().stream().map(Authority::getNom).collect(Collectors.toSet());
        this.authorityIds = role.getAuthorities().stream().map(Authority::getId).collect(Collectors.toSet());
        this.authorities = role.getAuthorities();
        this.description = role.getDescription();
    }
}
