package com.projet.equipement.controller;

import com.projet.equipement.dto.client.ClientGetDto;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.validerPanier.ValiderPanierDTO;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
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

    public VenteController(TransactionVenteAndLinesService transactionVenteAndLinesService) {
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

        /*
         * jai le client c'est ok
         * je cree la vente
         * je prens les lignes dans le panier a partir de l'id de panier quon me donne
         * je cree les lignes
         * je retourne la vente
         *
         * */


        VenteGetDto venteGetDto = transactionVenteAndLinesService.validerVenteDansPanier(validerPanierDTO);


        return ResponseEntity.ok(venteGetDto);
    }


}
