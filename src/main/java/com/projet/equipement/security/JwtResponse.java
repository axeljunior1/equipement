package com.projet.equipement.security;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.entity.Panier;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private EmployeGetDto employeGetDto;
    private List<Panier> panier;

}