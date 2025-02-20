package com.projet.equipement.mapper;

import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {EmployeMapper.class, ClientMapper.class , LigneVenteMapper.class})
public interface VenteMapper {


    @Mapping(source = "employe", target = "employeId", qualifiedByName = "mapEmployeToId")
    VenteGetDto toDto(Vente vente);

    @Mapping(source = "employeId", target = "employe", qualifiedByName = "mapIdToEmploye")
    Vente toEntity(VenteGetDto venteGetDto);

    @Mapping(source = "clientId", target = "client", qualifiedByName = "mapIdToClient")
    @Mapping(source = "employeId", target = "employe", qualifiedByName = "mapIdToEmploye")
    Vente toEntity(VentePostDto venteGetDto);

    @Mapping(source = "clientId", target = "client", qualifiedByName = "mapIdToClient")
    @Mapping(source = "employeId", target = "employe", qualifiedByName = "mapIdToEmploye")
    void updateDto( VenteUpdateDto venteUpdateDto, @MappingTarget Vente vente);


    @Named("mapEmployeToId")
    default Long mapEmployeToId(Employe employe){
        return employe.getId();
    }

    @Named("mapIdToEmploye")
    default Employe mapIdToEmploye(Long id){
        Employe employe = new Employe();
        employe.setId(id);
        return employe;
    }

    @Named("mapIdToClient")
    default Client mapIdToClient(Long id){
        Client client = new Client();
        client.setId(id);
        return client;
    }


}
