package com.projet.equipement.mapper;


import com.projet.equipement.dto.typeretour.TypeRetourGetDto;
import com.projet.equipement.entity.TypeRetour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeRetourMapper {


    TypeRetourGetDto toGetDto(TypeRetour typeRetour) ;


}
