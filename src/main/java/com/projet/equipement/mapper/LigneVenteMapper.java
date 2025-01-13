package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.services.ClientService;
import com.projet.equipement.services.EmployeService;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.services.VenteService;
import org.springframework.stereotype.Component;

@Component
public class LigneVenteMapper {


    public void updateLigneVenteFromDto(LigneVenteUpdateDto ligneVenteUpdateDto, LigneVente ligneVente, VenteService venteService, ProduitService produitService) {
        if (ligneVenteUpdateDto.getPrixVenteUnitaire() != null) ligneVente.setPrixVenteUnitaire(ligneVenteUpdateDto.getPrixVenteUnitaire());
        if (ligneVenteUpdateDto.getVenteId() != null) {
            Vente vente = venteService.findById(Long.valueOf(ligneVenteUpdateDto.getVenteId())) ;
            ligneVente.setVente(vente);
        }
        if (ligneVenteUpdateDto.getProduitId() != null) {
            Produit produit = produitService.findById(Long.valueOf(ligneVenteUpdateDto.getProduitId())) ;
            ligneVente.setProduit(produit);
        }
        if(ligneVenteUpdateDto.getQuantite() != null)ligneVente.setQuantite(ligneVenteUpdateDto.getQuantite());
    }

    public LigneVente postLigneVenteFromDto(LigneVentePostDto ligneVentePostDto, VenteService venteService, ProduitService produitService) {
        LigneVente ligneVente = LigneVente.builder()
                .prixVenteUnitaire(ligneVentePostDto.getPrixVenteUnitaire())
                .produit(produitService.findById(Long.valueOf(ligneVentePostDto.getProduitId())))
                .vente(venteService.findById(Long.valueOf(ligneVentePostDto.getVenteId())))
                .quantite(ligneVentePostDto.getQuantite())
                .build();
        return ligneVente;
    }
}
