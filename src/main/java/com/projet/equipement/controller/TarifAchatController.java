package com.projet.equipement.controller;

import com.projet.equipement.dto.tarifAchat.TarifAchatGetDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatPostDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatUpdateDto;
import com.projet.equipement.services.TarifAchatService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tarif-achats")
@RestController
public class TarifAchatController {

    private final TarifAchatService tarifAchatService;

    public TarifAchatController(TarifAchatService tarifAchatService) {
        this.tarifAchatService = tarifAchatService;
    }

    @GetMapping("")
    public ResponseEntity<Page<TarifAchatGetDto>> findAllTarifAchats(Pageable pageable) {

        Page<TarifAchatGetDto> tarifAchats = tarifAchatService.findAll(pageable);

        return ResponseEntity.ok( tarifAchats) ;
    }


    @GetMapping("/{id}")
    public ResponseEntity< TarifAchatGetDto> findTarifAchat(@PathVariable Long id) {

         TarifAchatGetDto tarifAchat = tarifAchatService.findById(id);

        return ResponseEntity.ok(tarifAchat);
    }

    @PostMapping("")
    public ResponseEntity<TarifAchatGetDto> addTarifAchat(@RequestBody TarifAchatPostDto tarifAchatPostDto) {
       TarifAchatGetDto tarifAchat = tarifAchatService.save(tarifAchatPostDto);
        return ResponseEntity.ok(tarifAchat);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<TarifAchatGetDto> updateTarifAchat(@PathVariable Long id , @Valid  @RequestBody TarifAchatUpdateDto tarifAchatUpdateDto) {
        // Objets pour construire TarifAchat et ses Rôles
        TarifAchatGetDto tarifAchat = tarifAchatService.updateTarifAchat(tarifAchatUpdateDto, id);
        return ResponseEntity.ok(tarifAchat);
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteTarifAchat(@PathVariable Long id ) {
//        tarifAchatService.deleteById(id);
//        return ResponseEntity.ok("TarifAchat deleted");
//    }
//


}
