package com.projet.equipement.dto.ligneAchat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAchatPostDto {

    private Integer prixAchatUnitaire;

    private Integer quantite;

    private Integer achatId;

    private Integer produitId;


}
