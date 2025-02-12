package com.projet.equipement.dto.categorie;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoriePostDto {

    @NotBlank
    private String nom;

    @NotBlank
    private String description;
}
