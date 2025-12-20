package com.projet.equipement.mapper;

import com.projet.equipement.dto.utilisationAvoir.UtilisationAvoirGetDto;
import com.projet.equipement.entity.Avoir;
import com.projet.equipement.entity.UtilisationAvoir;
import com.projet.equipement.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public interface UtilisationAvoirMapper {


    @Mapping(target = "venteId", source = "vente", qualifiedByName = "mapVenteToId")
    @Mapping(target = "avoirId", source = "avoir", qualifiedByName = "mapAvoirToId")
    UtilisationAvoirGetDto toGetDto(UtilisationAvoir utilisationAvoir) ;

    @Named("mapVenteToId")
    default Long mapVenteToId(Vente vente) {
        return vente.getId();
    }
    @Named("mapAvoirToId")
    default Long mapAvoirToId(Avoir avoir) {
        return avoir.getId();
    }


}
