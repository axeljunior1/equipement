package com.projet.equipement.services;


import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService{

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    public List<Produit> findAll(){
        return produitRepository.findAll();
    }
    public  Produit findById(Long id){
        return  produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }
    public Produit save(ProduitPostDto produitPostDto){
        Produit produit = produitMapper.PostProduitFromDto(produitPostDto);
        return produitRepository.save(produit);
    }


    public Produit updateProduit(ProduitUpdateDto produitUpdateDto, Long id){
        Produit produit = findById(id);
        produitMapper.updateProduitFromDto(produitUpdateDto, produit);
        return produitRepository.save(produit);
    }

    public void deleteById(Long id){
        produitRepository.deleteById(id);
    }
//    public Produit findByCode(String codeBarre) {
//        return produitRepository.findByCode(codeBarre)
//                .orElseThrow(() -> new EntityNotFoundException("Lot", codeBarre));
//    }
}
