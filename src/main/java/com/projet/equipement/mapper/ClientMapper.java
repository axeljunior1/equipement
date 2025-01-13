package com.projet.equipement.mapper;

import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {



    public void updateClientFromDto(ClientUpdateDto clientUpdateDto, Client client){
        if (clientUpdateDto.getNom() != null) client.setNom(clientUpdateDto.getNom());
        if (clientUpdateDto.getPrenom() != null) client.setPrenom(clientUpdateDto.getPrenom());
        if (clientUpdateDto.getEmail() != null) client.setEmail(clientUpdateDto.getEmail());
        if (clientUpdateDto.getTelephone() != null) client.setTelephone(clientUpdateDto.getTelephone());
    }

    public Client postClientDto(ClientPostDto clientPostDto) {
        Client client = new Client();
        client.setNom(clientPostDto.getNom());
        client.setPrenom(clientPostDto.getPronom());
        client.setEmail(clientPostDto.getEmail());
        client.setTelephone(clientPostDto.getTelephone());
       return client;
    }
}
