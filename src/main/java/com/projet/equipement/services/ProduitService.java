package com.projet.equipement.services;


import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService{
    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> findAll(){
        return produitRepository.findAll();
    }
    public  Produit findById(Long id){
        return  produitRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produit", id));
    }
    public Produit save(Produit produit){
        return produitRepository.save(produit);
    }



    public void deleteById(Long id){
        produitRepository.deleteById(id);
    }

}
