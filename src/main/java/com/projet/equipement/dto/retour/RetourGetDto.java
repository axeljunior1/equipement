package com.projet.equipement.dto.retour;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.entity.EtatRetour;
import com.projet.equipement.entity.TypeRetour;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RetourGetDto {

    private Long id ;

    private VenteGetDto vente;

    private TypeRetour type;

    private EtatRetour etat;

    private LocalDateTime dateCreation;

}
