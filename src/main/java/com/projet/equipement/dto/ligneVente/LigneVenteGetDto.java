package com.projet.equipement.dto.ligneVente;

import com.projet.equipement.entity.LigneVente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneVenteGetDto {

    private Long id;
    private Double prixUnitaire;

    private Integer quantite;

    private Long venteId;

    private Long produitId;

    private String  produitNom;

    public LigneVenteGetDto(LigneVente ligneVente) {
        this.id = ligneVente.getId();
        this.prixUnitaire = ligneVente.getPrixVenteUnitaire();
        this.quantite = ligneVente.getQuantite();
        this.produitId = ligneVente.getProduit().getId();
        this.venteId = ligneVente.getVente().getId();
        this.produitNom = ligneVente.getProduit().getNom();
    }

}
