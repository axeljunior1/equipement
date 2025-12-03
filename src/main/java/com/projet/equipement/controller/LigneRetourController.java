package com.projet.equipement.controller;


import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourPostDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourUpdateDto;
import com.projet.equipement.exceptions.ApiError;
import com.projet.equipement.services.LigneRetourService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ligne-retours")
public class LigneRetourController {

    private final LigneRetourService ligneRetourService;

    public LigneRetourController(LigneRetourService ligneRetourService) {
        this.ligneRetourService = ligneRetourService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LigneRetourGetDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ligneRetourService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<LigneRetourGetDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ligneRetourService.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<LigneRetourGetDto> create(@RequestBody LigneRetourPostDto postDto) {
        return ResponseEntity.ok(ligneRetourService.save(postDto));
    }

    @PostMapping("/all")
    public ResponseEntity<String> createAll(@RequestBody List<LigneRetourPostDto> dtoList) {
        ligneRetourService.saveAll(dtoList);
        return ResponseEntity.ok().body("All Saved");
    }

    @PutMapping("/{id}")
    public ResponseEntity<LigneRetourGetDto> update(@PathVariable Long id, @RequestBody LigneRetourUpdateDto updateDto) {
        return ResponseEntity.ok(ligneRetourService.update(updateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = ligneRetourService.deleteById(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted");
        } else {
        ApiError errorResponse = new ApiError(
                HttpStatus.FORBIDDEN.value(),
                "Suppression non autorisée",
                "La ligne de retour ne peut pas être supprimée car son état ne le permet pas.",
                "LigneRetour id=" + id
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
    }
}
