package com.projet.equipement.mapper;

import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.CategorieRepository;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.stereotype.Component;


@Component
public class ProduitMapper {


    private final CategorieRepository categorieRepository;

    public ProduitMapper( CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    public void updateProduitFromDto(ProduitUpdateDto produitUpdateDto, Produit produit){
        if (produitUpdateDto.getNom() != null) produit.setNom(produitUpdateDto.getNom());
        if (produitUpdateDto.getDescription() != null) produit.setDescription(produitUpdateDto.getDescription());
        if (produitUpdateDto.getImage() != null) produit.setImage(produitUpdateDto.getImage());
        if (produitUpdateDto.getPrixUnitaire() != null) produit.setPrixUnitaire(produitUpdateDto.getPrixUnitaire());
        if (produitUpdateDto.getStockInitial() != null) produit.setStockInitial(produitUpdateDto.getStockInitial());
        if (produitUpdateDto.getQrCode() != null) produit.setQrCode(produitUpdateDto.getQrCode());
        if (produitUpdateDto.getSeuilProduit() !=null) produit.setSeuilProduit(produitUpdateDto.getSeuilProduit());
        if(produitUpdateDto.getCategorieId() != null) {
            Categorie categorie = categorieRepository.findById(Long.valueOf(produitUpdateDto.getCategorieId())).orElseThrow(
                    () -> new EntityNotFoundException("Produit", Long.valueOf(produitUpdateDto.getCategorieId()))
            );
            produit.setCategorie(categorie);
        };
    }

    public Produit PostProduitFromDto(ProduitPostDto produitPostDto ){
        return Produit.builder()
                .nom(produitPostDto.getNom())
                .description(produitPostDto.getDescription())
                .image(produitPostDto.getImage())
                .prixUnitaire(produitPostDto.getPrixUnitaire())
                .stockInitial(produitPostDto.getStockInitial())
                .actif(true)
                .categorie(categorieRepository.findById(Long.valueOf(produitPostDto.getCategorieId())).orElseThrow(
                        () -> new EntityNotFoundException("Produit", Long.valueOf(produitPostDto.getCategorieId()))
                ))
                .build();
    }

}
