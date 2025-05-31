package com.projet.equipement.controller;

import com.projet.equipement.dto.mvt_stk.MouvementStockGetDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.services.MouvementStockService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/mouvements-stock")
@RestController
public class MouvementStockController {

    private final MouvementStockService mouvementStockService;
    private final MouvementStockMapper mouvementStockMapper;

    public MouvementStockController(MouvementStockService mouvementStockService, MouvementStockMapper mouvementStockMapper) {
        this.mouvementStockService = mouvementStockService;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<MouvementStockGetDto>> findAllMouvementStock(Pageable pageable) {
        Page <MouvementStock> mouvementStocks = mouvementStockService.findAll(pageable);
        return ResponseEntity.ok(mouvementStocks.map(mouvementStockMapper::toDto));
    }

    @GetMapping("/produit/{id}")
    public ResponseEntity<Page<MouvementStockGetDto>> findAllMouvementStockByProductId(@PathVariable Long id, Pageable pageable) {
        Page <MouvementStock> mouvementStocks = mouvementStockService.findAllMouvementStockByProductId(id, pageable);
        return ResponseEntity.ok(mouvementStocks.map(mouvementStockMapper::toDto));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MouvementStock> findMouvementStock(@PathVariable Long id) {
         MouvementStock mouvementStock = mouvementStockService.findById(id);
        return ResponseEntity.ok(mouvementStock);
    }

    @PostMapping("")
    public ResponseEntity<MouvementStock> save(@RequestBody MouvementStockPostDto mouvementStockPostDto){
        return ResponseEntity.ok(mouvementStockService.save(mouvementStockPostDto));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<MouvementStock> updateMouvementStock(@PathVariable Long id , @Valid  @RequestBody MouvementStockUpdateDto mouvementStockUpdateDto) {
        MouvementStock mouvementStock = mouvementStockService.updateMouvementStock(mouvementStockUpdateDto, id);
        return ResponseEntity.ok(mouvementStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMouvementStock(@PathVariable Long id ) {
        mouvementStockService.deleteById(id);
        return ResponseEntity.ok("MouvementStock deleted");
    }



}
