package com.projet.equipement.controller;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.services.AchatService;
import com.projet.equipement.services.LigneAchatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/achat")
@RestController
public class AchatController {

    private final AchatService achatService;
    private final LigneAchatService ligneAchatService;

    public AchatController(AchatService achatService, LigneAchatService ligneAchatService) {
        this.achatService = achatService;
        this.ligneAchatService = ligneAchatService;
    }

    @GetMapping("")
    public ResponseEntity<Page<AchatGetDto>> findAllAchats(Pageable pageable) {
        Page <Achat> achats = achatService.findAll(pageable);
        return ResponseEntity.ok(achats.map(AchatGetDto::new));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity< Achat> findAchat(@PathVariable Long id) {
         Achat achat = achatService.findById(id);
        return ResponseEntity.ok(achat);
    }

    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneAchat>> findAchatLineById(@PathVariable Long id, Pageable pageable) {
         Page<LigneAchat> ligneAchats = ligneAchatService.findByAchatId(id, pageable);
        return ResponseEntity.ok(ligneAchats);
    }

    
    @PatchMapping("/{id}")
    public ResponseEntity<Achat> updateAchat(@PathVariable Long id , @Valid  @RequestBody AchatUpdateDto achatUpdateDto) {

        Achat achat = achatService.updateAchat(achatUpdateDto, id);
        return ResponseEntity.ok(achat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAchat(@PathVariable Long id ) {
        achatService.deleteById(id);
        return ResponseEntity.ok("Achat deleted");
    }



}
