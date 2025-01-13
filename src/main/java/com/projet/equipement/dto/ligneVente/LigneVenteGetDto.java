package com.projet.equipement.dto.ligneVente;

import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Vente;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LigneVenteGetDto {

    private Long id;
    private Integer prixVenteUnitaire;

    private Integer quantite;

    private Long venteId;

    private Long produitId;

    public LigneVenteGetDto(LigneVente ligneVente) {
        this.id = ligneVente.getId();
        this.prixVenteUnitaire = ligneVente.getPrixVenteUnitaire();
        this.quantite = ligneVente.getQuantite();
        this.produitId = ligneVente.getProduit().getId();
        this.venteId = ligneVente.getVente().getId();
    }

}
