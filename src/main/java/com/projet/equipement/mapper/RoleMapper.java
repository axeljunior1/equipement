package com.projet.equipement.mapper;

import com.projet.equipement.dto.role.achat.RolePostDto;
import com.projet.equipement.entity.Authority;
import com.projet.equipement.entity.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleMapper {

//    public void updateRoleFromDto(RoleUpdateDto roleUpdateDto, Role role, Employe employe) {
//        if (roleUpdateDto.getMontantTotal() != null) role.setMontantTotal(roleUpdateDto.getMontantTotal());
//        if (roleUpdateDto.getActif() != null) role.setActif(roleUpdateDto.getActif());
//        if (employe != null) {
//            role.setEmploye(employe);
//        }
//    }

    public Role postRoleDto(RolePostDto rolePostDto, @NotNull Set<Authority> authorities) {
        return Role.builder()
                .nom(rolePostDto.getNom())
                .description(rolePostDto.getDescription())
                .authorities(authorities)
                .build();
    }
}
