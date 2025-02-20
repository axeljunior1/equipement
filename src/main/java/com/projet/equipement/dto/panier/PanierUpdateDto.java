package com.projet.equipement.dto.panier;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class PanierUpdateDto {

    private Double prixVente;

    private Long employeId;

    private Long etatId;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();



}
