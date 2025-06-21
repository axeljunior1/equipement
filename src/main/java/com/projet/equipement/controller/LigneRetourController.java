package com.projet.equipement.controller;


import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourPostDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourUpdateDto;
import com.projet.equipement.services.LigneRetourService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<LigneRetourGetDto> update(@PathVariable Long id, @RequestBody LigneRetourUpdateDto updateDto) {
        return ResponseEntity.ok(ligneRetourService.update(updateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        ligneRetourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
