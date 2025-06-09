package com.projet.equipement.mapper;

import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.dto.paiement.PaiementPostDTO;
import com.projet.equipement.dto.paiement.PaiementUpdateDTO;
import com.projet.equipement.entity.EtatPaiement;
import com.projet.equipement.entity.Paiement;
import com.projet.equipement.entity.Vente;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {VenteMapper.class, EtatPaiementMapper.class, EtatPaiement.class})
public interface PaiementMapper {

    @Mapping(source = "etat", target = "etatId", qualifiedByName = "mapEtatToId") // Convertit la relation 'Vente' en son ID
    @Mapping(source = "vente", target = "venteId", qualifiedByName = "mapVenteToId") // Convertit la relation 'Vente' en son ID
    PaiementGetDTO toDto(Paiement paiement);

    Paiement toEntity(PaiementPostDTO paiementPostDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDto(PaiementUpdateDTO paiementUpdateDTO, @MappingTarget Paiement paiement);

    @Named("mapEtatToId")
    default Long mapEtatToId(EtatPaiement etatPaiement) {
        return etatPaiement != null ? etatPaiement.getId() : null;
    }


    @Named("mapVenteToId")
    default Long mapVenteToId(Vente vente) {
        return vente != null ? vente.getId() : null;
    }


}
