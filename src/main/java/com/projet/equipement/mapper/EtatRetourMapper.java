package com.projet.equipement.mapper;

import com.projet.equipement.dto.etatDto.EtatRetourGetDto;
import com.projet.equipement.entity.EtatRetour;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EtatRetourMapper {


    EtatRetourGetDto toGetDto(EtatRetour etatRetour) ;


}
