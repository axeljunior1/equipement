package com.projet.equipement.mapper;

import com.projet.equipement.dto.tarifAchat.TarifAchatGetDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatPostDto;
import com.projet.equipement.dto.tarifAchat.TarifAchatUpdateDto;
import com.projet.equipement.entity.Produit;
import com.projet.equipement.entity.TarifAchat;
import org.mapstruct.*;


@Mapper(componentModel = "spring", uses = ProduitMapper.class)
public interface TarifAchatMapper {

    @Mapping(target = "id", source = "id")
    TarifAchatGetDto toGetDto(TarifAchat tarifAchat) ;

    TarifAchat toEntity(TarifAchatGetDto tarifAchatProduitGetDto);

    @Mapping(target = "produit", source = "produitId", qualifiedByName = "mapIdToProduit")
    TarifAchat toEntity(TarifAchatPostDto tarifAchatPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduitFromDto(TarifAchatUpdateDto tarifAchatUpdateDto, @MappingTarget TarifAchat tarifAchat);

    @Named("mapIdToProduit")
    default Produit mapIdToProduit(Long id){
        Produit produit = new Produit();
        produit.setId(id);
        return produit;
    }

    @Named("maProduitToId")
    default Long maProduitToId(Produit produit){
        if (produit == null) return null;
        return produit.getId();
    }


}
