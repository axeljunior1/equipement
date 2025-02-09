package com.projet.equipement.controller;

import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.specifications.ProduitSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produits")
public class ProduitController {


    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitGetDto> findProduitById(@PathVariable Long id) {
        ProduitGetDto produitGetDto = produitService.findProduitDtoById(id);
        return ResponseEntity.ok(produitGetDto);
    }

    @GetMapping("/code-barre/{ean13}")
    public ResponseEntity<ProduitGetDto> findProduitByEan13(@PathVariable String ean13) {
        Produit produit = produitService.findByEan13(ean13);
        return ResponseEntity.ok(new ProduitGetDto(produit));
    }


    @GetMapping("")
    public ResponseEntity<Page<ProduitGetDto>> findAllProduit( Pageable pageable) {

        Page<ProduitGetDto> produitGetDtos = produitService.findByActif(true, pageable);
        return ResponseEntity.ok(produitGetDtos);
    }




    @GetMapping("/recherche")
    public ResponseEntity<List<Produit>> rechercherProduits(@RequestParam String motCle) {
        List<Produit> produits = produitService.rechercherProduits(motCle);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/recherche-dynamique")
    public List<Produit> filterProduits(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false) Integer stockInitialMin,
            @RequestParam(required = false) Integer stockInitialMax,
            @RequestParam(required = false) Double prixUnitaireMin,
            @RequestParam(required = false) Double prixUnitaireMax) {

        Specification<Produit> spec = Specification.where(ProduitSpecification.hasDescription(description))
                .and(ProduitSpecification.hasNom(nom))
                .and(ProduitSpecification.isActif(actif))
                .and(ProduitSpecification.hasStockBetween(stockInitialMin, stockInitialMax))
                .and(ProduitSpecification.hasPrixBetween(prixUnitaireMin, prixUnitaireMax));

        return produitService.findBySpec(spec);
    }

    @PostMapping
    public ResponseEntity<Produit> addProduit( @RequestBody  ProduitPostDto produitPostDto) {
        Produit produit = produitService.save(produitPostDto);
        return ResponseEntity.ok(produit);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody ProduitUpdateDto produitUpdateDto) {
        Produit produit = produitService.updateProduit(produitUpdateDto, id);
        System.out.println(produit);
        return ResponseEntity.ok(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        produitService.deleteByIdSoft(id);
        return ResponseEntity.ok("Produit deleted");
    }

}

