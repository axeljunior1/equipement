package com.projet.equipement.mapper;

import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.dto.paiement.PaiementPostDTO;
import com.projet.equipement.dto.paiement.PaiementUpdateDTO;
import com.projet.equipement.entity.EtatPaiement;
import com.projet.equipement.entity.Paiements;
import com.projet.equipement.entity.Vente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {VenteMapper.class, EtatPaiementMapper.class, EtatPaiement.class})
public interface PaiementMapper {

    @Mapping(source = "etat", target = "etatId", qualifiedByName = "mapEtatToId") // Convertit la relation 'Vente' en son ID
    @Mapping(source = "vente", target = "venteId", qualifiedByName = "mapVenteToId") // Convertit la relation 'Vente' en son ID
    PaiementGetDTO toDto(Paiements paiement);

    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente") // Convertit l'ID en entité 'Vente'
    @Mapping(source = "etatId", target = "etat", qualifiedByName = "mapIdToEtat") // Convertit l'ID en entité 'Vente'
    Paiements toEntity(PaiementGetDTO paiementGetDTO);

    @Mapping(source = "etatId", target = "etat", qualifiedByName = "mapIdToEtat") // Convertit l'ID en entité 'Vente'
    @Mapping(source = "venteId", target = "vente", qualifiedByName = "mapIdToVente") // Convertit l'ID en entité 'Vente' pour la création
    Paiements toEntity(PaiementPostDTO paiementPostDTO);

    @Mapping(source = "etatId", target = "etat", qualifiedByName = "mapIdToEtat") // Convertit l'ID en entité 'Vente'
    void updateDto(PaiementUpdateDTO paiementUpdateDTO, @MappingTarget Paiements paiement);

    @Named("mapEtatToId")
    default Long mapEtatToId(EtatPaiement etatPaiement) {
        return etatPaiement != null ? etatPaiement.getId() : null;
    }

    @Named("mapIdToEtat")
    default EtatPaiement mapIdToEtat(Long id) {
        EtatPaiement etatPaiement = new EtatPaiement();
        etatPaiement.setId(id);
        return etatPaiement;
    }
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
