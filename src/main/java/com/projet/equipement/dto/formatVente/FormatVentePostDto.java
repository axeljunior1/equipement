package com.projet.equipement.dto.formatVente;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.UniteVente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FormatVentePostDto {

    @NotNull(message = "L'ID du produit est obligatoire")
    private Long produitId;


    @NotNull(message = "L'ID de l'unité de vente est obligatoire")
    private Long uniteVenteId;


    @NotBlank(message = "Le libellé du format ne peut pas être vide")
    private String libelleFormat;

    @Positive(message = "La quantité par format doit être positive")
    private int quantiteParFormat;

    @NotNull(message = "Le prix de vente est obligatoire")
    @Positive(message = "Le prix de vente doit être positif")
    private BigDecimal prixVente;

}