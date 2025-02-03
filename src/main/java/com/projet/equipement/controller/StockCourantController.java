package com.projet.equipement.controller;

import com.projet.equipement.entity.StockCourant;
import com.projet.equipement.services.StockCourantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/stock-courant")
@RestController
public class StockCourantController {

    private final StockCourantService stockCourantService;

    public StockCourantController(StockCourantService stockCourantService) {

        this.stockCourantService = stockCourantService;
    }

    @GetMapping("")
    public ResponseEntity<Page<StockCourant>> findAllStockCourant(Pageable pageable) {
        Page<StockCourant> stockCourants = stockCourantService.getStockCourant(pageable);
        return ResponseEntity.ok(stockCourants);
    }

    @PostMapping("/ids")
    public List<StockCourant> getStocksByIdsPaginated( @RequestBody List<Long> ids ) {
        return stockCourantService.getStockCourantByIds(ids);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockCourant> findStockCourantByProduitId(@PathVariable Long id) {
        StockCourant stockCourant = stockCourantService.getStockCourantById(id);
        return ResponseEntity.ok(stockCourant);
    }





}
