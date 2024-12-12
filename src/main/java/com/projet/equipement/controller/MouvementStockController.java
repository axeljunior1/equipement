package com.projet.equipement.controller;

import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.services.MouvementStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/mouvementStock")
@RestController
public class MouvementStockController {
    
    @Autowired
    private MouvementStockService mouvementStockService;
    
    public ResponseEntity<List<MouvementStock>> findAllMouvementStocks() {
        List<MouvementStock> mouvementStocks = mouvementStockService.findAll();
        return ResponseEntity.ok(mouvementStocks) ;
    }
    public ResponseEntity<Optional<MouvementStock>> findMouvementStock(int id) {
        Optional<MouvementStock> mouvementStock = mouvementStockService.findById(id);
        return ResponseEntity.ok(mouvementStock);
    }
    public ResponseEntity<MouvementStock> addMouvementStock(MouvementStock mouvementStock) {
        mouvementStock = mouvementStockService.save(mouvementStock);
        return ResponseEntity.ok(mouvementStock);
    }
    public ResponseEntity<MouvementStock> updateMouvementStock(int id, MouvementStock mouvementStock) {
        mouvementStock = mouvementStockService.save(mouvementStock);
        return ResponseEntity.ok(mouvementStock);
    }
    public ResponseEntity<String> deleteMouvementStock(int id) {
        mouvementStockService.deleteById(id);
        return ResponseEntity.ok("MouvementStock deleted");
    }
    
    
}
