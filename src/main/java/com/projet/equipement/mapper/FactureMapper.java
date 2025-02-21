package com.projet.equipement.mapper;

import com.projet.equipement.dto.facture.FactureGetDTO;
import com.projet.equipement.dto.facture.FacturePostDTO;
import com.projet.equipement.dto.facture.FactureUpdateDTO;
import com.projet.equipement.entity.Facture;
import com.projet.equipement.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = VenteMapper.class) // Utilisation d'un mapper Vente si nécessaire
public interface FactureMapper {

    @Mapping(source = "vente", target = "venteId", qualifiedByName = "mapVenteToId") // Convertit la relation 'Vente' en son ID
    FactureGetDTO toDto(Facture facture);

    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente") // Convertit l'ID en entité 'Vente'
    Facture toEntity(FactureGetDTO factureGetDTO);

    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente") // Convertit l'ID en entité 'Vente' pour la création
    Facture toEntity(FacturePostDTO facturePostDTO);

    void updateDto(FactureUpdateDTO factureUpdateDTO, @MappingTarget Facture facture);

    @Named("mapVenteToId")
    default Long mapVenteToId(Vente vente) {
        return vente != null ? vente.getId() : null;
    }

    @Named("mapIdToVente")
    default Vente mapIdToVente(Long id) {
        Vente vente = new Vente();
        vente.setId(id);
        return vente;
    }
}
