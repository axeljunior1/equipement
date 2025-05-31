package com.projet.equipement.mapper;

import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.EtatVente;
import com.projet.equipement.entity.Vente;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EmployeMapper.class, ClientMapper.class, LigneVenteMapper.class, PaiementMapper.class})
public interface VenteMapper {

    @Mapping(source = "etat", target = "etatId", qualifiedByName = "mapEtatToId")
    @Mapping(source = "employe", target = "employeId", qualifiedByName = "mapEmployeToId")
      VenteGetDto toDto(Vente vente);


      Vente toEntity(VentePostDto ventePostDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
      void updateDto(VenteUpdateDto venteUpdateDto, @MappingTarget Vente vente);


    @Named("mapEmployeToId")
    default Long mapEmployeToId(Employe employe) {
        return employe.getId();
    }

    @Named("mapEtatToId")
    default Long mapEtatToId(EtatVente etatVente) {
        return etatVente.getId();
    }


}