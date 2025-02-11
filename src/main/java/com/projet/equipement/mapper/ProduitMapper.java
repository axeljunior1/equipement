package com.projet.equipement.mapper;

import com.projet.equipement.dto.produit.ProduitGetDto;
import com.projet.equipement.dto.produit.ProduitPostDto;
import com.projet.equipement.dto.produit.ProduitUpdateDto;
import com.projet.equipement.entity.Categorie;
import com.projet.equipement.entity.Produit;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface ProduitMapper {



    @Mapping(target = "categorieId", source = "categorie", qualifiedByName = "mapCategorieToId")
    @Mapping(target = "categorieNom", source = "categorie", qualifiedByName = "mapCategorieToNom")
    @Mapping(target = "id", source = "id")
    ProduitGetDto toGetDto(Produit produit) ;

    Produit toEntity(ProduitGetDto produitGetDto);

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
    @Named("mapCategorieToNom")
    default String mapCategorieToNom( Categorie categorie){
        return categorie.getNom();
    }

//    public void updateProduitFromDto(ProduitUpdateDto produitUpdateDto, Produit produit, Categorie categorie){
//        if (produitUpdateDto.getNom() != null) produit.setNom(produitUpdateDto.getNom());
//        if (produitUpdateDto.getDescription() != null) produit.setDescription(produitUpdateDto.getDescription());
//        if (produitUpdateDto.getImage() != null) produit.setImage(produitUpdateDto.getImage());
//        if (produitUpdateDto.getPrixUnitaire() != null) produit.setPrixUnitaire(produitUpdateDto.getPrixUnitaire());
//        if (produitUpdateDto.getStockInitial() != null) produit.setStockInitial(produitUpdateDto.getStockInitial());
//        if (produitUpdateDto.getQrCode() != null) produit.setQrCode(produitUpdateDto.getQrCode());
//        if (produitUpdateDto.getSeuilProduit() !=null) produit.setSeuilProduit(produitUpdateDto.getSeuilProduit());
//        if(categorie != null) produit.setCategorie(categorie);
//    }
//
//
//
//
//
//    public Produit PostProduitFromDto(ProduitPostDto produitPostDto, @NotNull Categorie categorie ){
//        return Produit.builder()
//                .nom(produitPostDto.getNom())
//                .description(produitPostDto.getDescription())
//                .image(produitPostDto.getImage())
//                .prixUnitaire(produitPostDto.getPrixUnitaire())
//                .stockInitial(produitPostDto.getStockInitial())
//                .actif(true)
//                .categorie(categorie)
//                .build();
//    }

}
