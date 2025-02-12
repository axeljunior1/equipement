package com.projet.equipement.mapper;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import org.mapstruct.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeMapper {


    @Mapping(target = "rolesNoms", source = "roles", qualifiedByName = "mapRolesToNoms")
    @Mapping(target = "rolesIds", source = "roles", qualifiedByName = "mapRolesToIds")
    EmployeGetDto toDto(Employe employe);

    @Mapping(target = "roles", source = "rolesIds", qualifiedByName = "mapIdToRoles")
    Employe toEmploye(EmployeGetDto employeGetDto);

    @Mapping(target = "roles", source = "rolesIds", qualifiedByName = "mapIdToRoles")
    Employe toEmploye(EmployePostDto employePostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", source = "rolesIds", qualifiedByName = "mapIdToRoles")
    void updateEmployeFromDto(EmployeUpdateDto employeUpdateDto, @MappingTarget Employe employe);


    @Named("mapRolesToNoms")
    default Set<String> mapRolesToNoms(Set<Role> roles) {
        return roles.stream().map(Role::getNom).collect(Collectors.toSet());
    }

    @Named("mapRolesToIds")
    default Set<Long> mapRolesToIds(Set<Role> roles) {
        return roles.stream().map(Role::getId).collect(Collectors.toSet());
    }


    @Named("mapIdToRoles")
    default Set<Role> mapIdToRoles(Set<Long> ids) {
        Set<Role> roles = new HashSet<>();
        ids.forEach(roleId -> {
            Role roleEntity = new Role();
            roleEntity.setId(roleId);
            roles.add(roleEntity);
        });
        return roles;
    }

}
