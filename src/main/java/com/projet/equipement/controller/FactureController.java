package com.projet.equipement.controller;


import com.projet.equipement.dto.facture.FactureGetDTO;
import com.projet.equipement.dto.facture.FacturePostDTO;
import com.projet.equipement.dto.facture.FactureUpdateDTO;
import com.projet.equipement.services.FactureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/factures")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping
    public ResponseEntity<FactureGetDTO> createFacture(@RequestBody FacturePostDTO facturePostDTO) {
        FactureGetDTO factureGetDTO = factureService.createFacture(facturePostDTO);
        return new ResponseEntity<>(factureGetDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{factureId}")
    public ResponseEntity<FactureGetDTO> updateFacture(
            @PathVariable Long factureId,
            @RequestBody FactureUpdateDTO factureUpdateDTO) {
        FactureGetDTO factureGetDTO = factureService.updateFacture(factureId, factureUpdateDTO);
        return ResponseEntity.ok(factureGetDTO);
    }

    @GetMapping("/{factureId}")
    public ResponseEntity<FactureGetDTO> getFactureById(@PathVariable Long factureId) {
        FactureGetDTO factureGetDTO = factureService.getFactureById(factureId);
        return ResponseEntity.ok(factureGetDTO);
    }

    @GetMapping
    public ResponseEntity<Page<FactureGetDTO>> getAllFactures(Pageable pageable) {
        Page<FactureGetDTO> facturePage = factureService.getAllFactures(pageable);
        return ResponseEntity.ok(facturePage);
    }


}
