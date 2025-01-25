package com.projet.equipement.services;


import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ProduitMapper;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Produit> findAll(Pageable pageable){
        return produitRepository.findAll(pageable);
    }
    public  Produit findById(Long id){
        return  produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }

    public List<Produit> rechercherProduits(String motCle){
        return produitRepository.rechercherProduits(motCle);
    }

    public List<Produit> findBySpec(Specification<Produit> spec){
        return produitRepository.findAll(spec);
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

}
