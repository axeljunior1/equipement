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

    private Double prixVenteUnitaire;

    private Integer quantite;

    private Integer venteId;

    private Integer produitId;


}
