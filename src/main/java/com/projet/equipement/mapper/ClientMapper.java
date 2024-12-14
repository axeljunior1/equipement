package com.projet.equipement.mapper;

import com.projet.equipement.dto.ClientDto;
import com.projet.equipement.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {



    public void updateClientFromDto(ClientDto clientDto, Client client){
        if (clientDto.getFirstName() != null) client.setFirstName(clientDto.getFirstName());
        if (clientDto.getLastName() != null) client.setLastName(clientDto.getLastName());
        if (clientDto.getEmail() != null) client.setEmail(clientDto.getEmail());
        if (clientDto.getPhone() != null) client.setPhone(clientDto.getPhone());
        if(clientDto.getAddress() != null) client.setAddress(clientDto.getAddress());
        if (clientDto.getCity() != null) client.setCity(clientDto.getCity());
        if (clientDto.getState() != null) client.setState(clientDto.getState());
        if (clientDto.getZip() != null) client.setZip(clientDto.getZip());
        if (clientDto.getCountry() != null) client.setCountry(clientDto.getCountry());

    }
}
