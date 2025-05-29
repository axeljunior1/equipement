package com.projet.equipement.dto.panierProduit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PanierProduitPostDto {

    private Double prixVente;

    private Long panierId;

    private Long produitId;

    private Integer quantite;

    private Long formatVenteId;


}
