package com.projet.equipement.mapper;

import com.projet.equipement.dto.employe.EmployePostDto;
import com.projet.equipement.dto.employe.EmployeUpdateDto;
import com.projet.equipement.entity.Employe;
import org.springframework.stereotype.Component;

@Component
public class EmployeMapper {



    public void updateEmployeFromDto(EmployeUpdateDto employeUpdateDto, Employe employe){
        if (employeUpdateDto.getNom() != null) employe.setNom(employeUpdateDto.getNom());
        if (employeUpdateDto.getPrenom() != null) employe.setPrenom(employeUpdateDto.getPrenom());
    }

    public Employe postEmployeDto(EmployePostDto employePostDto) {
        Employe employe = new Employe();
        employe.setNom(employePostDto.getNom());
        employe.setPrenom(employePostDto.getPronom());
        return employe;
    }
}
