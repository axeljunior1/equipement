package com.projet.equipement.services;


import com.projet.equipement.entity.Produit;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService{
    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> findAll(){
        return produitRepository.findAll();
    }
    public Optional<Produit> findById(int id){
        return Optional.of(produitRepository.findById(id)).get();
    }
    public Produit save(Produit produit){
        return produitRepository.save(produit);
    }
    public void deleteById(int id){
        produitRepository.deleteById(id);
    }

}
