package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.services.LigneVenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ligneVentes")
@RestController
public class LigneVenteController {


    private final LigneVenteService ligneVenteService;

    public LigneVenteController(LigneVenteService ligneVenteService) {
        this.ligneVenteService = ligneVenteService;
    }

    @GetMapping("")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentes(Pageable pageable) {
        Page<LigneVenteGetDto> ligneVentes = ligneVenteService.findAll(pageable);
        return ResponseEntity.ok(ligneVentes);
    }
    @GetMapping("/{id}")
    public ResponseEntity< LigneVenteGetDto> findLigneVente(@PathVariable Long id) {
         LigneVenteGetDto ligneVente = ligneVenteService.findById(id);
        return ResponseEntity.ok(ligneVente);
    }
    @PostMapping("")
    public ResponseEntity<LigneVente> addLigneVente(@RequestBody LigneVentePostDto ligneVentePostDto) {
       LigneVente ligneVente = ligneVenteService.save(ligneVentePostDto);
        return ResponseEntity.ok(ligneVente);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneVente> updateLigneVente(@PathVariable Long id , @Valid  @RequestBody LigneVenteUpdateDto ligneVenteUpdateDto) {

        LigneVente ligneVente = ligneVenteService.updateLigneVente(ligneVenteUpdateDto, id);
        return ResponseEntity.ok(ligneVente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneVente(@PathVariable Long id ) {
        ligneVenteService.deleteLinesByIdSoft(id);
        return ResponseEntity.ok("LigneVente deleted");
    }

}
