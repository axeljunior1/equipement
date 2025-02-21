package com.projet.equipement.services;


import com.projet.equipement.dto.client.ClientGetDto;
import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.mapper.ClientMapper;
import com.projet.equipement.repository.ClientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client", id));
    }

    public Client findByTelephone(String tel) {
        return clientRepository.findByTelephone(tel)
                .orElseThrow(() -> new EntityNotFoundException("Client", tel ));
    }

    public Client save(ClientPostDto clientPostDto) {
//        Set<Role> roles = client.getRoles();
       Client client =  clientMapper.toEntity(clientPostDto);

        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }



    public Client updateClient(ClientUpdateDto clientUpdateDto, Long id) {
        Client client = findById(id);
//        Set<Role> roles = new HashSet<>();
        clientMapper.updateDto(clientUpdateDto,client);
        return clientRepository.save(client);
    }



    public List<ClientGetDto> rechercherClients(String motCle) {
        List<Client> clientsList = clientRepository.rechercherClients(motCle);
        return clientsList.stream().map(clientMapper::toDto).collect(Collectors.toList());
    }






}
