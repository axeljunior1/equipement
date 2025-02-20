package com.projet.equipement.dto.vente;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentePostDto {

    @NotNull
    private Double montantTotal;


    @NotNull
    private Long clientId;
    @NotNull

    @NotNull
    private Long employeId;

    private Boolean actif ;


    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

}
