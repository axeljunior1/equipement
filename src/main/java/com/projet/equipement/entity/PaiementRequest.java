package com.projet.equipement.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequest {
    @NotNull
    private Long modePaiementId;

    @NotNull
    @Positive
    BigDecimal montantPaiement;
}
