package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.validerPanier.ValiderPanierDTO;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.PaiementRequest;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.services.TransactionVenteAndLinesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ventes")
@RestController
public class VenteController {

    private final TransactionVenteAndLinesService transactionVenteAndLinesService;

    public VenteController(TransactionVenteAndLinesService transactionVenteAndLinesService
    ) {
        this.transactionVenteAndLinesService = transactionVenteAndLinesService;
    }


    @GetMapping("")
    public ResponseEntity<Page<VenteGetDto>> findAllVentes(Pageable pageable) {
        Page<VenteGetDto> ventes = transactionVenteAndLinesService.findAllVentes(pageable);
        return ResponseEntity.ok(ventes);
    }

    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentesByVenteId(@PathVariable Long id, Pageable pageable) {
        Page<LigneVenteGetDto> lineByVenteId = transactionVenteAndLinesService.findByVenteId(id, pageable);
        return ResponseEntity.ok(lineByVenteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenteGetDto> findVente(@PathVariable Long id) {
        VenteGetDto vente = transactionVenteAndLinesService.findByIdVente(id);
        return ResponseEntity.ok(vente);
    }

    @PostMapping("")
    public ResponseEntity<Vente> addVente(@RequestBody VentePostDto ventePostDto) {
        Vente vente = transactionVenteAndLinesService.saveVente(ventePostDto);
        return ResponseEntity.ok(vente);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @Valid @RequestBody VenteUpdateDto venteUpdateDto) {

        Vente vente = transactionVenteAndLinesService.updateVente(venteUpdateDto, id);
        return ResponseEntity.ok(vente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVente(@PathVariable Long id) {

        transactionVenteAndLinesService.softDeleteVente(id);
        return ResponseEntity.ok("Vente deleted");
    }


    @PostMapping("/valide-panier")
    @Transactional // Active la gestion transactionnelle
    public ResponseEntity<VenteGetDto> createVenteNLignes(@Valid @RequestBody ValiderPanierDTO validerPanierDTO
    ) {
        VenteGetDto venteGetDto = transactionVenteAndLinesService.validerVenteDansPanier(validerPanierDTO);

        return ResponseEntity.ok(venteGetDto);
    }

    //    @GetMapping("/{venteId}/payer")
//    public ResponseEntity<String> payerVente(@PathVariable Long venteId, @PathVariable BigDecimal montant) {
//         transactionVenteAndLinesService.payer(venteId, BigDecimal.ZERO);
//         return ResponseEntity.ok("payed");
//    }
//
//    @GetMapping("/{venteId}/rembourser")
//    public ResponseEntity<String> rembourser(@PathVariable Long venteId) {
//         transactionVenteAndLinesService.rembourser(venteId);
//         return ResponseEntity.ok("refund");
//    }
//
//    @GetMapping("/{venteId}/annuler")
//    public ResponseEntity<String> annuler(@PathVariable Long venteId) {
//         transactionVenteAndLinesService.annuler(venteId);
//         return ResponseEntity.ok("cancel");
//    }
//
    @PostMapping("/payer/{id}")
    public ResponseEntity<String> payerVenteSansState(@PathVariable Long id, @RequestBody PaiementRequest paiementRequest) {

        transactionVenteAndLinesService.payer(id, paiementRequest.getMontantPaiement());

        return ResponseEntity.ok(" Vente payed !!");
    }

    @GetMapping("/credit/{id}")
    public ResponseEntity<String> marquerCommeCredit(@PathVariable Long id) {

        transactionVenteAndLinesService.marquerCommeCredit(id);

        return ResponseEntity.ok(" Credit validé !!");
    }


    @GetMapping("/{id}/fermer")
    public ResponseEntity<String> fermerVente(@PathVariable Long id) {

        transactionVenteAndLinesService.fermerVente(id);

        return ResponseEntity.ok(" Vente fermé !!");
    }


}
