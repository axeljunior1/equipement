package com.projet.equipement.controller;

import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/client")
@RestController
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Client> findClientById(@PathVariable Long id) {
         Client client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }
    @GetMapping("")
    public ResponseEntity<List<Client>> findAllClient() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    @PostMapping("")
    public ResponseEntity<Client> addClient(@RequestBody Client client) {
        clientService.save(client);
        return ResponseEntity.ok(client);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<Client> partialUpdateClient(@PathVariable Long id ,@Valid @RequestBody ClientUpdateDto clientUpdateDto) {
        Client updatedClient = clientService.updateClient(clientUpdateDto, id);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok("Client deleted");
    }

}
