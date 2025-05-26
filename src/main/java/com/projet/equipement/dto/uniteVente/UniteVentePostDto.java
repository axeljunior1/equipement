package com.projet.equipement.dto.uniteVente;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UniteVentePostDto {

    @NotBlank(message = "Le code ne peut pas être vide")
    private String code;
    
    @NotBlank(message = "Le libellé ne peut pas être vide")
    private String libelle;
}