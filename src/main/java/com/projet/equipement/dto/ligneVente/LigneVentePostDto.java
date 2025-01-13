package com.projet.equipement.dto.ligneVente;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.Vente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LigneVentePostDto {

    private Integer prixVenteUnitaire;

    private Integer quantite;

    private Integer venteId;

    private Integer produitId;


}
