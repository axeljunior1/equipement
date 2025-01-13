package com.projet.equipement.controller;

import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.services.VenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/vente")
@RestController
public class VenteController {

    @Autowired
    private VenteService venteService;

    @GetMapping("")
    public ResponseEntity<List<VenteGetDto>> findAllVentes() {
        List<Vente> ventes = venteService.findAll();
        return ResponseEntity.ok( ventes.stream().map(VenteGetDto::new).collect(Collectors.toList())) ;
    }
    
    @GetMapping("/{id}")
    public ResponseEntity< Vente> findVente(@PathVariable Long id) {
         Vente vente = venteService.findById(id);
        return ResponseEntity.ok(vente);
    }
    
    @PostMapping("")
    public ResponseEntity<Vente> addVente(@RequestBody VentePostDto ventePostDto) {
       Vente vente = venteService.save(ventePostDto);
        return ResponseEntity.ok(vente);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id , @Valid  @RequestBody VenteUpdateDto venteUpdateDto) {

        Vente vente = venteService.updateVente(venteUpdateDto, id);
        return ResponseEntity.ok(vente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVente(@PathVariable Long id ) {
        venteService.deleteById(id);
        return ResponseEntity.ok("Vente deleted");
    }



}
