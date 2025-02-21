package com.projet.equipement.dto.facture;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FacturePostDTO {

    private Long venteId;
    private Double montantTotal;
    @Builder.Default
    private String statut = "CREEE";
    private String modePaiement;
}
