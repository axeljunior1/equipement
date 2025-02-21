package com.projet.equipement.dto.panierProduit;

import com.projet.equipement.dto.employe.EmployeGetDto;
import com.projet.equipement.dto.panier.PanierGetDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PanierProduitGetDto {


    private Long id ;

    private Double prixVente;

    private PanierGetDto panier;

    private ProduitGetDto produit;

    private Integer quantite;


}
