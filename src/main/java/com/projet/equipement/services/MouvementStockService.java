package com.projet.equipement.services;


import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MouvementStockService {

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;

    public MouvementStockService(ProduitRepository produitRepository, ProduitMapper produitMapper, MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    public Page<MouvementStock> findAll(Pageable pageable){
        return mouvementStockRepository.findAll(pageable);
    }

    public  MouvementStock findById(Long id){
        return  mouvementStockRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }

//    public List<MouvementStock> rechercherProduits(String motCle){
//        return mouvementStockRepository.rechercherProduits(motCle);
//    }
//
//    public List<MouvementStock> findBySpec(Specification<MouvementStock> spec){
//        return mouvementStockRepository.findAll(spec);
//    }

    public MouvementStock save(MouvementStockPostDto mouvementStockPostDto){
        MouvementStock mouvementStock = mouvementStockMapper.PostMouvementStockFromDto(mouvementStockPostDto);
        return mouvementStockRepository.save(mouvementStock);
    }


    public MouvementStock updateMouvementStock(MouvementStockUpdateDto mouvementStockUpdateDto, Long id){
        MouvementStock mouvementStock = findById(id);
        mouvementStockMapper.updateMouvementStockFromDto(mouvementStockUpdateDto, mouvementStock);
        return mouvementStockRepository.save(mouvementStock);
    }

    public void deleteById(Long id){
        mouvementStockRepository.deleteById(id);
    }

    public Page<MouvementStock> findAllMouvementStockByProductId(Long id, Pageable pageable) {

        return mouvementStockRepository.findAllMouvementStockByProductId(id, pageable) ;
    }
}
