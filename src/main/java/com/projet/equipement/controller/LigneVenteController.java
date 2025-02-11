package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.services.TransactionVenteAndLinesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ligneVentes")
@RestController
public class LigneVenteController {


    private final TransactionVenteAndLinesService transactionVenteAndLinesService;

    public LigneVenteController(TransactionVenteAndLinesService transactionVenteAndLinesService) {
        this.transactionVenteAndLinesService = transactionVenteAndLinesService;
    }

    @GetMapping("")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentes(Pageable pageable) {
        Page<LigneVente> ligneVentes = transactionVenteAndLinesService.findAllLine(pageable);
        return ResponseEntity.ok(ligneVentes.map(LigneVenteGetDto::new));
    }
    @GetMapping("/{id}")
    public ResponseEntity< LigneVente> findLigneVente(@PathVariable Long id) {
         LigneVente ligneVente = transactionVenteAndLinesService.findByIdLine(id);
        return ResponseEntity.ok(ligneVente);
    }
    @PostMapping("")
    public ResponseEntity<LigneVente> addLigneVente(@RequestBody LigneVentePostDto ligneVentePostDto) {
       LigneVente ligneVente = transactionVenteAndLinesService.saveLigneVente(ligneVentePostDto);
        return ResponseEntity.ok(ligneVente);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneVente> updateLigneVente(@PathVariable Long id , @Valid  @RequestBody LigneVenteUpdateDto ligneVenteUpdateDto) {

        LigneVente ligneVente = transactionVenteAndLinesService.updateLigneVente(ligneVenteUpdateDto, id);
        return ResponseEntity.ok(ligneVente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneVente(@PathVariable Long id ) {
        transactionVenteAndLinesService.deleteLinesByIdSoft(id);
        return ResponseEntity.ok("LigneVente deleted");
    }

}
