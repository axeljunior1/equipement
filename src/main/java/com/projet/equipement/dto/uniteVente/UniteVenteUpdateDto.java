package com.projet.equipement.dto.uniteVente;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UniteVenteUpdateDto {
    
    @NotBlank(message = "Le libellé ne peut pas être vide")
    private String libelle;
}