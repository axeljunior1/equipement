package com.projet.equipement.dto.ligneVente;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LigneVentePostDto {

    private Double prixVente;

    private Integer quantite;

    private Long venteId;

    private Long produitId;

    private Long formatVenteId;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


}
