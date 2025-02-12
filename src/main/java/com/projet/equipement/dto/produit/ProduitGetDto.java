package com.projet.equipement.dto.produit;

import com.projet.equipement.dto.categorie.CategorieGetDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.StockCourant;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitGetDto {

    private Long id;
    private String nom;
    private String description;
    private Integer categorieId;
    private CategorieGetDto categorie;
    private byte[]  qrCode;
    private String  ean13;
    private Integer seuilProduit;
    private Boolean actif;
    @NotNull(message = "La quantité est obligatoire")
    private Integer quantity;
    private LocalDateTime created_at ;
    private String image;
    private Double prixUnitaire;

    private String categorieNom;

    private Integer stockInitial;
    // mis en place dans le controlleur
    private Integer stockCourant;


}
