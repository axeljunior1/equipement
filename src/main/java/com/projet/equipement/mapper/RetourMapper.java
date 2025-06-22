package com.projet.equipement.mapper;

import com.projet.equipement.dto.retour.RetourGetDto;
import com.projet.equipement.dto.retour.RetourLightGetDto;
import com.projet.equipement.dto.retour.RetourPostDto;
import com.projet.equipement.dto.retour.RetourUpdateDto;
import com.projet.equipement.entity.*;
import jdk.jfr.Name;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VenteMapper.class, EtatRetour.class, TypeRetour.class})
public interface RetourMapper {

    RetourGetDto toDto(Retour retour);

    @Mapping(target = "venteId", source = "vente", qualifiedByName = "mapVenteToId")
    @Mapping(target = "etatId", source = "etat", qualifiedByName = "mapEtatToId")
    @Mapping(target = "typeId", source = "typeRetour", qualifiedByName = "mapTypeToId")
    @Mapping(target = "typeLibelle", source = "typeRetour", qualifiedByName = "mapTypeToLibelle")
    @Mapping(target = "etatLibelle", source = "etat", qualifiedByName = "mapEtatToLibelle")
    @Mapping(target = "clientId", source = "vente", qualifiedByName = "mapVenteToClientId")
    @Mapping(target = "clientNom", source = "vente", qualifiedByName = "mapVenteToClientNom")
    @Mapping(target = "employeId", source = "vente", qualifiedByName = "mapVenteToEmployeId")
    @Mapping(target = "employeNom", source = "vente", qualifiedByName = "mapVenteToEmployeNom")
    RetourLightGetDto toLightDto(Retour retour);


    Retour toEntity(RetourPostDto retourPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(RetourUpdateDto retourUpdateDto, @MappingTarget Retour retour);

    @Named("mapVenteToId")
    default Long mapVenteToId(Vente vente) {
        return vente != null ? vente.getId() : null;
    }
    @Named("mapEtatToId")
    default Long mapEtatToId(EtatRetour etatRetour) {
        return etatRetour != null ? etatRetour.getId() : null;
    }
    @Named("mapEtatToLibelle")
    default String mapEtatToLibelle(EtatRetour etatRetour) {
        return etatRetour != null ? etatRetour.getLibelle() : "";
    }
    @Named("mapTypeToId")
    default Long mapTypeToId(TypeRetour typeRetour) {
        return typeRetour != null ? typeRetour.getId() : null;
    }
    @Named("mapTypeToLibelle")
    default String mapTypeToLibelle(TypeRetour typeRetour) {
        return typeRetour != null ? typeRetour.getLibelle() : "";
    }
    @Named("mapVenteToClientId")
    default Long mapVenteToClientId(Vente vente){
        return vente.getClient() != null ? vente.getClient().getId() : null;
    }
    @Named("mapVenteToClientNom")
    default String mapVenteToClientNom(Vente vente){
        return vente.getClient() != null ? vente.getClient().getNom() : "";
    }
    @Named("mapVenteToEmployeId")
    default Long mapVenteToEmployeId(Vente vente){
        return vente.getEmploye() != null ? vente.getEmploye().getId() : null;
    }
    @Named("mapVenteToEmployeNom")
    default String mapVenteToEmployeNom(Vente vente){
        return vente.getEmploye() != null ? vente.getEmploye().getNom() : "";
    }
}