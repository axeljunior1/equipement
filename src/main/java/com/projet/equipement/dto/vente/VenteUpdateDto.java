package com.projet.equipement.dto.vente;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class VenteUpdateDto {

    private Double montantTotal;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private Boolean actif;


}
