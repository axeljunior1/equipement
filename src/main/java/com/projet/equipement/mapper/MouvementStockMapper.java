package com.projet.equipement.mapper;

import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.services.TypeMouvementStockService;
import org.springframework.stereotype.Component;


@Component
public class MouvementStockMapper {


    private final ProduitRepository produitRepository;
    private final TypeMouvementStockService typeMouvementStockService;

    public MouvementStockMapper(ProduitRepository produitRepository, TypeMouvementStockService typeMouvementStockService) {
        this.produitRepository = produitRepository;
        this.typeMouvementStockService = typeMouvementStockService;
    }

    public void updateMouvementStockFromDto(MouvementStockUpdateDto mouvementStockUpdateDto, MouvementStock mouvementStock){
        if (mouvementStockUpdateDto.getReference() != null) mouvementStock.setReference(mouvementStockUpdateDto.getReference());
        if (mouvementStockUpdateDto.getCommentaire() != null) mouvementStock.setCommentaire(mouvementStockUpdateDto.getCommentaire());
        if(mouvementStockUpdateDto.getTypeMouvementCode() !=null) mouvementStock.setTypeMouvement(mouvementStockUpdateDto.getTypeMouvementCode());
        if(mouvementStockUpdateDto.getIdEvenementOrigine() != null) mouvementStock.setIdEvenementOrigine(mouvementStockUpdateDto.getIdEvenementOrigine());
        if(mouvementStockUpdateDto.getIdLigneOrigine() != null) mouvementStock.setIdLigneOrigine(mouvementStockUpdateDto.getIdLigneOrigine());
        if(mouvementStockUpdateDto.getProduitId() != null) {
            Produit produit = produitRepository.findById(mouvementStock.getId()).orElseThrow(
                    () -> new EntityNotFoundException("MouvementStock", mouvementStock.getId())
            );
            mouvementStock.setProduit(produit);
        };
    }



    public MouvementStock PostMouvementStockFromDto(MouvementStockPostDto mouvementStockPostDto ){
        TypeMouvementStock typeMouvementStock = typeMouvementStockService.findByCode(mouvementStockPostDto.getTypeMouvementCode());
        return MouvementStock.builder()
                .reference(mouvementStockPostDto.getReference())
                .commentaire(mouvementStockPostDto.getCommentaire())
                .typeMouvement(typeMouvementStock)
                .quantite(mouvementStockPostDto.getQuantite())
                .idLigneOrigine(mouvementStockPostDto.getIdLigneOrigine())
                .idEvenementOrigine(mouvementStockPostDto.getIdEvenementOrigine())
                .produit(produitRepository.findById(mouvementStockPostDto.getProduitId()).orElseThrow(()-> new EntityNotFoundException("Produit", mouvementStockPostDto.getProduitId())))
                .dateMouvement(mouvementStockPostDto.getDateMouvement())
                .createdAt(mouvementStockPostDto.getCreatedAt())
                .build();
    }

}
