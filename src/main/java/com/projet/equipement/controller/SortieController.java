package com.projet.equipement.controller;

import com.projet.equipement.entity.Sortie;
import com.projet.equipement.services.SortieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/sortie")
@RestController
public class SortieController {
    
    @Autowired
    private SortieService sortieService;
    
    public ResponseEntity<List<Sortie>> findAllSorties() {
        List<Sortie> sorties = sortieService.findAll();
        return ResponseEntity.ok(sorties) ;
    }
    public ResponseEntity<Optional<Sortie>> findSortie(int id) {
        Optional<Sortie> sortie = sortieService.findById(id);
        return ResponseEntity.ok(sortie);
    }
    public ResponseEntity<Sortie> addSortie(Sortie sortie) {
        sortie = sortieService.save(sortie);
        return ResponseEntity.ok(sortie);
    }
    public ResponseEntity<Sortie> updateSortie(int id, Sortie sortie) {
        sortie = sortieService.save(sortie);
        return ResponseEntity.ok(sortie);
    }
    public ResponseEntity<String> deleteSortie(int id) {
        sortieService.deleteById(id);
        return ResponseEntity.ok("Sortie deleted");
    }
    
    
}
