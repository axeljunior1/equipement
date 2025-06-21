package com.projet.equipement.controller;


import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.retour.*;
import com.projet.equipement.services.LigneRetourService;
import com.projet.equipement.services.RetourService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/retours")
public class RetourController {

    private final RetourService retourService;
    private final LigneRetourService ligneRetourService;

    public RetourController(RetourService retourService, LigneRetourService ligneRetourService) {
        this.retourService = retourService;
        this.ligneRetourService = ligneRetourService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RetourGetDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(retourService.findById(id));
    }

    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneRetourGetDto>> findAllLigneRetoursByVenteId(@PathVariable Long id, Pageable pageable) {
        Page<LigneRetourGetDto> lineByVenteId = ligneRetourService.findByRetourId(id, pageable);
        return ResponseEntity.ok(lineByVenteId);
    }


    @GetMapping
    public ResponseEntity<Page<RetourGetDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(retourService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<RetourGetDto> create(@RequestBody RetourPostDto postDto) {
        return ResponseEntity.ok(retourService.save(postDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RetourGetDto> update(@PathVariable Long id, @RequestBody RetourUpdateDto updateDto) {
        return ResponseEntity.ok(retourService.update(updateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        retourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}