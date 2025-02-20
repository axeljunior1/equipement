package com.projet.equipement.dto.produit;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


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
    private Double prixVente;

    private Integer seuilProduit;

    private Long categorieId;

    private byte[] qrCode;

    private Integer stockInitial;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();



}
