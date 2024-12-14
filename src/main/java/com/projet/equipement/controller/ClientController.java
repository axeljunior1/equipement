package com.projet.equipement.controller;

import com.projet.equipement.dto.ClientDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/client")
@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;


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
    public ResponseEntity<Client> partialUpdateClient(@PathVariable Long id ,@Valid @RequestBody ClientDto clientDto) {
        Client updatedClient = clientService.updateClient(clientDto, id);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok("Client deleted");
    }

}
