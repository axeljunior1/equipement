package com.projet.equipement.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaiementRequestMomo {
    @NotNull
    private String numero;

    @NotNull
    private String montant;

    @NotNull
    private String referenceId;
}
