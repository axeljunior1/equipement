package com.projet.equipement.controller;

import com.projet.equipement.dto.categorie.CategoriePostDto;
import com.projet.equipement.dto.categorie.CategorieUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.services.CategorieService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/categories")
@RestController
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("")
    public ResponseEntity<Page<Categorie>> findAllCategories(Pageable pageable) {
        Page <Categorie> categories = categorieService.findAll(pageable);
        return ResponseEntity.ok(categories);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity< Categorie> findCategorie(@PathVariable Long id) {
         Categorie categorie = categorieService.findById(id);
        return ResponseEntity.ok(categorie);
    }

    @PostMapping()
    public ResponseEntity<Categorie> save(@RequestBody CategoriePostDto categoriePostDto) {
         Categorie categorie = categorieService.save(categoriePostDto);
        return ResponseEntity.ok(categorie);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Categorie> update(@RequestBody CategorieUpdateDto categorieUpdateDto, @PathVariable Long id) {
         Categorie categorie = categorieService.update(categorieUpdateDto, id);
        return ResponseEntity.ok(categorie);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategorie(@PathVariable Long id ) {
        categorieService.deleteById(id);
        return ResponseEntity.ok("Categorie deleted");
    }



}
