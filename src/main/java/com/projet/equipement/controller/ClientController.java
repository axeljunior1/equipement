package com.projet.equipement.controller;

import com.projet.equipement.dto.client.ClientGetDto;
import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.services.ClientService;
import com.projet.equipement.utils.PaginationUtil;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/clients")
@RestController
public class ClientController {

    private final ClientService clientService;
    private EntityManager entityManager;

    public ClientController(ClientService clientService, EntityManager entityManager) {
        this.clientService = clientService;
        this.entityManager = entityManager;
    }

    @GetMapping("")
    public ResponseEntity<Page<Client>> findAllClients(Pageable pageable) {

        Page<Client> clients = clientService.findAll(pageable);

        return ResponseEntity.ok( clients) ;
    }

    @GetMapping("/recherche")
    public ResponseEntity<Page<ClientGetDto>> rechercherClients(@RequestParam String motCle, Pageable pageable) {

        List<ClientGetDto> produits = clientService.rechercherClients(motCle);
        Page<ClientGetDto> clientGetDtos = PaginationUtil.toPage(produits, pageable);

        return ResponseEntity.ok(clientGetDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity< Client> findClient(@PathVariable Long id) {

         Client client = clientService.findById(id);

        return ResponseEntity.ok(client);
    }
    @GetMapping("/test-clients")
    public List<Client> testClients() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("tenantFilter").setParameter("tenantId", "ent3");


        return entityManager.createQuery("from Client", Client.class).getResultList();
    }
    @GetMapping("/debug-clients")
    public List<Client> debugClients() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("tenantFilter").setParameter("tenantId", "ent3");

        return entityManager.createQuery("from Client", Client.class).getResultList();
    }


    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody ClientPostDto clientPostDto) {
       Client client = clientService.save(clientPostDto);
        return ResponseEntity.ok(client);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable Long id , @Valid  @RequestBody ClientUpdateDto clientUpdateDto) {

        // Objets pour construire Client et ses Rôles
        clientService.updateClient(clientUpdateDto, id);
        return ResponseEntity.ok().body("client updated");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id ) {
        clientService.deleteById(id);
        return ResponseEntity.ok("Client deleted");
    }



}
