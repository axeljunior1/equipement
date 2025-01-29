package com.projet.equipement.controller;

import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.repository.ClientRepository;
import com.projet.equipement.services.ClientService;
import com.projet.equipement.services.LigneVenteService;
import com.projet.equipement.services.VenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequestMapping("/ventes")
@RestController
public class VenteController {

    private final VenteService venteService;
    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final LigneVenteService ligneVenteService;

    public VenteController(VenteService venteService, ClientService clientService, ClientRepository clientRepository, LigneVenteService ligneVenteService) {
        this.venteService = venteService;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.ligneVenteService = ligneVenteService;
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
@Transactional // Active la gestion transactionnelle
public ResponseEntity<Vente> createVenteNLignes( @RequestBody Caisse caisse) {

    Optional<Client> cli = clientRepository.findByTelephone(caisse.getClientTelephone());
    if (cli.isEmpty()) {
        ClientPostDto clientPostDto = ClientPostDto.builder()
                .telephone(caisse.getClientTelephone())
                .nom(caisse.getClientNom())
                .pronom(caisse.getClientPrenom())
                .email(caisse.getClientEmail())
                .build();
        cli = Optional.ofNullable(clientService.save(clientPostDto));
    }

    Client client = cli.get();

    VentePostDto ventePostDto = VentePostDto.builder()
            .clientId(client.getId())
            .montantTotal(caisse.getVenteMontantTotal())
            .employeId(caisse.getVenteEmployeId())
            .dateDerniereMiseAjour(LocalDateTime.now())
            .build();
    Vente vente = venteService.save(ventePostDto);

    caisse.getLignesCaisses().forEach(ligneCaisse -> {
        LigneVentePostDto ligneVentePostDto = LigneVentePostDto.builder()
                .venteId(Math.toIntExact(vente.getId()))
                .prixVenteUnitaire(ligneCaisse.getLVentePrixVenteUnitaire())
                .produitId(ligneCaisse.getLVenteProduitId())
                .quantite(ligneCaisse.getLVenteQuantite())
                .build();
        ligneVenteService.save(ligneVentePostDto);
    });

    return ResponseEntity.ok(vente);
}



}
