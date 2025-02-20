package com.projet.equipement.controller;

import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.Caisse;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.repository.ClientRepository;
import com.projet.equipement.services.ClientService;
import com.projet.equipement.services.TransactionVenteAndLinesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RequestMapping("/ventes")
@RestController
public class VenteController {

    private final ClientService clientService;
    private final ClientRepository clientRepository;
    private final TransactionVenteAndLinesService transactionVenteAndLinesService;

    public VenteController(ClientService clientService, ClientRepository clientRepository, TransactionVenteAndLinesService transactionVenteAndLinesService) {
        this.clientService = clientService;
        this.clientRepository = clientRepository;
        this.transactionVenteAndLinesService = transactionVenteAndLinesService;
    }



    @GetMapping("")
    public ResponseEntity<Page<VenteGetDto>> findAllVentes(Pageable pageable) {
        Page<VenteGetDto> ventes = transactionVenteAndLinesService.findAllVentes(pageable);
        return ResponseEntity.ok( ventes);
    }
    @GetMapping("/{id}/lignes")
    public ResponseEntity<Page<LigneVenteGetDto>> findAllLigneVentesByVenteId(@PathVariable Long id, Pageable pageable) {
        Page<LigneVenteGetDto> lineByVenteId = transactionVenteAndLinesService.findByVenteId(id, pageable);
        return ResponseEntity.ok(lineByVenteId);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity< VenteGetDto> findVente(@PathVariable Long id) {
        VenteGetDto vente = transactionVenteAndLinesService.findByIdVente(id);
        return ResponseEntity.ok(vente);
    }
    
    @PostMapping("")
    public ResponseEntity<Vente> addVente(@RequestBody VentePostDto ventePostDto) {
       Vente vente = transactionVenteAndLinesService.saveVente(ventePostDto);
        return ResponseEntity.ok(vente);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Vente> updateVente(@PathVariable Long id , @Valid  @RequestBody VenteUpdateDto venteUpdateDto) {

        Vente vente = transactionVenteAndLinesService.updateVente(venteUpdateDto, id);
        return ResponseEntity.ok(vente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVente(@PathVariable Long id ) {

        transactionVenteAndLinesService.softDeleteVente(id);
        return ResponseEntity.ok("Vente deleted");
    }




    @PostMapping("/createVenteNLignes")
@Transactional // Active la gestion transactionnelle
public ResponseEntity<Vente> createVenteNLignes( @Valid @RequestBody Caisse caisse) {

    Optional<Client> cli = clientRepository.findByTelephone(caisse.getClientTelephone());
    if (cli.isEmpty()) {
        if(caisse.clientTelephone.isEmpty() || caisse.clientTelephone.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        ClientPostDto clientPostDto = ClientPostDto.builder()
                .telephone(caisse.getClientTelephone())
                .nom(caisse.getClientNom())
                .prenom(caisse.getClientPrenom())
                .email(caisse.getClientEmail())

                .build();
        cli = Optional.ofNullable(clientService.save(clientPostDto));
    }

    Client client = cli.get();

    VentePostDto ventePostDto = VentePostDto.builder()
            .clientId(client.getId())
            .montantTotal(caisse.getVenteMontantTotal())
            .employeId(caisse.getVenteEmployeId())
            .updatedAt(LocalDateTime.now())
            .actif(true)
            .build();
    Vente vente = transactionVenteAndLinesService.saveVente(ventePostDto);

    caisse.getLignesCaisses().forEach(ligneCaisse -> {
        LigneVentePostDto ligneVentePostDto = LigneVentePostDto.builder()
                .venteId(Math.toIntExact(vente.getId()))
                .prixVente(ligneCaisse.getLVentePrixVenteUnitaire())
                .produitId(ligneCaisse.getLVenteProduitId())
                .quantite(ligneCaisse.getLVenteQuantite())
                .build();
        transactionVenteAndLinesService.saveLigneVente(ligneVentePostDto);
    });

    return ResponseEntity.ok(vente);
}



}
