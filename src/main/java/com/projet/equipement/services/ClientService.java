package com.projet.equipement.services;


import com.projet.equipement.entity.Client;
import com.projet.equipement.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> findAll(){
        return clientRepository.findAll();
    }
    public Optional<Client> findById(int id){
        return Optional.of(clientRepository.findById(id)).get();
    }
    public Client save(Client client){
        return clientRepository.save(client);
    }
    public void deleteById(int id){
        clientRepository.deleteById(id);
    }

}
