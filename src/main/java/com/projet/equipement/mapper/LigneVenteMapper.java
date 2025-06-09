package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.FormatVente;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Produit;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class, VenteMapper.class})
public interface LigneVenteMapper {

    @Mapping(source = "produit", target = "produitId", qualifiedByName = "mapProduitToId")
    @Mapping(source = "vente", target = "venteId", qualifiedByName = "mapVenteToId")
    @Mapping(source = "formatVente", target = "formatVenteId", qualifiedByName = "mapFormatToId")
    @Mapping(source = "formatVente", target = "formatVenteLibelle", qualifiedByName = "mapFormatToLib")
    LigneVenteGetDto toDto(LigneVente ligneVente);


    LigneVente toEntity(LigneVentePostDto ligneVentePostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLigneVenteFromDto(LigneVenteUpdateDto ligneVenteUpdateDto, @MappingTarget LigneVente ligneVente);


    @Named("mapProduitToId")
    default Long mapProduitToId(Produit produit) {
        return produit.getId();
    }

    @Named("mapFormatToId")
    default Long mapFormatToId(FormatVente formatVente) {
        return formatVente.getId();
    }
    @Named("mapFormatToLib")
    default String mapFormatToLib(FormatVente formatVente) {
        return formatVente.getLibelleFormat();
    }


    @Named("mapVenteToId")
    default Long mapVenteToId(Vente vente) {
        return vente.getId();
    }


}
