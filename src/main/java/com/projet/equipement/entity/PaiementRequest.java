package com.projet.equipement.entity;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequest {
    private String numero;
    private String montant;
    private String referenceId;
    BigDecimal montantPaiement;
}
