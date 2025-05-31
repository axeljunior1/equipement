package com.projet.equipement.mapper;

import com.projet.equipement.dto.ligneAchat.LigneAchatGetDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatPostDto;
import com.projet.equipement.dto.ligneAchat.LigneAchatUpdateDto;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.entity.LigneAchat;
import com.projet.equipement.entity.Produit;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , uses = {ProduitMapper.class, AchatMapper.class})
public interface LigneAchatMapper {

    @Mapping(source = "produit", target = "produitId", qualifiedByName = "mapProduitToId")
    @Mapping(source = "achat", target = "achatId", qualifiedByName = "mapAchatToId")
    LigneAchatGetDto toDto(LigneAchat ligneAchat);


    LigneAchat toEntity(LigneAchatPostDto ligneAchatPostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLigneAchatFromDto(LigneAchatUpdateDto ligneAchatUpdateDto, @MappingTarget LigneAchat ligneAchat);


    @Named("mapProduitToId")
    default Long mapProduitToId(Produit produit) {
        return produit.getId();
    }


    @Named("mapAchatToId")
    default Long mapAchatToId(Achat achat) {
        return achat.getId();
    }





}
