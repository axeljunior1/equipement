package com.projet.equipement.dto.ligneAchat;

import com.projet.equipement.entity.LigneAchat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneAchatGetDto {

    private Long id ;

    private Double prixUnitaire;

    private Integer quantite;

    private Long achatId;

    private Boolean actif;

    private Long produitId;

    private String  produitNom;


    public LigneAchatGetDto(LigneAchat ligneAchat) {
        this.id = ligneAchat.getId();
        this.achatId = ligneAchat.getAchat().getId();
        this.produitId = ligneAchat.getProduit().getId();
        this.quantite = ligneAchat.getQuantite();
        this.prixUnitaire = ligneAchat.getPrixAchatUnitaire();
        this.produitNom = ligneAchat.getProduit().getNom();
        this.actif = ligneAchat.getActif();
    }

}
