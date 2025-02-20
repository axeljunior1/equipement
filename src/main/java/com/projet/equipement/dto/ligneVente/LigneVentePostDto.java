package com.projet.equipement.dto.ligneVente;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.Vente;
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

    private Integer venteId;

    private Integer produitId;

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();


}
