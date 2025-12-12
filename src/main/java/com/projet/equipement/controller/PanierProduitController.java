package com.projet.equipement.controller;

import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.panierProduit.PanierProduitPostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitUpdateDto;
import com.projet.equipement.services.PanierProduitService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/panier-produit")
@RestController
public class PanierProduitController {


    private final PanierProduitService panierProduitService;

    public PanierProduitController(PanierProduitService panierProduitService) {
        this.panierProduitService = panierProduitService;
    }

    @GetMapping("")
    public ResponseEntity<Page<PanierProduitGetDto>> findAllPanierProduits(Pageable pageable) {
        Page<PanierProduitGetDto> panierProduits = panierProduitService.findAll(pageable);
        return ResponseEntity.ok(panierProduits);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PanierProduitGetDto> findPanierProduit(@PathVariable Long id) {
        PanierProduitGetDto panierProduit = panierProduitService.findById(id);
        return ResponseEntity.ok(panierProduit);
    }

    @GetMapping("/panier/{id}")
    public ResponseEntity<List<PanierProduitGetDto>> findPanierProduitLineById(@PathVariable Long id) {
        List<PanierProduitGetDto> lignePanierProduits = panierProduitService.findAllByPanierId(id);
        return ResponseEntity.ok(lignePanierProduits);
    }

    @GetMapping("/panier/{idPanier}/produit/{idProduit}")
    public ResponseEntity<PanierProduitGetDto> findPanierProduitLineById(@PathVariable Long idPanier ,@PathVariable Long idProduit) {
        PanierProduitGetDto lignePanierProduits = panierProduitService.findByIdPanierAndIdProduit(idPanier, idProduit);
        return ResponseEntity.ok(lignePanierProduits);
    }

    @PostMapping()
    public ResponseEntity<PanierProduitGetDto> save(@RequestBody PanierProduitPostDto panierProduitPostDto) {
        PanierProduitGetDto panierProduit = panierProduitService.save(panierProduitPostDto);
        return ResponseEntity.ok(panierProduit);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<PanierProduitGetDto> updatePanierProduit(@PathVariable Long id, @Valid @RequestBody PanierProduitUpdateDto panierProduitUpdateDto) {
        PanierProduitGetDto panierProduit = panierProduitService.update(panierProduitUpdateDto, id);
        return ResponseEntity.ok(panierProduit);
    }

    @GetMapping("/present/panier/{idPanier}/produit/{idProduit}")
    public ResponseEntity<Boolean> isPresent(@PathVariable Long idPanier ,@PathVariable Long idProduit) {
        return ResponseEntity.ok(panierProduitService.isPresent(idPanier, idProduit));
    }

    @GetMapping("/quantite/panier/{idPanier}/produit/{idProduit}")
    public ResponseEntity<Integer> getQte(@PathVariable Long idPanier ,@PathVariable Long idProduit) {
        return ResponseEntity.ok(panierProduitService.getQte(idPanier, idProduit));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePanierProduit(@PathVariable Long id) {
        panierProduitService.deleteById(id);
        return ResponseEntity.ok("PanierProduit deleted");
    }


}
