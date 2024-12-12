package com.projet.equipement.controller;

import com.projet.equipement.entity.Entree;
import com.projet.equipement.services.EntreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/entree")
@RestController
public class EntreeController {

    @Autowired
    private EntreeService entreeService;

    @GetMapping("")
    public ResponseEntity<List<Entree>> findAllEntrees() {
        List<Entree> entrees = entreeService.findAll();
        return ResponseEntity.ok( entrees) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Entree>> findEntree(@PathVariable int id) {
        Optional<Entree> entree = entreeService.findById(id);
        return ResponseEntity.ok(entree);
    }
    @PostMapping("")
    public ResponseEntity<Entree> addEntree(@RequestBody Entree entree) {
        entree = entreeService.save(entree);
        return ResponseEntity.ok(entree);
    }
    @PutMapping("")
    public ResponseEntity<Entree> updateEntree(@RequestBody Entree entree) {
        entree = entreeService.save(entree);
        return ResponseEntity.ok(entree);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEntree(@PathVariable int id ) {
        entreeService.deleteById(id);
        return ResponseEntity.ok("Entree deleted");
    }


}
