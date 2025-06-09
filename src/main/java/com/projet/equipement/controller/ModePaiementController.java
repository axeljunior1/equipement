package com.projet.equipement.controller;

import com.projet.equipement.dto.modePaiement.ModePaiementGetDto;
import com.projet.equipement.dto.modePaiement.ModePaiementPostDto;
import com.projet.equipement.dto.modePaiement.ModePaiementUpdateDto;
import com.projet.equipement.entity.ModePaiement;
import com.projet.equipement.services.ModepaiementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modes-paiement")
@RequiredArgsConstructor
public class ModePaiementController {

    private final ModepaiementService modepaiementService;

    @GetMapping("/{id}")
    public ResponseEntity<ModePaiement> getById(@PathVariable Long id) {
        return ResponseEntity.ok(modepaiementService.getModePaiement(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<ModePaiement> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(modepaiementService.getModePaiementByCode(code));
    }

    @PostMapping
    public ResponseEntity<ModePaiementGetDto> create(@RequestBody @Valid ModePaiementPostDto modePaiement) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modepaiementService.save(modePaiement));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ModePaiement> update(@PathVariable Long id, @RequestBody @Valid ModePaiementUpdateDto updatedData) {

        return ResponseEntity.ok(modepaiementService.update(id, updatedData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ModePaiement modePaiement = modepaiementService.getModePaiement(id);
        modepaiementService.delete(modePaiement);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ModePaiementGetDto>> list(Pageable pageable) {
        return ResponseEntity.ok(modepaiementService.findAll(pageable));
    }
}
