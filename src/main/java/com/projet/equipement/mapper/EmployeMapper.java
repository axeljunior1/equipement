package com.projet.equipement.mapper;

import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Role;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
public class EmployeMapper {



    public void updateEmployeFromDto(EmployeUpdateDto employeUpdateDto, Employe employe, Set<Role> roles){
        if (employeUpdateDto.getNom() != null) employe.setNom(employeUpdateDto.getNom());
        if (employeUpdateDto.getPrenom() != null) employe.setPrenom(employeUpdateDto.getPrenom());
        if (roles != null) employe.setRoles(roles);
    }

    public Employe postEmployeDto(EmployePostDto employePostDto, Set<Role> roles) {
        Employe employe = new Employe();
        employe.setNom(employePostDto.getNom());
        employe.setPrenom(employePostDto.getPrenom());
        employe.setPassword(employePostDto.getPassword());
        employe.setDateCreation(LocalDateTime.now());
        employe.setActif(true);
        employe.setRoles(roles != null ? roles : new HashSet<Role>());
        return employe;
    }
}
