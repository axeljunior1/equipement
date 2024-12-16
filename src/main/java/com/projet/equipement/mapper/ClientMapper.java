package com.projet.equipement.mapper;

import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {



    public void updateClientFromDto(ClientUpdateDto clientUpdateDto, Client client){
        if (clientUpdateDto.getFirstName() != null) client.setFirstName(clientUpdateDto.getFirstName());
        if (clientUpdateDto.getLastName() != null) client.setLastName(clientUpdateDto.getLastName());
        if (clientUpdateDto.getEmail() != null) client.setEmail(clientUpdateDto.getEmail());
        if (clientUpdateDto.getPhone() != null) client.setPhone(clientUpdateDto.getPhone());
        if(clientUpdateDto.getAddress() != null) client.setAddress(clientUpdateDto.getAddress());
        if (clientUpdateDto.getCity() != null) client.setCity(clientUpdateDto.getCity());
        if (clientUpdateDto.getState() != null) client.setState(clientUpdateDto.getState());
        if (clientUpdateDto.getZip() != null) client.setZip(clientUpdateDto.getZip());
        if (clientUpdateDto.getCountry() != null) client.setCountry(clientUpdateDto.getCountry());
    }
}
