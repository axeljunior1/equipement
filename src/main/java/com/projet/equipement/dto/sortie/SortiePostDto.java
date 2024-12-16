package com.projet.equipement.dto.sortie;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SortiePostDto {

    private String nom;
    private String description;
    private String image;
    @NotNull(message = "La quantité est obligatoire")
    private Integer quantity;
    @NotNull(message = "Le prix unitaire est obligatoire")
    private Integer prixUnitaire;


}
