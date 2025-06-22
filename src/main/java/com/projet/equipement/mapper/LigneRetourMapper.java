package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourPostDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourUpdateDto;
import com.projet.equipement.entity.LigneRetour;
import com.projet.equipement.entity.Retour;
import com.projet.equipement.entity.TypeRetour;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {RetourMapper.class, LigneVenteMapper.class, TypeRetour.class})
public interface LigneRetourMapper {

    @Mapping(source = "retour", target = "retourId", qualifiedByName = "mapRetourToId")
    LigneRetourGetDto toDto(LigneRetour ligneRetour);

    LigneRetour toEntity(LigneRetourPostDto ligneRetourPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(LigneRetourUpdateDto ligneRetourUpdateDto, @MappingTarget LigneRetour ligneRetour);

    @Named("mapRetourToId")
    default Long mapRetourToId(Retour retour){
        return retour.getId();
    }


}