package com.projet.equipement.services;


import com.projet.equipement.dto.client.ClientGetDto;
import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.TenantContext;
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
    private final EntityManagerSetupService entityManagerSetupService;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, EntityManagerSetupService entityManagerSetupService) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.entityManagerSetupService = entityManagerSetupService;
    }

    public Page<Client> findAll(Pageable pageable) {
        entityManagerSetupService.setTenantFilter();
        return clientRepository.findAll(pageable);
    }

    public Client findById(Long id) {
        entityManagerSetupService.setTenantFilter();
        return clientRepository.findWithTenant(id)
                .orElseThrow(() -> new EntityNotFoundException("Client", id));
    }

    public Client findByTelephone(String tel) {
        entityManagerSetupService.setTenantFilter();
        return clientRepository.findByTelephone(tel)
                .orElseThrow(() -> new EntityNotFoundException("Client", tel ));
    }

    public Client save(ClientPostDto clientPostDto) {
        entityManagerSetupService.setTenantFilter();
       Client client =  clientMapper.toEntity(clientPostDto);

        client.setTenantId(TenantContext.getTenantId());
        return clientRepository.save(client);
    }

    public void deleteById(Long id) {
        entityManagerSetupService.setTenantFilter();
        clientRepository.deleteById(id);
    }



    public void updateClient(ClientUpdateDto clientUpdateDto, Long id) {
        entityManagerSetupService.setTenantFilter();
        Client client = findById(id);
        clientMapper.updateDto(clientUpdateDto,client);
        clientRepository.save(client);
    }



    public List<ClientGetDto> rechercherClients(String motCle) {
        List<Client> clientsList = clientRepository.rechercherClients(motCle);
        return clientsList.stream().map(clientMapper::toDto).collect(Collectors.toList());
    }






}
