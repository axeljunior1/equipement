package com.projet.equipement.controller;

import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.ProduitRepository;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.specifications.ProduitSpecification;
import jakarta.validation.Valid;
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
    private final ProduitRepository produitRepository;

    public ProduitController(ProduitService produitService, ProduitRepository produitRepository) {
        this.produitService = produitService;
        this.produitRepository = produitRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitGetDto> findProduitById(@PathVariable Long id) {
        ProduitGetDto produitGetDto = produitService.findProduitDtoById(id);
        return ResponseEntity.ok(produitGetDto);
    }

    @GetMapping("/code-barre/{ean13}")
    public ResponseEntity<ProduitGetDto> findProduitByEan13(@PathVariable String ean13) {
        ProduitGetDto produit = produitService.findByEan13(ean13);
        return ResponseEntity.ok(produit);
    }


    @GetMapping("")
    public ResponseEntity<Page<ProduitGetDto>> findAllProduit( Pageable pageable) {

        Page<ProduitGetDto> produitGetDtos = produitService.findByActif(true, pageable);
        return ResponseEntity.ok(produitGetDtos);
    }




    @GetMapping("/recherche")
    public ResponseEntity<List<ProduitGetDto>> rechercherProduits(@RequestParam String motCle) {
        List<ProduitGetDto> produits = produitService.rechercherProduits(motCle);
        return ResponseEntity.ok(produits);
    }

    @GetMapping("/recherche-dynamique")
    public List<ProduitGetDto> filterProduits(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Boolean actif,
            @RequestParam(required = false) Integer stockInitialMin,
            @RequestParam(required = false) Integer stockInitialMax,
            @RequestParam(required = false) Double prixVenteMin,
            @RequestParam(required = false) Double prixVenteMax) {

        Specification<Produit> spec = Specification.where(ProduitSpecification.hasDescription(description))
                .and(ProduitSpecification.hasNom(nom))
                .and(ProduitSpecification.isActif(actif))
                .and(ProduitSpecification.hasStockBetween(stockInitialMin, stockInitialMax))
                .and(ProduitSpecification.hasPrixBetween(prixVenteMin, prixVenteMax));

        return produitService.findBySpec(spec);
    }

    @PostMapping
    public ResponseEntity<ProduitGetDto> addProduit(@Valid @RequestBody  ProduitPostDto produitPostDto) {
        ProduitGetDto produit = produitService.save(produitPostDto);
        Produit p = produitRepository.findById(produit.getId()).orElseThrow(()->new EntityNotFoundException("Produit", produit.getId()));
        return ResponseEntity.ok(produit);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProduitGetDto> updateProduit(@PathVariable Long id, @RequestBody ProduitUpdateDto produitUpdateDto) {
        ProduitGetDto produit = produitService.updateProduit(produitUpdateDto, id);
        return ResponseEntity.ok(produit);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        produitService.deleteByIdSoft(id);
        return ResponseEntity.ok("Produit deleted");
    }

}

