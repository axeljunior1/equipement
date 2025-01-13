package com.projet.equipement.controller;

import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.services.AchatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/achat")
@RestController
public class AchatController {

    @Autowired
    private AchatService achatService;

    @GetMapping("")
    public ResponseEntity<List<Achat>> findAllAchats() {
        List<Achat> achats = achatService.findAll();
        return ResponseEntity.ok( achats) ;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity< Achat> findAchat(@PathVariable Long id) {
         Achat achat = achatService.findById(id);
        return ResponseEntity.ok(achat);
    }
    
    @PostMapping("")
    public ResponseEntity<Achat> addAchat(@RequestBody AchatPostDto achatPostDto) {
       Achat achat = achatService.save(achatPostDto);
        return ResponseEntity.ok(achat);
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
