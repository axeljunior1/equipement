package com.projet.equipement.mapper;

import com.projet.equipement.dto.etatDto.EtatVenteGetDto;
import com.projet.equipement.entity.EtatVente;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EtatVenteMapper {

    EtatVenteGetDto toGetDto(EtatVente etatVente) ;

}
