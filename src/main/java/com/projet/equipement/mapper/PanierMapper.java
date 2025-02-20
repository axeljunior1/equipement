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

    @Mapping(target = "employe", source = "employeId", qualifiedByName = "mapIdToEmploye")
    @Mapping(target = "etat", source = "etatId", qualifiedByName = "mapIdToEtat")
    Panier toEntity(PanierPostDto panierPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "employe", source = "employeId", qualifiedByName = "mapIdToEmploye")
    @Mapping(target = "etat", source = "etatId", qualifiedByName = "mapIdToEtat")
    void updateProduitFromDto(PanierUpdateDto panierUpdateDto, @MappingTarget Panier panier);

    @Named("mapIdToEmploye")
    default Employe mapIdToEmploye(Long id){
        Employe employe = new Employe();
        employe.setId(id);
        return employe;
    }

    @Named("mapIdToEtat")
    default Etat mapIdToEtat(Long id){
        Etat employe = new Etat();
        employe.setId(id);
        return employe;
    }

    @Named("mapCategorieToId")
    default Long mapCategorieToId( Categorie categorie){
        return categorie.getId();
    }
    @Named("mapCategorieToNom")
    default String mapCategorieToNom( Categorie categorie){
        return categorie.getNom();
    }


}
