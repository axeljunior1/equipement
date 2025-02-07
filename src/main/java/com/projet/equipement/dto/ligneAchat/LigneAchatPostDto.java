package com.projet.equipement.dto.ligneAchat;

import com.projet.equipement.entity.LigneAchat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAchatPostDto {

    private Double prixAchatUnitaire;

    private Integer quantite;

    private Integer achatId;

    private Integer produitId;

}
