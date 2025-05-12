package com.projet.equipement.dto.role;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleUpdateDto {

    private String nom;

    private String description;

    private Set<String> authoritiesNoms = new HashSet<String>();


}
