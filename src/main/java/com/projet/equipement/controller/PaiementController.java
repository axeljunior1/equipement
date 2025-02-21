package com.projet.equipement.controller;


import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.dto.paiement.PaiementPostDTO;
import com.projet.equipement.dto.paiement.PaiementUpdateDTO;
import com.projet.equipement.services.PaiementService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paiements")
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    // Créer un paiement
    @PostMapping
    public ResponseEntity<PaiementGetDTO> createPaiement(@RequestBody PaiementPostDTO paiementPostDTO) {
        PaiementGetDTO paiementGetDTO = paiementService.createPaiement(paiementPostDTO);
        return new ResponseEntity<>(paiementGetDTO, HttpStatus.CREATED);
    }

    // Mettre à jour un paiement
    @PutMapping("/{paiementId}")
    public ResponseEntity<PaiementGetDTO> updatePaiement(
            @PathVariable Long paiementId,
            @RequestBody PaiementUpdateDTO paiementUpdateDTO) {
        PaiementGetDTO paiementGetDTO = paiementService.updatePaiement(paiementId, paiementUpdateDTO);
        return ResponseEntity.ok(paiementGetDTO);
    }

    // Obtenir un paiement par son ID
    @GetMapping("/{paiementId}")
    public ResponseEntity<PaiementGetDTO> getPaiementById(@PathVariable Long paiementId) {
        PaiementGetDTO paiementGetDTO = paiementService.getPaiementById(paiementId);
        return ResponseEntity.ok(paiementGetDTO);
    }

    // Obtenir une liste paginée de tous les paiements
    @GetMapping
    public ResponseEntity<Page<PaiementGetDTO>> getAllPaiements(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PaiementGetDTO> paiementPage = paiementService.getAllPaiements(page, size);
        return ResponseEntity.ok(paiementPage);
    }

    // Obtenir une liste paginée de paiements par mode de paiement
    @GetMapping("/modePaiement")
    public ResponseEntity<Page<PaiementGetDTO>> getPaiementsByModePaiement(
            @RequestParam String modePaiement,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PaiementGetDTO> paiementPage = paiementService.getPaiementsByModePaiement(modePaiement, page, size);
        return ResponseEntity.ok(paiementPage);
    }
}
