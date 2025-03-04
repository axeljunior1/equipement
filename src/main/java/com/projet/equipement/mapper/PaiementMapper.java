package com.projet.equipement.mapper;

import com.projet.equipement.dto.paiement.PaiementGetDTO;
import com.projet.equipement.dto.paiement.PaiementPostDTO;
import com.projet.equipement.dto.paiement.PaiementUpdateDTO;
import com.projet.equipement.entity.EtatPaiement;
import com.projet.equipement.entity.Paiement;
import com.projet.equipement.entity.Facture;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    @Mapping(source = "etat", target = "etatId", qualifiedByName = "mapEtatToId") // Convertit la relation 'Facture' en son ID
    @Mapping(source = "facture", target = "factureId", qualifiedByName = "mapFactureToId") // Convertit la relation 'Facture' en son ID
    PaiementGetDTO toDto(Paiement paiement);

    @Mapping(source = "factureId", target = "facture", qualifiedByName = "mapIdToFacture") // Convertit l'ID en entité 'Facture'
    @Mapping(source = "etatId", target = "etat", qualifiedByName = "mapIdToEtat") // Convertit l'ID en entité 'Facture'
    Paiement toEntity(PaiementGetDTO paiementGetDTO);

    @Mapping(source = "etatId", target = "etat", qualifiedByName = "mapIdToEtat") // Convertit l'ID en entité 'Facture'
    @Mapping(source = "factureId", target = "facture", qualifiedByName = "mapIdToFacture") // Convertit l'ID en entité 'Facture' pour la création
    Paiement toEntity(PaiementPostDTO paiementPostDTO);

    @Mapping(source = "etatId", target = "etat", qualifiedByName = "mapIdToEtat") // Convertit l'ID en entité 'Facture'
    void updateDto(PaiementUpdateDTO paiementUpdateDTO, @MappingTarget Paiement paiement);

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
    @Named("mapFactureToId")
    default Long mapFactureToId(Facture facture) {
        return facture != null ? facture.getIdFacture() : null;
    }

    @Named("mapIdToFacture")
    default Facture mapIdToFacture(Long id) {
        Facture facture = new Facture();
        facture.setIdFacture(id);
        return facture;
    }
}
