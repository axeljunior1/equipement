package com.projet.equipement.mapper;

import com.projet.equipement.dto.panierProduit.PanierProduitGetDto;
import com.projet.equipement.dto.panierProduit.PanierProduitPostDto;
import com.projet.equipement.dto.panierProduit.PanierProduitUpdateDto;
import com.projet.equipement.entity.FormatVente;
import com.projet.equipement.entity.Panier;
import com.projet.equipement.entity.PanierProduit;
import com.projet.equipement.entity.Produit;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = PanierMapper.class)
public interface PanierProduitMapper {




    @Mapping(target = "formatVenteId", source = "formatVente", qualifiedByName = "mapFormatVenteToId")
    PanierProduitGetDto toGetDto(PanierProduit produit) ;

    PanierProduit toEntity(PanierProduitGetDto panierProduitGetDto);

    @Mapping(target = "produit", source = "produitId", qualifiedByName = "mapIdToProduit")
    @Mapping(target = "panier", source = "panierId", qualifiedByName = "mapIdToPanier")
    PanierProduit toEntity(PanierProduitPostDto panierProduitPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduitFromDto(PanierProduitUpdateDto panierProduitUpdateDto, @MappingTarget PanierProduit panierProduit);

    @Named("mapIdToProduit")
    default Produit mapIdToProduit(Long id){
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }

    @Named("mapIdToPanier")
    default Panier mapIdToPanier(Long id){
        Panier panier = new Panier();
        panier.setId(id);
        return panier;
    }

    @Named("mapFormatVenteToId")
    default Long mapFormatVenteToId( FormatVente formatVente){
        return formatVente.getId();
    }

    @Named("mapProduitToId")
    default Long mapProduitToId( Produit produit){
        return produit.getId();
    }

    @Named("mapPanierToId")
    default Long mapPanierToId( Panier panier){
        return panier.getId();
    }


}
