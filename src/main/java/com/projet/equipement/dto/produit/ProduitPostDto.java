package com.projet.equipement.dto.produit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitPostDto {

    @NotBlank
    private String nom;

    @NotBlank
    private String description;


    private String ean13 ;

    private String image;

    @NotNull(message = "Le prix unitaire est obligatoire")
    private Double prixVente;

    @NotNull
    private Long categorieId;

    private Integer seuilProduit;


    private Integer stockInitial;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();



}
