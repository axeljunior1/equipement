package com.projet.equipement.mapper;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import com.projet.equipement.entity.RoleEmploye;
import org.mapstruct.*;


import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeMapper {


    @Mapping(target = "rolesNoms", source = "employeRoles", qualifiedByName = "mapRolesToNoms")
    @Mapping(target = "rolesIds", source = "employeRoles", qualifiedByName = "mapRolesToIds")
    EmployeGetDto toDto(Employe employe);

    Employe toEmploye(EmployeGetDto employeGetDto);

    Employe toEmploye(EmployePostDto employePostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEmployeFromDto(EmployeUpdateDto employeUpdateDto, @MappingTarget Employe employe);


    @Named("mapRolesToNoms")
    default Set<String> mapRolesToNoms(Set<RoleEmploye> roles) {
        return roles.stream().map(RoleEmploye::getRole).map(Role::getNom).collect(Collectors.toSet());
    }
    @Named("mapRolesEmployeToER")
    default Set<Role> mapRolesEmployeToER(Set<RoleEmploye> roles) {
        return roles.stream().map(RoleEmploye::getRole).collect(Collectors.toSet());
    }

    @Named("mapRolesToIds")
    default Set<Long> mapRolesToIds(Set<RoleEmploye> roles) {
        return roles.stream().map(RoleEmploye::getRole).map(Role::getId).collect(Collectors.toSet());
    }



}
