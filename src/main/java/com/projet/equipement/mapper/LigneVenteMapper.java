package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.Vente;
import org.springframework.stereotype.Component;

@Component
public class LigneVenteMapper {


    public void updateLigneVenteFromDto(LigneVenteUpdateDto ligneVenteUpdateDto, LigneVente ligneVente, Vente vente, Produit produit) {
        if (ligneVenteUpdateDto.getPrixVenteUnitaire() != null) ligneVente.setPrixVenteUnitaire(ligneVenteUpdateDto.getPrixVenteUnitaire());
        if (ligneVenteUpdateDto.getActif() != null) ligneVente.setActif(ligneVenteUpdateDto.getActif());
        if (vente != null) {
            ligneVente.setVente(vente);
        }
        if (produit != null) {
            ligneVente.setProduit(produit);
        }
        if(ligneVenteUpdateDto.getQuantite() != null)ligneVente.setQuantite(ligneVenteUpdateDto.getQuantite());
    }

    public LigneVente postLigneVenteFromDto(LigneVentePostDto ligneVentePostDto, Vente vente, Produit produit) {
        return LigneVente.builder()
                .prixVenteUnitaire(ligneVentePostDto.getPrixVenteUnitaire())
                .produit(produit)
                .vente(vente)
                .quantite(ligneVentePostDto.getQuantite())
                .actif(true)
                .build();
    }
}
