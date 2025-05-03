package com.projet.equipement.mapper;

import com.projet.equipement.dto.etatPaiement.EtatPaiementGetDTO;
import com.projet.equipement.entity.EtatPaiement;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface EtatPaiementMapper {



    EtatPaiementGetDTO toDto(EtatPaiement etatPaiement);


}
