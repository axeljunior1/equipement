package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.services.TransactionAchatAndLinesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ligneAchats")
@RestController
public class LigneAchatController {

    private final TransactionAchatAndLinesService transactionAchatAndLinesService;

    public LigneAchatController(TransactionAchatAndLinesService transactionAchatAndLinesService) {
        this.transactionAchatAndLinesService = transactionAchatAndLinesService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<LigneAchat>> findAllLigneAchats(Pageable pageable) {
        Page<LigneAchat> ligneAchats = transactionAchatAndLinesService.findAllLine(pageable);
        return ResponseEntity.ok( ligneAchats) ;
    }

    @GetMapping("")
    public ResponseEntity<Page<LigneAchatGetDto>> findAllLigneAchatsDto(Pageable pageable) {
        Page<LigneAchat> ligneAchats = transactionAchatAndLinesService.findAllLine(pageable);
        return ResponseEntity.ok(ligneAchats.map(LigneAchatGetDto::new));
    }


    @GetMapping("/{id}")
    public ResponseEntity<LigneAchatGetDto> findLigneAchat(@PathVariable Long id) {
         LigneAchat ligneAchat = transactionAchatAndLinesService.findByIdLine(id);

        return ResponseEntity.ok(new LigneAchatGetDto(ligneAchat));
    }
    @PostMapping("")
    public ResponseEntity<LigneAchat> addLigneAchat(@RequestBody LigneAchatPostDto ligneAchatPostDto) {
       LigneAchat ligneAchat = transactionAchatAndLinesService.saveLigneAchat(ligneAchatPostDto);
        return ResponseEntity.ok(ligneAchat);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneAchat> updateLigneAchat(@PathVariable Long id , @Valid  @RequestBody LigneAchatUpdateDto ligneAchatUpdateDto) {

        LigneAchat ligneAchat = transactionAchatAndLinesService.updateLigneAchat(ligneAchatUpdateDto, id);
        return ResponseEntity.ok(ligneAchat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneAchat(@PathVariable Long id ) {
        transactionAchatAndLinesService.deleteByIdSoftLine(id);
        return ResponseEntity.ok("LigneAchat deleted");
    }

}
