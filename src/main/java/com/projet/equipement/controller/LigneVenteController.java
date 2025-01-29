package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.mapper.LigneVenteMapper;
import com.projet.equipement.mapper.MouvementStockMapper;
import com.projet.equipement.repository.LigneVenteRepository;
import com.projet.equipement.repository.MouvementStockRepository;
import com.projet.equipement.services.LigneVenteService;
import com.projet.equipement.services.ProduitService;
import com.projet.equipement.services.VenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/ligneVentes")
@RestController
public class LigneVenteController {

    private final LigneVenteService ligneVenteService;
    private final LigneVenteMapper ligneVenteMapper;
    private final VenteService venteService;
    private final ProduitService produitService;
    private final LigneVenteRepository ligneVenteRepository;
    private final MouvementStockRepository mouvementStockRepository;
    private final MouvementStockMapper mouvementStockMapper;

    public LigneVenteController(LigneVenteService ligneVenteService, LigneVenteMapper ligneVenteMapper, VenteService venteService, ProduitService produitService, LigneVenteRepository ligneVenteRepository, MouvementStockRepository mouvementStockRepository, MouvementStockMapper mouvementStockMapper) {
        this.ligneVenteService = ligneVenteService;
        this.ligneVenteMapper = ligneVenteMapper;
        this.venteService = venteService;
        this.produitService = produitService;
        this.ligneVenteRepository = ligneVenteRepository;
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockMapper = mouvementStockMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentes(Pageable pageable) {
        Page<LigneVente> ligneVentes = ligneVenteService.findAll(pageable);
        return ResponseEntity.ok(ligneVentes.map(LigneVenteGetDto::new));
    }
    @GetMapping("/{id}")
    public ResponseEntity< LigneVente> findLigneVente(@PathVariable Long id) {
         LigneVente ligneVente = ligneVenteService.findById(id);
        return ResponseEntity.ok(ligneVente);
    }
    @PostMapping("")
    public ResponseEntity<LigneVente> addLigneVente(@RequestBody LigneVentePostDto ligneVentePostDto) {
       LigneVente ligneVente = ligneVenteService.save(ligneVentePostDto);
        return ResponseEntity.ok(ligneVente);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<LigneVente> updateLigneVente(@PathVariable Long id , @Valid  @RequestBody LigneVenteUpdateDto ligneVenteUpdateDto) {

        LigneVente ligneVente = ligneVenteService.updateLigneVente(ligneVenteUpdateDto, id);
        return ResponseEntity.ok(ligneVente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLigneVente(@PathVariable Long id ) {
        ligneVenteService.deleteById(id);
        return ResponseEntity.ok("LigneVente deleted");
    }

}
