package com.projet.equipement.mapper;

import com.projet.equipement.dto.mvt_stk.MouvementStockGetDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockPostDto;
import com.projet.equipement.dto.mvt_stk.MouvementStockUpdateDto;
import com.projet.equipement.entity.MouvementStock;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TypeMouvementStock;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface MouvementStockMapper {


    @Mapping(source = "produit", target = "produitId", qualifiedByName = "mapProduitToId")
    @Mapping(source = "produit", target = "produitNom", qualifiedByName = "mapProduitToNom")
    @Mapping(source = "typeMouvement", target = "typeMouvementCode", qualifiedByName = "mapTypeMouvementToCode")
    @Mapping(source = "typeMouvement", target = "typeMouvementId", qualifiedByName = "mapTypeMouvementToId")
    MouvementStockGetDto toDto(MouvementStock mouvementStock) ;

    MouvementStock toEntity(MouvementStockPostDto mouvementStockPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMouvementStockFromDto(MouvementStockUpdateDto mouvementStockUpdateDto, @MappingTarget MouvementStock mouvementStock);


    @Named("mapProduitToId")
    default Long mapProduitToId(Produit produit) {
        return produit != null ? produit.getId() : null;
    }
    @Named("mapProduitToNom")
    default String mapProduitToNom(Produit produit) {
        return produit != null ? produit.getNom() : null;
    }

    @Named("mapTypeMouvementToId")
    default Long mapTypeMouvementToId(TypeMouvementStock typeMouvementStock) {
        return typeMouvementStock != null ? typeMouvementStock.getId() : null;
    }

    @Named("mapTypeMouvementToCode")
    default String mapTypeMouvementToCode(TypeMouvementStock typeMouvementStock) {
        return typeMouvementStock != null ? typeMouvementStock.getCode() : null;
    }


}
