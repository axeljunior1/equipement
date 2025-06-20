package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourPostDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourUpdateDto;
import com.projet.equipement.entity.LigneRetour;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {RetourMapper.class, LigneVenteMapper.class})
public interface LigneRetourMapper {

    LigneRetourGetDto toDto(LigneRetour ligneRetour);

    LigneRetour toEntity(LigneRetourPostDto ligneRetourPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(LigneRetourUpdateDto ligneRetourUpdateDto, @MappingTarget LigneRetour ligneRetour);



}