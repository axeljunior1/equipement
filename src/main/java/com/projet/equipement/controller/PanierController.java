package com.projet.equipement.controller;

import com.projet.equipement.entity.Panier;
import com.projet.equipement.services.PanierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/panier")
@RestController
public class PanierController {


    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Panier>> findAllPaniers(Pageable pageable) {
        Page<Panier> paniers = panierService.findAll(pageable);
        return ResponseEntity.ok(paniers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Panier> findPanier(@PathVariable Long id) {
        Panier panier = panierService.findById(id);
        return ResponseEntity.ok(panier);
    }

    @GetMapping("/employe/{id}")
    public ResponseEntity<List<Panier>> findPanierLineById(@PathVariable Long id) {
        List<Panier> lignePaniers = panierService.findAllByEmployeId(id);
        return ResponseEntity.ok(lignePaniers);
    }







    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePanier(@PathVariable Long id) {
        panierService.deleteById(id);
        return ResponseEntity.ok("Panier deleted");
    }


}
