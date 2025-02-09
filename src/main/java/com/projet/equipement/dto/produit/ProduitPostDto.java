package com.projet.equipement.dto.produit;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitPostDto {

    private String nom;

    private String description;

    private String image;

    @NotNull(message = "La quantité est obligatoire")
    private Integer quantity;

    @NotNull(message = "Le prix unitaire est obligatoire")
    private Double prixUnitaire;

    private Integer categorieId;

    private Integer seuilProduit;


    private Integer stockInitial;
    private LocalDateTime creationDate;



}
