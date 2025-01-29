package com.projet.equipement.controller;

import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/clients")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("")
    public ResponseEntity<Page<Client>> findAllClients(Pageable pageable) {
        Page<Client> clients = clientService.findAll(pageable);
        return ResponseEntity.ok( clients) ;
    }
    @GetMapping("/{id}")
    public ResponseEntity< Client> findClient(@PathVariable Long id) {
         Client client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }
    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody ClientPostDto clientPostDto) {
       Client client = clientService.save(clientPostDto);
        return ResponseEntity.ok(client);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id , @Valid  @RequestBody ClientUpdateDto clientUpdateDto) {

        // Objets pour construire Client et ses Rôles
        Client client = clientService.updateClient(clientUpdateDto, id);
        return ResponseEntity.ok(client);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id ) {
        clientService.deleteById(id);
        return ResponseEntity.ok("Client deleted");
    }



}
