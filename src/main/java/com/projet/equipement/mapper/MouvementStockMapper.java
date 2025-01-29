package com.projet.equipement.mapper;

import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.CategorieRepository;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.repository.TypeMouvementStockRepository;
import org.springframework.stereotype.Component;


@Component
public class MouvementStockMapper {


    private final ProduitRepository produitRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final TypeMouvementStockRepository typeMouvementStockRepository;

    public MouvementStockMapper(ProduitRepository produitRepository, MouvementStockRepository mouvementStockRepository, TypeMouvementStockRepository typeMouvementStockRepository) {
        this.produitRepository = produitRepository;
        this.mouvementStockRepository = mouvementStockRepository;
        this.typeMouvementStockRepository = typeMouvementStockRepository;
    }

    public void updateMouvementStockFromDto(MouvementStockUpdateDto mouvementStockUpdateDto, MouvementStock mouvementStock){
        if (mouvementStockUpdateDto.getReference() != null) mouvementStock.setReference(mouvementStockUpdateDto.getReference());
        if (mouvementStockUpdateDto.getCommentaire() != null) mouvementStock.setCommentaire(mouvementStockUpdateDto.getCommentaire());
        if(mouvementStockUpdateDto.getTypeMouvementCode() !=null) mouvementStock.setTypeMouvement(mouvementStockUpdateDto.getTypeMouvementCode());
        if(mouvementStockUpdateDto.getProduitId() != null) {
            Produit produit = produitRepository.findById(mouvementStock.getId()).orElseThrow(
                    () -> new EntityNotFoundException("MouvementStock", mouvementStock.getId())
            );
            mouvementStock.setProduit(produit);
        };
    }

    public MouvementStock PostMouvementStockFromDto(MouvementStockPostDto mouvementStockPostDto ){
        TypeMouvementStock typeMouvementStock = typeMouvementStockRepository.findByCode(mouvementStockPostDto.getTypeMouvementCode());
        return MouvementStock.builder()
                .reference(mouvementStockPostDto.getReference())
                .commentaire(mouvementStockPostDto.getCommentaire())
                .typeMouvement(typeMouvementStock)
                .quantite(mouvementStockPostDto.getQuantite())
                .produit(produitRepository.findById(mouvementStockPostDto.getProduitId()).orElseThrow(()->{
                    return new EntityNotFoundException("Produit", mouvementStockPostDto.getProduitId());
                }))
                .dateMouvement(mouvementStockPostDto.getDateMouvement())
                .build();
    }

}
