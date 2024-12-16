package com.projet.equipement.mapper;

import com.projet.equipement.dto.mouvementStock.MouvementStockPostDto;
import com.projet.equipement.dto.mouvementStock.MouvementStockUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.services.EntreeService;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.services.SortieService;
import com.projet.equipement.services.UtilisateurService;
import org.springframework.stereotype.Component;

@Component
public class MouvementStockMapper {


    public void validateMouvementStock(MouvementStockPostDto mouvementStockPostDto) {
        if ((mouvementStockPostDto.getEntreeId() == null && mouvementStockPostDto.getSortieId() == null) ||
                (mouvementStockPostDto.getEntreeId() != null && mouvementStockPostDto.getSortieId() != null)) {
            throw new IllegalArgumentException("Un mouvement doit être lié soit à une entrée, soit à une sortie, pas les deux.");
        }
    }


    public MouvementStock createMouvementStockFromDto(MouvementStockPostDto mouvementStockPostDto, ProduitService produitService, UtilisateurService utilisateurService, EntreeService entreeService, SortieService sortieService) {

        Produit produit = produitService.findById(mouvementStockPostDto.getProduitId());
        Utilisateur utilisateur = utilisateurService.findById(mouvementStockPostDto.getUtilisateurId());
        validateMouvementStock(mouvementStockPostDto);

        MouvementStock mouvementStock = new MouvementStock();

        Entree entree ;
        Sortie sortie;
        if (mouvementStockPostDto.getSortieId() == null) {
            entree = entreeService.findById(mouvementStockPostDto.getUtilisateurId());
            mouvementStock.setTypeMouvement(TypeMouvement.entree);
            mouvementStock.setEntree(entree);
        }
        if (mouvementStockPostDto.getEntreeId() == null) {
            sortie = sortieService.findById(mouvementStockPostDto.getUtilisateurId());
            mouvementStock.setTypeMouvement(TypeMouvement.sortie);
            mouvementStock.setSortie(sortie);

        }


         mouvementStock.setTypeMouvement(mouvementStockPostDto.getTypeMouvement());
        if (mouvementStockPostDto.getQuantity() != null) mouvementStock.setQuantity(mouvementStockPostDto.getQuantity());
        mouvementStock.setProduit(produit);
        mouvementStock.setUtilisateur(utilisateur);

        return mouvementStock;

    }

    public void updateMouvementStockFromDto(MouvementStockUpdateDto mouvementStockUpdateDto, MouvementStock mouvementStock, ProduitService produitService, UtilisateurService utilisateurService, EntreeService entreeService, SortieService sortieService) {
        if (mouvementStockUpdateDto.getTypeMouvement() != null) mouvementStock.setTypeMouvement(mouvementStock.getTypeMouvement());
        if (mouvementStockUpdateDto.getQuantity() != null) mouvementStock.setQuantity(mouvementStock.getQuantity());


        if (mouvementStockUpdateDto.getProduitId() != null) {
            Produit produit = produitService.findById(mouvementStockUpdateDto.getProduitId());
            mouvementStock.setProduit(produit);
        }
        if (mouvementStockUpdateDto.getEntreeId() != null) {
            Entree entree = entreeService.findById(mouvementStockUpdateDto.getEntreeId());
            mouvementStock.setEntree(entree);
        }
        if (mouvementStockUpdateDto.getSortieId() != null) {
            Sortie sortie = sortieService.findById(mouvementStockUpdateDto.getSortieId());
            mouvementStock.setSortie(sortie);
        }
        if (mouvementStockUpdateDto.getUtilisateurId() != null) {
            Utilisateur utilisateur = utilisateurService.findById(mouvementStockUpdateDto.getUtilisateurId());
            mouvementStock.setUtilisateur(utilisateur);
        }
    }


}
