package com.projet.equipement.controller;

import com.projet.equipement.entity.PaiementRequestMomo;
import com.projet.equipement.entity.StatusMomoRequest;
import com.projet.equipement.services.MtnMomoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> lancerPaiement(@RequestBody PaiementRequestMomo request) {
        boolean result = momoService.initierPaiement(request);
        return result
                ? ResponseEntity.ok().body("Paiement initié")
                : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur MTN");
    }

    @PostMapping("/statut")
    public ResponseEntity<Map<String, String>> verifierStatut(@RequestBody @Valid StatusMomoRequest statusMomoRequest) {
        String statut = momoService.getStatut(statusMomoRequest);
        return ResponseEntity.ok(Map.of("status", statut));
    }
}
