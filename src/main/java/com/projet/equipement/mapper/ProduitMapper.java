package com.projet.equipement.mapper;

import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import org.springframework.stereotype.Component;


@Component
public class ProduitMapper {



    public void updateProduitFromDto(ProduitUpdateDto produitUpdateDto, Produit produit){
        if (produitUpdateDto.getNom() != null) produit.setNom(produitUpdateDto.getNom());
        if (produitUpdateDto.getDescription() != null) produit.setDescription(produitUpdateDto.getDescription());
        if (produitUpdateDto.getImage() != null) produit.setImage(produitUpdateDto.getImage());
        if (produitUpdateDto.getPrixUnitaire() != null) produit.setPrixUnitaire(produitUpdateDto.getPrixUnitaire());
        if (produitUpdateDto.getQuantity() != null) produit.setQuantity(produitUpdateDto.getQuantity());
    }

}
