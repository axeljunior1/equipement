package com.projet.equipement.mapper;

import com.projet.equipement.dto.client.ClientGetDto;
import com.projet.equipement.dto.client.ClientPostDto;
import com.projet.equipement.dto.client.ClientUpdateDto;
import com.projet.equipement.entity.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ClientMapper {


    ClientGetDto toDto(Client client);

    Client toEntity(ClientGetDto clientGetDto);

    Client toEntity(ClientPostDto clientGetDto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto( ClientUpdateDto clientUpdateDto, @MappingTarget Client client);



}
