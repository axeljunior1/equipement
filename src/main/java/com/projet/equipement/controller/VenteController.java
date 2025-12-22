package com.projet.equipement.controller;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.validerPanier.ValiderPanierDTO;
import com.projet.equipement.dto.vente.VenteFilterDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.PaiementRequest;
import com.projet.equipement.entity.PaiementRequestAvoir;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.mapper.VenteMapper;
import com.projet.equipement.repository.VenteRepository;
import com.projet.equipement.services.LigneVenteService;
import com.projet.equipement.services.VenteService;
import com.projet.equipement.specifications.VenteSpecification;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/ventes")
@RestController
public class VenteController {

    private final VenteService venteService;
    private final LigneVenteService ligneVenteService;
    private final VenteRepository venteRepository;
    private final VenteMapper venteMapper;

    public VenteController(
            VenteService venteService, LigneVenteService ligneVenteService, VenteRepository venteRepository, VenteMapper venteMapper) {
        this.venteService = venteService;
        this.ligneVenteService = ligneVenteService;
        this.venteRepository = venteRepository;
        this.venteMapper = venteMapper;
    }


    @PostMapping("/search")
    public Page<VenteGetDto> search(
            @RequestBody VenteFilterDto filter,
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            ) Pageable pageable
    ) {
        return venteRepository
                .findAll(VenteSpecification.filter(filter), pageable)
                .map(venteMapper::toDto);
    }


    @GetMapping("")
    public ResponseEntity<Page<VenteGetDto>> findAllVentes(Pageable pageable) {
        Page<VenteGetDto> ventes = venteService.findAll(pageable);
        return ResponseEntity.ok(ventes);
    }

    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentesByVenteId(@PathVariable Long id, Pageable pageable) {
        Page<LigneVenteGetDto> lineByVenteId = ligneVenteService.findByVenteId(id, pageable);
        return ResponseEntity.ok(lineByVenteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenteGetDto> findVente(@PathVariable Long id) {
        VenteGetDto vente = venteService.findDtoById(id);
        return ResponseEntity.ok(vente);
    }

    @PostMapping("")
    public ResponseEntity<Vente> addVente(@RequestBody VentePostDto ventePostDto) {
        Vente vente = venteService.save(ventePostDto);
        return ResponseEntity.ok(vente);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id, @Valid @RequestBody VenteUpdateDto venteUpdateDto) {

        Vente vente = venteService.updateVente(venteUpdateDto, id);
        return ResponseEntity.ok(vente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVente(@PathVariable Long id) {

        venteService.softDeleteVente(id);
        return ResponseEntity.ok("Vente deleted");
    }


    @PostMapping("/valide-panier")
    @Transactional // Active la gestion transactionnelle
    public ResponseEntity<VenteGetDto> createVenteNLignes(@Valid @RequestBody ValiderPanierDTO validerPanierDTO
    ) {

        VenteGetDto venteGetDto = venteService.validerVenteDansPanier(validerPanierDTO);

        return ResponseEntity.ok(venteGetDto);
    }


    @PostMapping("/payer/{id}")
    public ResponseEntity<String> payerVenteSansState(@PathVariable Long id, @RequestBody  PaiementRequest paiementRequest) {

        venteService.payer(id, paiementRequest);

        return ResponseEntity.ok(" Vente payed !!");
    }

    @PostMapping("/payer/{id}/avoir")
    public ResponseEntity<String> payerVenteAvoir(@PathVariable Long id, @RequestBody PaiementRequestAvoir paiementRequestAvoir) {

        venteService.payerAvoir(id, paiementRequestAvoir);

        return ResponseEntity.ok("Vente avec avoir payée !!");
    }

    @GetMapping("/credit/{id}")
    public ResponseEntity<String> marquerCommeCredit(@PathVariable Long id) {

        venteService.marquerCommeCredit(id);

        return ResponseEntity.ok(" Credit validé !!");
    }


    @GetMapping("/{id}/fermer")
    public ResponseEntity<String> fermerVente(@PathVariable Long id) {

        venteService.fermerVente(id);

        return ResponseEntity.ok(" Vente fermé !!");
    }


    @GetMapping("/{id}/annuler")
    public ResponseEntity<String> annulerVente(@PathVariable Long id) {

        venteService.annulerVente(id);

        return ResponseEntity.ok(" Vente annulé !!");
    }

    @GetMapping("/{id}/rembourser")
    public ResponseEntity<String> rembourserVente(@PathVariable Long id) {

        venteService.rembourserVente(id);

        return ResponseEntity.ok().body("Rembourser !!");
    }


}
