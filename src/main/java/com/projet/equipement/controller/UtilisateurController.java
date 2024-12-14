package com.projet.equipement.controller;

import com.projet.equipement.dto.UtilisateurDto;
import com.projet.equipement.entity.Role;
import com.projet.equipement.entity.Utilisateur;
import com.projet.equipement.services.UtilisateurService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/utilisateur")
@RestController
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @GetMapping("")
    public ResponseEntity<List<Utilisateur>> findAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.findAll();
        return ResponseEntity.ok( utilisateurs) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< Utilisateur> findUtilisateur(@PathVariable Long id) {
         Utilisateur utilisateur = utilisateurService.findById(id);
        return ResponseEntity.ok(utilisateur);
    }
    @PostMapping("")
    public ResponseEntity<Utilisateur> addUtilisateur(@RequestBody Utilisateur utilisateur) {
        utilisateur = utilisateurService.save(utilisateur);
        return ResponseEntity.ok(utilisateur);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity updateUtilisateur(@PathVariable Long id , @Valid  @RequestBody UtilisateurDto utilisateurDto) {

        // Objets pour construire Utilisateur et ses Rôles
        Utilisateur utilisateur = utilisateurService.updateUtilisateur(utilisateurDto, id);
        return ResponseEntity.ok(utilisateur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUtilisateur(@PathVariable Long id ) {
        utilisateurService.deleteById(id);
        return ResponseEntity.ok("Utilisateur deleted");
    }



}
