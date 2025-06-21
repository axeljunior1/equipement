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

}