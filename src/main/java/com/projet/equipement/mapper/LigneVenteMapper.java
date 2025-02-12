package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.ligneVente.LigneVentePostDto;
import com.projet.equipement.dto.ligneVente.LigneVenteUpdateDto;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.entity.LigneVente;
import com.projet.equipement.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {ProduitMapper.class, VenteMapper.class})
public interface LigneVenteMapper {

    @Mapping(source = "produit", target = "produitId", qualifiedByName = "mapProduitToId")
    @Mapping(source = "vente", target = "venteId", qualifiedByName = "mapVenteToId")
    LigneVenteGetDto toDto(LigneVente ligneVente);

    @Mapping(source = "produitId", target = "produit", qualifiedByName = "mapIdToProduit")
    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente")
    LigneVente toEntity(LigneVenteGetDto ligneVenteGetDto);

    @Mapping(source = "produitId", target = "produit", qualifiedByName = "mapIdToProduit")
    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente")
    LigneVente toEntity(LigneVentePostDto ligneVentePostDto);

    @Mapping(source = "produitId", target = "produit", qualifiedByName = "mapIdToProduit")
    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente")
    void updateLigneVenteFromDto(LigneVenteUpdateDto ligneVenteUpdateDto, @MappingTarget LigneVente ligneVente);

    @Named("mapIdToProduit")
    default Produit mapIdToProduit(Long id) {
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }

    @Named("mapProduitToId")
    default Long mapProduitToId(Produit produit) {
        return produit.getId();
    }


    @Named("mapVenteToId")
    default Long mapVenteToId(Vente vente) {
        return vente.getId();
    }

    @Named("mapIdToVente")
    default Vente mapIdToVente(Long id) {
        Vente vente = new Vente();
        vente.setId(id);
        return vente;
    }
}
