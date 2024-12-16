package com.projet.equipement.controller;

import com.projet.equipement.dto.mouvementStock.MouvementStockPostDto;
import com.projet.equipement.dto.mouvementStock.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.services.MouvementStockService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/mouvement-stock")
@RestController
@Tag(name = "Mouvement", description = "Gestion des mouvements de stock")
public class MouvementStockController {
    
    private final MouvementStockService mouvementStockService;

    public MouvementStockController(MouvementStockService mouvementStockService) {
        this.mouvementStockService = mouvementStockService;
    }

    @GetMapping("")
    public ResponseEntity<List<MouvementStock>> findAllMouvementStocks() {
        List<MouvementStock> mouvementStocks = mouvementStockService.findAll();
        return ResponseEntity.ok(mouvementStocks) ;
    }

    @ApiResponse(responseCode = "200", description = "Retourne le mouvement de stock")
    @GetMapping("/{id}")
    public ResponseEntity< MouvementStock> findMouvementStock(@PathVariable Long id) {
        MouvementStock mouvementStock = mouvementStockService.findById(id);
        return ResponseEntity.ok(mouvementStock);
    }
    @PostMapping
    public ResponseEntity<MouvementStock> addMouvementStock(@RequestBody MouvementStockPostDto mouvementStockPostDto) {
       MouvementStock mouvementStock = mouvementStockService.save(mouvementStockPostDto);
        return ResponseEntity.ok(mouvementStock);
    }
    @PatchMapping
    public ResponseEntity<MouvementStock> updateMouvementStock(@PathVariable Long id,@RequestBody MouvementStockUpdateDto mouvementStockUpdateDto) {
       MouvementStock mouvementStock = mouvementStockService.updateMouvementStock(mouvementStockUpdateDto, id);
        return ResponseEntity.ok(mouvementStock);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMouvementStock(@PathVariable Long id) {
        mouvementStockService.deleteById(id);
        return ResponseEntity.ok("MouvementStock deleted");
    }
    
    
}
