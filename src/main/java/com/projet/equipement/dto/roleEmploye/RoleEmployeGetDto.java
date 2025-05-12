package com.projet.equipement.dto.roleEmploye;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.role.RoleGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleEmployeGetDto {

    private String tenantId;
    private EmployeGetDto employe;
    private RoleGetDto role;


}
