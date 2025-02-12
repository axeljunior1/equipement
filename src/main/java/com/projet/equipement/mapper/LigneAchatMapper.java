package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring" , uses = {ProduitMapper.class, AchatMapper.class})
public interface LigneAchatMapper {

    @Mapping(source = "produit", target = "produitId", qualifiedByName = "mapProduitToId")
    @Mapping(source = "achat", target = "achatId", qualifiedByName = "mapAchatToId")
    LigneAchatGetDto toDto(LigneAchat ligneAchat);

    @Mapping(source = "produitId", target = "produit", qualifiedByName = "mapIdToProduit")
    @Mapping(source = "achatId", target = "achat", qualifiedByName = "mapIdToAchat")
    LigneAchat toEntity(LigneAchatGetDto ligneAchatGetDto);

    @Mapping(source = "produitId", target = "produit", qualifiedByName = "mapIdToProduit")
    @Mapping(source = "achatId", target = "achat", qualifiedByName = "mapIdToAchat")
    LigneAchat toEntity(LigneAchatPostDto ligneAchatPostDto);

    @Mapping(source = "produitId", target = "produit", qualifiedByName = "mapIdToProduit")
    @Mapping(source = "achatId", target = "achat", qualifiedByName = "mapIdToAchat")
    void updateLigneAchatFromDto(LigneAchatUpdateDto ligneAchatUpdateDto, @MappingTarget LigneAchat ligneAchat);

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


    @Named("mapAchatToId")
    default Long mapAchatToId(Achat achat) {
        return achat.getId();
    }

    @Named("mapIdToAchat")
    default Achat mapIdToAchat(Long id) {
        Achat achat = new Achat();
        achat.setId(id);
        return achat;
    }



}
