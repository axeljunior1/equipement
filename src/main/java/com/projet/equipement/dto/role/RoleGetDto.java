package com.projet.equipement.dto.role;

import com.projet.equipement.entity.Authority;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class RoleGetDto {


    private String nom;

    private String description;

    private Set<String> authorityNoms = new HashSet<String>();
    private Set<Long> authorityIds = new HashSet<Long>();
    private Set<Authority> authorities = new HashSet<Authority>();

}
