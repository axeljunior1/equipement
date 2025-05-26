package com.projet.equipement.mapper;

import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.Devise;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TarifAchat;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.util.List;


@Mapper(componentModel = "spring", uses = CategorieMapper.class)
public interface ProduitMapper {



    @Mapping(target = "categorieId", source = "categorie", qualifiedByName = "mapCategorieToId")
    @Mapping(target = "categorieNom", source = "categorie", qualifiedByName = "mapCategorieToNom")
    @Mapping(target = "deviseCode", source = "devise", qualifiedByName = "mapDevToCode")
    @Mapping(target = "deviseSymbole", source = "devise", qualifiedByName = "mapDevToSymbole")
    ProduitGetDto toGetDto(Produit produit) ;

    @Mapping(target = "categorie", source = "categorieId", qualifiedByName = "mapIdToCategorie")
    Produit toEntity(ProduitPostDto produitPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "categorie", source = "categorieId", qualifiedByName = "mapIdToCategorie")
    void updateProduitFromDto(ProduitUpdateDto produitUpdateDto, @MappingTarget Produit produit);

    @Named("mapIdToCategorie")
    default Categorie mapIdToCategorie(Long id){
        Categorie categorie = new Categorie();
        categorie.setId(id);
        return categorie;
    }

    @Named("mapCategorieToId")
    default Long mapCategorieToId( Categorie categorie){
        return categorie.getId();
    }

    @Named("mapTarifToPrix")
    default BigDecimal mapTarifToPrix(List<TarifAchat> tarifAchat){
        return tarifAchat.get(0).getPrixAchat();
    }

    @Named("mapCategorieToNom")
    default String mapCategorieToNom( Categorie categorie){
        return categorie.getNom();
    }

    @Named("mapDevToCode")
    default String mapDevToCode(Devise devise){
        return devise.getCode();
    }
    @Named("mapDevToSymbole")
    default String mapDevToSymbole(Devise devise){
        return devise.getSymbole();
    }


}
