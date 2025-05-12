package com.projet.equipement.mapper;

import com.projet.equipement.dto.role.RolePostDto;
import com.projet.equipement.dto.role.RoleUpdateDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleMapper {

    public void updateRoleFromDto(RoleUpdateDto roleUpdateDto, Role role, Set<Authority> authorities) {
        if (roleUpdateDto.getNom() != null) role.setNom(roleUpdateDto.getNom());
        if (roleUpdateDto.getDescription() != null) role.setDescription(roleUpdateDto.getDescription());
        if(authorities != null) role.setAuthorities(authorities);
    }

    public Role postRoleDto(RolePostDto rolePostDto, @NotNull Set<Authority> authorities) {
        return Role.builder()
                .nom(rolePostDto.getNom())
                .description(rolePostDto.getDescription())
                .authorities(authorities)
                .build();
    }
}
