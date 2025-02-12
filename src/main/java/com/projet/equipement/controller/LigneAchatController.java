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

//    @GetMapping("/all")
//    public ResponseEntity<Page<LigneAchatGetDto>> findAllLigneAchats(Pageable pageable) {
//        Page<LigneAchat> ligneAchats = transactionAchatAndLinesService.findAllLine(pageable);
//        return ResponseEntity.ok( ligneAchats) ;
//    }

    @GetMapping("")
    public ResponseEntity<Page<LigneAchatGetDto>> findAllLigneAchatsDto(Pageable pageable) {
        Page<LigneAchatGetDto> ligneAchats = transactionAchatAndLinesService.findAllLine(pageable);
        return ResponseEntity.ok(ligneAchats);
    }


    @GetMapping("/{id}")
    public ResponseEntity<LigneAchatGetDto> findLigneAchat(@PathVariable Long id) {
        LigneAchatGetDto ligneAchat = transactionAchatAndLinesService.findLigneAchatById(id);

        return ResponseEntity.ok(ligneAchat);
    }
    @PostMapping("")
    public ResponseEntity<LigneAchatGetDto> addLigneAchat(@RequestBody LigneAchatPostDto ligneAchatPostDto) {
        LigneAchatGetDto ligneAchat = transactionAchatAndLinesService.saveLigneAchat(ligneAchatPostDto);
        return ResponseEntity.ok(ligneAchat);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneAchatGetDto> updateLigneAchat(@PathVariable Long id , @Valid  @RequestBody LigneAchatUpdateDto ligneAchatUpdateDto) {

        LigneAchatGetDto ligneAchat = transactionAchatAndLinesService.updateLigneAchat(ligneAchatUpdateDto, id);
        return ResponseEntity.ok(ligneAchat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneAchat(@PathVariable Long id ) {
        transactionAchatAndLinesService.deleteLinesByIdSoft(id);
        return ResponseEntity.ok("LigneAchat deleted");
    }

}
