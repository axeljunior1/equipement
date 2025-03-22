package com.projet.equipement.dto.produit;

import com.projet.equipement.dto.categorie.CategorieGetDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
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
    private LocalDateTime createdAt ;
    private LocalDateTime updatedAt ;
    private String image;
    private Double prixVente;
    private BigDecimal prixAchat;

    private String categorieNom;

    private Integer stockInitial;
    // mis en place dans le controller
    private Integer stockCourant;


}
