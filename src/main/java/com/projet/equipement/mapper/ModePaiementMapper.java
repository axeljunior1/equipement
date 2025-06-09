package com.projet.equipement.mapper;

import com.projet.equipement.dto.modePaiement.ModePaiementPostDto;
import com.projet.equipement.dto.modePaiement.ModePaiementUpdateDto;
import com.projet.equipement.dto.modePaiement.ModePaiementGetDto;
import com.projet.equipement.entity.ModePaiement;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ModePaiementMapper {


    ModePaiementGetDto toDto(ModePaiement modePaiement);
    
    
    ModePaiement toEntity(ModePaiementPostDto modePaiementGetDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto( ModePaiementUpdateDto modePaiementUpdateDto, @MappingTarget ModePaiement modePaiement);


}
