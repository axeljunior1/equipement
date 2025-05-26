package com.projet.equipement.mapper;

import com.projet.equipement.dto.formatVente.FormatVenteGetDto;
import com.projet.equipement.dto.formatVente.FormatVentePostDto;
import com.projet.equipement.dto.formatVente.FormatVenteUpdateDto;
import com.projet.equipement.entity.FormatVente;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.UniteVente;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface FormatVenteMapper {


    @Mapping(source = "uniteVente", target = "uniteVenteId", qualifiedByName = "mapUniteVenteToId")
    @Mapping(source = "uniteVente", target = "uniteVenteNom", qualifiedByName = "mapUniteVenteToNom")
    @Mapping(source = "produit", target = "produitId", qualifiedByName = "mapProduitToId")
    @Mapping(source = "produit", target = "produitNom", qualifiedByName = "mapProduitToNom")
    FormatVenteGetDto toDto(FormatVente formatVente);

    FormatVente toEntity(FormatVenteGetDto formatVenteGetDto);

    FormatVente toEntity(FormatVentePostDto formatVenteGetDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto( FormatVenteUpdateDto formatVenteUpdateDto, @MappingTarget FormatVente formatVente);

    @Named("mapUniteVenteToId")
    default Long mapUniteVenteToId(UniteVente uniteVente) {
        return uniteVente != null ? uniteVente.getId() : null;
    }

    @Named("mapUniteVenteToNom")
    default String mapUniteVenteToNom(UniteVente uniteVente) {
        return uniteVente != null ? uniteVente.getCode() : null;
    }

    @Named("mapProduitToId")
    default Long mapProduitToId(Produit produit) {
        return produit.getId();
    }


    @Named("mapProduitToNom")
    default String mapProduitToNom(Produit produit) {
        return produit.getNom();
    }




}
