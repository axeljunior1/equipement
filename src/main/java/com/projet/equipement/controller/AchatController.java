package com.projet.equipement.controller;

import com.projet.equipement.dto.achat.AchatGetDto;
import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.services.TransactionAchatAndLinesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/achats")
@RestController
public class AchatController {

    private final TransactionAchatAndLinesService transactionAchatAndLinesService;

    public AchatController(TransactionAchatAndLinesService transactionAchatAndLinesService) {

        this.transactionAchatAndLinesService = transactionAchatAndLinesService;
    }


    @GetMapping("")
    public ResponseEntity<Page<AchatGetDto>> findAllAchats(Pageable pageable) {
        Page<AchatGetDto> achats = transactionAchatAndLinesService.findAllAchat(pageable);
        return ResponseEntity.ok(achats);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AchatGetDto> findAchat(@PathVariable Long id) {
        AchatGetDto achat = transactionAchatAndLinesService.findAchatById(id);
        return ResponseEntity.ok(achat);
    }

    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneAchatGetDto>> findAchatLineById(@PathVariable Long id, Pageable pageable) {
        Page<LigneAchatGetDto> ligneAchats = transactionAchatAndLinesService.findByAchatId(id, pageable);
        return ResponseEntity.ok(ligneAchats);
    }

    @PostMapping()
    public ResponseEntity<AchatGetDto> save(@RequestBody AchatPostDto achatPostDto) {
        AchatGetDto achat = transactionAchatAndLinesService.saveAchat(achatPostDto);
        return ResponseEntity.ok(achat);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AchatGetDto> updateAchat(@PathVariable Long id, @Valid @RequestBody AchatUpdateDto achatUpdateDto) {

        AchatGetDto achat = transactionAchatAndLinesService.updateAchat(achatUpdateDto, id);
        return ResponseEntity.ok(achat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAchat(@PathVariable Long id) {
        transactionAchatAndLinesService.softDeleteAchat(id);
        return ResponseEntity.ok("Achat deleted");
    }


}
