package com.projet.equipement.controller;

import com.projet.equipement.entity.Entree;
import com.projet.equipement.services.EntreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("/entree")
@RestController
public class EntreeController {

    @Autowired
    private EntreeService entreeService;

    public ResponseEntity<List<Entree>> findAllEntrees() {
        List<Entree> entrees = entreeService.findAll();
        return ResponseEntity.ok( entrees) ;
    }
    public ResponseEntity<Optional<Entree>> findEntree(int id) {
        Optional<Entree> entree = entreeService.findById(id);
        return ResponseEntity.ok(entree);
    }
    public ResponseEntity<Entree> addEntree(Entree entree) {
        entree = entreeService.save(entree);
        return ResponseEntity.ok(entree);
    }
    public ResponseEntity<Entree> updateEntree(int id, Entree entree) {
        entree = entreeService.save(entree);
        return ResponseEntity.ok(entree);
    }
    public ResponseEntity<String> deleteEntree(int id) {
        entreeService.deleteById(id);
        return ResponseEntity.ok("Entree deleted");
    }


}
