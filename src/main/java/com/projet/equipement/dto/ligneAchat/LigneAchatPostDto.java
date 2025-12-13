package com.projet.equipement.dto.ligneAchat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAchatPostDto {

    private Double prixAchat;

    private Double prixAchatF;

    private Integer quantite;

    private Long achatId;

    private Long produitId;

}
