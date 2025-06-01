package com.projet.equipement.controller;

import com.projet.equipement.entity.PaiementRequest;
import com.projet.equipement.services.MtnMomoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/momo")
public class PaiementMomoController {

    private final MtnMomoService momoService;

    public PaiementMomoController(MtnMomoService momoService) {
        this.momoService = momoService;
    }

    @PostMapping("/payer")
    public ResponseEntity<?> lancerPaiement(@RequestBody PaiementRequest request) {
        boolean result = momoService.initierPaiement(request);
        return result
                ? ResponseEntity.ok().body("Paiement initié")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur MTN");
    }

    @GetMapping("/statut/{referenceId}")
    public ResponseEntity<Map<String, String>> verifierStatut(@PathVariable String referenceId) {
        String statut = momoService.getStatut(referenceId);
        return ResponseEntity.ok(Map.of("status", statut));
    }
}
