package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.services.AchatService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LigneAchatMapper {


    public void updateLigneAchatFromDto(LigneAchatUpdateDto ligneAchatUpdateDto, LigneAchat ligneAchat, AchatService achatService, ProduitService produitService) {
        if (ligneAchatUpdateDto.getPrixAchatUnitaire() != null) ligneAchat.setPrixAchatUnitaire(ligneAchatUpdateDto.getPrixAchatUnitaire());
        if (ligneAchatUpdateDto.getAchatId() != null) {
            Achat achat = achatService.findById(Long.valueOf(ligneAchatUpdateDto.getAchatId())) ;
            ligneAchat.setAchat(achat);
        }
        if (ligneAchatUpdateDto.getProduitId() != null) {
            Produit produit = produitService.findById(Long.valueOf(ligneAchatUpdateDto.getProduitId())) ;
            ligneAchat.setProduit(produit);
        }
        if(ligneAchatUpdateDto.getQuantite() != null)ligneAchat.setQuantite(ligneAchatUpdateDto.getQuantite());
    }

    public LigneAchat postLigneAchatFromDto(LigneAchatPostDto ligneAchatPostDto, AchatService achatService, ProduitService produitService) {
        LigneAchat ligneAchat = LigneAchat.builder()
                .prixAchatUnitaire(ligneAchatPostDto.getPrixAchatUnitaire())
                .produit(produitService.findById(Long.valueOf(ligneAchatPostDto.getProduitId())))
                .achat(achatService.findById(Long.valueOf(ligneAchatPostDto.getAchatId())))
                .quantite(ligneAchatPostDto.getQuantite())
                .build();
        return ligneAchat;
    }


}
