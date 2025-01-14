package com.projet.equipement.controller;

import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PatchMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, ProduitUpdateDto produitUpdateDto) {
         Produit produit =  produitService.updateProduit(produitUpdateDto, id);
        return ResponseEntity.ok(produit);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        produitService.deleteById(id);
        return ResponseEntity.ok("Produit deleted");
    }

}
