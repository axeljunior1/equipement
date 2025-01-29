package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.Caisse;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.services.VenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/ventes")
@RestController
public class VenteController {

    private final VenteService venteService;

    public VenteController(VenteService venteService) {
        this.venteService = venteService;
    }

    @GetMapping("")
    public ResponseEntity<Page<VenteGetDto>> findAllVentes(Pageable pageable) {
        Page<Vente> ventes = venteService.findAll(pageable);
        return ResponseEntity.ok( ventes.map(VenteGetDto::new));
    }
    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentesByVenteId(@PathVariable Long id, Pageable pageable) {
        Page<LigneVente> lineByVenteId = venteService.findLineByVenteId(id, pageable);
        return ResponseEntity.ok( lineByVenteId.map(LigneVenteGetDto::new));
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

    @PostMapping("/createVenteNLignes")
    public ResponseEntity<Caisse> createVenteNLignes(@RequestBody Caisse caisse){



        return ResponseEntity.ok(null);
    }



}
