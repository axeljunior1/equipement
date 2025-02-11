package com.projet.equipement.mapper;

import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.Produit;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;


@Component
public class ProduitMapper {


    public void updateProduitFromDto(ProduitUpdateDto produitUpdateDto, Produit produit, Categorie categorie){
        if (produitUpdateDto.getNom() != null) produit.setNom(produitUpdateDto.getNom());
        if (produitUpdateDto.getDescription() != null) produit.setDescription(produitUpdateDto.getDescription());
        if (produitUpdateDto.getImage() != null) produit.setImage(produitUpdateDto.getImage());
        if (produitUpdateDto.getPrixUnitaire() != null) produit.setPrixUnitaire(produitUpdateDto.getPrixUnitaire());
        if (produitUpdateDto.getStockInitial() != null) produit.setStockInitial(produitUpdateDto.getStockInitial());
        if (produitUpdateDto.getQrCode() != null) produit.setQrCode(produitUpdateDto.getQrCode());
        if (produitUpdateDto.getSeuilProduit() !=null) produit.setSeuilProduit(produitUpdateDto.getSeuilProduit());
        if(categorie != null) produit.setCategorie(categorie);
    }




    public Produit PostProduitFromDto(ProduitPostDto produitPostDto, @NotNull Categorie categorie ){
        return Produit.builder()
                .nom(produitPostDto.getNom())
                .description(produitPostDto.getDescription())
                .image(produitPostDto.getImage())
                .prixUnitaire(produitPostDto.getPrixUnitaire())
                .stockInitial(produitPostDto.getStockInitial())
                .actif(true)
                .categorie(categorie)
                .build();
    }

}
