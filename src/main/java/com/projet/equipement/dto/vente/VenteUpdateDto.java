package com.projet.equipement.dto.vente;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VenteUpdateDto {

    private Double montantTotal;

    private LocalDateTime dateDerniereMiseAjour;

    private Integer clientId;

    private Integer employeId;

    private Boolean actif;


}
