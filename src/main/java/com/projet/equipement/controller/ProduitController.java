package com.projet.equipement.controller;

import com.projet.equipement.entity.Produit;
import com.projet.equipement.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/produit")
@RestController
public class ProduitController {

    @Autowired
    private ProduitService produitService;


    @GetMapping("/{id}")
    public ResponseEntity< Produit> findProduitById(@PathVariable Long id) {
         Produit produit = produitService.findById(id);
        return ResponseEntity.ok(produit);
    }
    @GetMapping
    public ResponseEntity<List<Produit>> findAllProduit() {
        List<Produit> produits = produitService.findAll();
        return ResponseEntity.ok(produits);
    }
    @PostMapping
    public ResponseEntity<Produit> addProduit(Produit produit) {
        produitService.save(produit);
        return ResponseEntity.ok(produit);
    }
    @PutMapping
    public ResponseEntity<Produit> updateProduit(Produit produit) {
        produitService.save(produit);
        return ResponseEntity.ok(produit);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        produitService.deleteById(id);
        return ResponseEntity.ok("Produit deleted");
    }

}
