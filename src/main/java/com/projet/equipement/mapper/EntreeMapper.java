package com.projet.equipement.mapper;

import com.projet.equipement.dto.entree.EntreePostDto;
import com.projet.equipement.dto.entree.EntreeUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.services.FournisseurService;
import com.projet.equipement.services.MouvementStockService;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.services.UtilisateurService;
import org.springframework.stereotype.Component;

@Component
public class EntreeMapper {

    private final MouvementStockRepository mouvementStockRepository;

    public EntreeMapper(MouvementStockRepository mouvementStockRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
    }

    public Entree createEntreeFromDto(EntreePostDto entreePostDto, ProduitService produitService, FournisseurService fournisseurService, UtilisateurService utilisateurService) {

        Produit produit = produitService.findById(entreePostDto.getProduitId());
        Fournisseur fournisseur = fournisseurService.findById(entreePostDto.getFournisseurId());
        Utilisateur utilisateur = utilisateurService.findById(entreePostDto.getUtilisateurId());

        return Entree.builder()
                .produit(produit)
                .fournisseur(fournisseur)
                .utilisateur(utilisateur)
                .quantite(entreePostDto.getQuantite())
                .prix_achat(entreePostDto.getPrixAchat())
                .build();

    }

    public void updateEntreeFromDto(EntreeUpdateDto entreeUpdateDto, Entree entree, ProduitService produitService, FournisseurService fournisseurService, UtilisateurService utilisateurService) {
        if (entreeUpdateDto.getPrixAchat() != null) entree.setPrix_achat(entreeUpdateDto.getPrixAchat());
        if (entreeUpdateDto.getQuantite() != null) entree.setQuantite(entreeUpdateDto.getQuantite());

        if (entreeUpdateDto.getProduitId() != null) {
            Produit produit = produitService.findById(entreeUpdateDto.getProduitId());
            entree.setProduit(produit);
        }
        if (entreeUpdateDto.getFournisseurId() != null) {
            Fournisseur fournisseur = fournisseurService.findById(entreeUpdateDto.getFournisseurId());
            entree.setFournisseur(fournisseur);
        }
        if (entreeUpdateDto.getUtilisateurId() != null) {
            Utilisateur utilisateur = utilisateurService.findById(entreeUpdateDto.getUtilisateurId());
            entree.setUtilisateur(utilisateur);
        }
        if (entreeUpdateDto.getMouvementId() != null) {
            MouvementStock mouvementStock = mouvementStockRepository.findById(entreeUpdateDto.getMouvementId()).orElseThrow(() -> new EntityNotFoundException("MouvementStock", entreeUpdateDto.getMouvementId()));
            entree.setMouvementStock(mouvementStock);
        }
    }
}
