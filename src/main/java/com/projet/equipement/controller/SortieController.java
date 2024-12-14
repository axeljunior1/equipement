package com.projet.equipement.controller;

import com.projet.equipement.entity.Sortie;
import com.projet.equipement.services.SortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/sortie")
@RestController
public class SortieController {
    
    @Autowired
    private SortieService sortieService;

    @GetMapping("")
    public ResponseEntity<List<Sortie>> findAllSorties() {
        List<Sortie> sorties = sortieService.findAll();
        return ResponseEntity.ok(sorties) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< Sortie> findSortie(@PathVariable Long id) {
         Sortie sortie = sortieService.findById(id);
        return ResponseEntity.ok(sortie);
    }
    @PostMapping("")
    public ResponseEntity<Sortie> addSortie(@RequestBody Sortie sortie) {
        sortie = sortieService.save(sortie);
        return ResponseEntity.ok(sortie);
    }
    @PutMapping("")
    public ResponseEntity<Sortie> updateSortie(@RequestBody Sortie sortie) {
        sortie = sortieService.save(sortie);
        return ResponseEntity.ok(sortie);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSortie(Long id) {
        sortieService.deleteById(id);
        return ResponseEntity.ok("Sortie deleted");
    }
    
    
}
