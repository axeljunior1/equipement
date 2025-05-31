package com.projet.equipement.controller;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.services.AchatService;
import com.projet.equipement.services.LigneAchatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/achats")
@RestController
public class AchatController {
    
    private final AchatService achatService;
    private final LigneAchatService ligneAchatService;

    public AchatController(AchatService achatService,
                           LigneAchatService ligneAchatService) {

        this.achatService = achatService;
        this.ligneAchatService = ligneAchatService;
    }


    @GetMapping("")
    public ResponseEntity<Page<AchatGetDto>> findAllAchats(Pageable pageable) {
        Page<AchatGetDto> achats = achatService.findAll(pageable);
        return ResponseEntity.ok(achats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatGetDto> findAchat(@PathVariable Long id) {
        AchatGetDto achat = achatService.findById(id);
        return ResponseEntity.ok(achat);
    }

    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneAchatGetDto>> findAchatLineById(@PathVariable Long id, Pageable pageable) {
        Page<LigneAchatGetDto> ligneAchats = ligneAchatService.findByAchatId(id, pageable);
        return ResponseEntity.ok(ligneAchats);
    }

    @PostMapping()
    public ResponseEntity<AchatGetDto> save(@RequestBody AchatPostDto achatPostDto) {
        AchatGetDto achat = achatService.save(achatPostDto);
        return ResponseEntity.ok(achat);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AchatGetDto> updateAchat(@PathVariable Long id, @Valid @RequestBody AchatUpdateDto achatUpdateDto) {

        AchatGetDto achat = achatService.update(achatUpdateDto, id);
        return ResponseEntity.ok(achat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAchat(@PathVariable Long id) {
        achatService.softDeleteAchat(id);
        return ResponseEntity.ok("Achat deleted");
    }


}
