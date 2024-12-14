package com.projet.equipement.controller;

import com.projet.equipement.entity.Fournisseur;
import com.projet.equipement.entity.Fournisseur;
import com.projet.equipement.services.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fournisseur")
public class FournisseurController {
    
    @Autowired
    private FournisseurService fournisseurService;

    @GetMapping("/{id}")
    public ResponseEntity< Fournisseur> findFournisseurById(@PathVariable Long id) {
         Fournisseur fournisseur = fournisseurService.findById(id);
        return ResponseEntity.ok(fournisseur);
    }
    @GetMapping("")
    public ResponseEntity<List<Fournisseur>> findAllFournisseur() {
        List<Fournisseur> fournisseurs = fournisseurService.findAll();
        return ResponseEntity.ok(fournisseurs);
    }
    @PostMapping("")
    public ResponseEntity<Fournisseur> addFournisseur(@RequestBody Fournisseur fournisseur) {
        fournisseurService.save(fournisseur);
        return ResponseEntity.ok(fournisseur);
    }

//    @PutMapping("")
//    public ResponseEntity<Fournisseur> updateFournisseur(@RequestBody Fournisseur fournisseur) {
//        Fournisseur updatedFournisseur = fournisseurService.update(fournisseur);
//        return ResponseEntity.ok(updatedFournisseur);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFournisseur(@PathVariable Long id) {
        fournisseurService.deleteById(id);
        return ResponseEntity.ok("Fournisseur deleted");
    }
    
}
