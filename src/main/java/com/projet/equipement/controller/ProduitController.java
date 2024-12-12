package com.projet.equipement.controller;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/produit")
@RestController
public class ProduitController {

    @Autowired
    private ProduitService produitService;


    public ResponseEntity<Optional<Produit>> findProduitById(int id) {
        Optional<Produit> produit = produitService.findById(id);
        return ResponseEntity.ok(produit);
    }
    public ResponseEntity<List<Produit>> findAllProduit() {
        List<Produit> produits = produitService.findAll();
        return ResponseEntity.ok(produits);
    }
    public ResponseEntity<Produit> addProduit(Produit produit) {
        produitService.save(produit);
        return ResponseEntity.ok(produit);
    }
    public ResponseEntity<Produit> updateProduit(Produit produit) {
        produitService.save(produit);
        return ResponseEntity.ok(produit);
    }
    public ResponseEntity<String> deleteProduit(int id) {
        produitService.deleteById(id);
        return ResponseEntity.ok("Produit deleted");
    }

}
