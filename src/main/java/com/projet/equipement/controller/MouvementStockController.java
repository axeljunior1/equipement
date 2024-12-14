package com.projet.equipement.controller;

import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.services.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/mouvement-stock")
@RestController
public class MouvementStockController {
    
    @Autowired
    private MouvementStockService mouvementStockService;

    @GetMapping("")
    public ResponseEntity<List<MouvementStock>> findAllMouvementStocks() {
        List<MouvementStock> mouvementStocks = mouvementStockService.findAll();
        return ResponseEntity.ok(mouvementStocks) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< MouvementStock> findMouvementStock(@PathVariable Long id) {
         MouvementStock mouvementStock = mouvementStockService.findById(id);
        return ResponseEntity.ok(mouvementStock);
    }
    @PostMapping
    public ResponseEntity<MouvementStock> addMouvementStock(@RequestBody MouvementStock mouvementStock) {
        mouvementStock = mouvementStockService.save(mouvementStock);
        return ResponseEntity.ok(mouvementStock);
    }
    @PutMapping
    public ResponseEntity<MouvementStock> updateMouvementStock(@RequestBody MouvementStock mouvementStock) {
        mouvementStock = mouvementStockService.save(mouvementStock);
        return ResponseEntity.ok(mouvementStock);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMouvementStock(@PathVariable Long id) {
        mouvementStockService.deleteById(id);
        return ResponseEntity.ok("MouvementStock deleted");
    }
    
    
}
