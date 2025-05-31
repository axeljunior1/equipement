package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.services.LigneAchatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ligneAchats")
@RestController
public class LigneAchatController {

    private final LigneAchatService ligneAchatService;

    public LigneAchatController(LigneAchatService ligneAchatService) {
        this.ligneAchatService = ligneAchatService;
    }


    @GetMapping("")
    public ResponseEntity<Page<LigneAchatGetDto>> findAllLigneAchatsDto(Pageable pageable) {
        Page<LigneAchatGetDto> ligneAchats = ligneAchatService.findAll(pageable);
        return ResponseEntity.ok(ligneAchats);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LigneAchatGetDto> findLigneAchat(@PathVariable Long id) {
        LigneAchatGetDto ligneAchat = ligneAchatService.findById(id);
        return ResponseEntity.ok(ligneAchat);
    }
    @PostMapping("")
    public ResponseEntity<LigneAchatGetDto> addLigneAchat(@Valid @RequestBody LigneAchatPostDto ligneAchatPostDto) {
        LigneAchatGetDto ligneAchat = ligneAchatService.save(ligneAchatPostDto);
        return ResponseEntity.ok(ligneAchat);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneAchatGetDto> updateLigneAchat(@PathVariable Long id , @Valid  @RequestBody LigneAchatUpdateDto ligneAchatUpdateDto) {

        LigneAchatGetDto ligneAchat = ligneAchatService.updateLigneAchat(ligneAchatUpdateDto, id);
        return ResponseEntity.ok(ligneAchat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneAchat(@PathVariable Long id ) {
        ligneAchatService.deleteLinesByIdSoft(id);
        return ResponseEntity.ok("LigneAchat deleted");
    }

}
