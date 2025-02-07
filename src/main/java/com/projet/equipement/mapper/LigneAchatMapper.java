package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.Produit;
import org.springframework.stereotype.Component;

@Component
public class LigneAchatMapper {


    public void updateLigneAchatFromDto(LigneAchatUpdateDto ligneAchatUpdateDto, LigneAchat ligneAchat, Achat achat, Produit produit) {
        if (ligneAchatUpdateDto.getPrixAchatUnitaire() != null)
            ligneAchat.setPrixAchatUnitaire(ligneAchatUpdateDto.getPrixAchatUnitaire());
        if (ligneAchatUpdateDto.getActif() != null) ligneAchat.setActif(ligneAchatUpdateDto.getActif());
        if (ligneAchatUpdateDto.getQuantite() != null) ligneAchat.setQuantite(ligneAchatUpdateDto.getQuantite());
        if (produit != null) ligneAchat.setProduit(produit);
        if (achat != null) ligneAchat.setAchat(achat);
    }

    public LigneAchat postLigneAchatFromDto(LigneAchatPostDto ligneAchatPostDto, Achat achat, Produit produit) {
        return LigneAchat.builder()
                .prixAchatUnitaire(ligneAchatPostDto.getPrixAchatUnitaire())
                .quantite(ligneAchatPostDto.getQuantite())
                .achat(achat)
                .produit(produit)
                .actif(true)
                .build();
    }


}
