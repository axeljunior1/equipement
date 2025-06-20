package com.projet.equipement.mapper;

import com.projet.equipement.dto.retour.RetourGetDto;
import com.projet.equipement.dto.retour.RetourPostDto;
import com.projet.equipement.dto.retour.RetourUpdateDto;
import com.projet.equipement.entity.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VenteMapper.class, EtatRetour.class, TypeRetour.class})
public interface RetourMapper {

    RetourGetDto toDto(Retour retour);


    Retour toEntity(RetourPostDto retourPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(RetourUpdateDto retourUpdateDto, @MappingTarget Retour retour);


//    @Named("mapTypeToId")
//    default Long mapTypeToId(TypeRetour typeRetour) {
//        return typeRetour.getId();
//    }
//
//    @Named("mapVenteToId")
//    default Long mapVenteToId(Vente vente) {
//        return vente.getId();
//    }
//
//    @Named("mapEtatToId")
//    default Long mapEtatToId(EtatRetour etatRetour) {
//        return etatRetour.getId();
//    }


}