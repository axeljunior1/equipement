package com.projet.equipement.dto.uniteVente;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UniteVenteGetDto {
    private Long id;
    
    @NotBlank(message = "Le code ne peut pas être vide")
    private String code;
    
    @NotBlank(message = "Le libellé ne peut pas être vide")
    private String libelle;
}