package com.projet.equipement.dto.achat;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.etatDto.EtatAchatGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AchatGetDto {


    private Long id ;

    private Double montantTotal;

    private Long employeId;

    private String  employeNom;

    private EtatAchatGetDto etat;

    private EmployeGetDto employe;

    private boolean  actif;

    private LocalDateTime dateCreation;

    private LocalDateTime updatedAt;


}
