package com.projet.equipement.dto.panierProduit;

import com.projet.equipement.dto.panier.PanierGetDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PanierProduitGetDto {


    private Long id ;

    private Double prixVente;

    private PanierGetDto panier;

    private ProduitGetDto produit;

    private Integer quantite;

    private Long formatVenteId;


}
