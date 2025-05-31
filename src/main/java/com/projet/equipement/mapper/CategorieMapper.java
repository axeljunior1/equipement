package com.projet.equipement.mapper;

import com.projet.equipement.dto.categorie.CategorieGetDto;
import com.projet.equipement.dto.categorie.CategoriePostDto;
import com.projet.equipement.dto.categorie.CategorieUpdateDto;
import com.projet.equipement.entity.Categorie;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface CategorieMapper {


    CategorieGetDto toGetDto(Categorie categorie) ;

    Categorie toEntity(CategoriePostDto categoriePostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCategorieFromDto(CategorieUpdateDto categorieUpdateDto, @MappingTarget Categorie categorie);


}
