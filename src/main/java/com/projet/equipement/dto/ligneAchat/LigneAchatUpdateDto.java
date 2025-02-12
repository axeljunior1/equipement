package com.projet.equipement.dto.ligneAchat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAchatUpdateDto {

    private Double prixAchatUnitaire;

    private Integer quantite;

    private Long achatId;

    private Long produitId;

    private Boolean actif;


}
