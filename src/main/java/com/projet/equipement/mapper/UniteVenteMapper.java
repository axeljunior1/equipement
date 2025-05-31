package com.projet.equipement.mapper;

import com.projet.equipement.dto.uniteVente.UniteVenteGetDto;
import com.projet.equipement.dto.uniteVente.UniteVentePostDto;
import com.projet.equipement.dto.uniteVente.UniteVenteUpdateDto;
import com.projet.equipement.entity.UniteVente;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UniteVenteMapper {


    UniteVenteGetDto toDto(UniteVente uniteVente);

    UniteVente toEntity(UniteVenteGetDto uniteVenteGetDto);

    UniteVente toEntity(UniteVentePostDto uniteVenteGetDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto( UniteVenteUpdateDto uniteVenteUpdateDto, @MappingTarget UniteVente uniteVente);

}
