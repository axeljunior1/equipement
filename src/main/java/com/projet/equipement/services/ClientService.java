package com.projet.equipement.services;


import com.projet.equipement.dto.ClientDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ClientMapper;
import com.projet.equipement.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client", id));
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(ClientDto clientDto, Long id) {
        Client client = findById(id);
        clientMapper.updateClientFromDto(clientDto, client);
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

}
