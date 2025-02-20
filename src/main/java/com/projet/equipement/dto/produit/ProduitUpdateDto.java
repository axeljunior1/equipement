package com.projet.equipement.dto.produit;

import jakarta.validation.constraints.NotNull;
import lombok.*;



@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitUpdateDto {

    private String nom;
    private String description;
    private String image;
    @NotNull(message = "La quantité est obligatoire")
    private Integer quantity;
    @NotNull(message = "Le prix unitaire est obligatoire")
    private Double prixUnitaire;
    private Integer seuilProduit;
    private Long categorieId;

    private byte[] qrCode;

    private Integer stockInitial;



}
