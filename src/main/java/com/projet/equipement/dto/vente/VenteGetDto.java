package com.projet.equipement.dto.vente;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.entity.Client;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class VenteGetDto {

    private Long id ;

    private Double montantTotal;

    private LocalDateTime dateDerniereMiseAjour;

    private Long clientId;

    private String clientNom;

    private Client client;

    private Boolean actif;

    private Long employeId;

    private String employeNom;

    private EmployeGetDto employe;

    private List<Long> lignesVenteId;


}
