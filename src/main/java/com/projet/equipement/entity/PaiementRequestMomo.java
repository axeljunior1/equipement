package com.projet.equipement.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;


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
