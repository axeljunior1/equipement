package com.projet.equipement.mapper;

import com.projet.equipement.dto.panier.PanierGetDto;
import com.projet.equipement.dto.panier.PanierPostDto;
import com.projet.equipement.dto.panier.PanierUpdateDto;
import com.projet.equipement.entity.*;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = EmployeMapper.class)
public interface PanierMapper {




    PanierGetDto toGetDto(Panier panier) ;

    Panier toEntity(PanierGetDto panierProduitGetDto);

    Panier toEntity(PanierPostDto panierPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduitFromDto(PanierUpdateDto panierUpdateDto, @MappingTarget Panier panier);


    @Named("mapCategorieToId")
    default Long mapCategorieToId( Categorie categorie){
        return categorie.getId();
    }
    @Named("mapCategorieToNom")
    default String mapCategorieToNom( Categorie categorie){
        return categorie.getNom();
    }


}
