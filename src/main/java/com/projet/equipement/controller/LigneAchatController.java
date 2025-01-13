package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.services.LigneAchatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/ligneAchat")
@RestController
public class LigneAchatController {

    private final LigneAchatService ligneAchatService;

    public LigneAchatController(LigneAchatService ligneAchatService) {
        this.ligneAchatService = ligneAchatService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<LigneAchat>> findAllLigneAchats() {
        List<LigneAchat> ligneAchats = ligneAchatService.findAll();
        return ResponseEntity.ok( ligneAchats) ;
    }

    @GetMapping("")
    public ResponseEntity<List<LigneAchatGetDto>> findAllLigneAchatsDto() {
        List<LigneAchat> ligneAchats = ligneAchatService.findAll();

        return ResponseEntity.ok( ligneAchats.stream()
                .map(LigneAchatGetDto::new)
                .collect(Collectors.toList())
        ) ;
    }


    @GetMapping("/{id}")
    public ResponseEntity<LigneAchatGetDto> findLigneAchat(@PathVariable Long id) {
         LigneAchat ligneAchat = ligneAchatService.findById(id);

        return ResponseEntity.ok(new LigneAchatGetDto(ligneAchat));
    }
    @PostMapping("")
    public ResponseEntity<LigneAchat> addLigneAchat(@RequestBody LigneAchatPostDto ligneAchatPostDto) {
       LigneAchat ligneAchat = ligneAchatService.save(ligneAchatPostDto);
        return ResponseEntity.ok(ligneAchat);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneAchat> updateLigneAchat(@PathVariable Long id , @Valid  @RequestBody LigneAchatUpdateDto ligneAchatUpdateDto) {

        LigneAchat ligneAchat = ligneAchatService.updateLigneAchat(ligneAchatUpdateDto, id);
        return ResponseEntity.ok(ligneAchat);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneAchat(@PathVariable Long id ) {
        ligneAchatService.deleteById(id);
        return ResponseEntity.ok("LigneAchat deleted");
    }

}
