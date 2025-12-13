package com.projet.equipement.mapper;

import com.projet.equipement.dto.etatPaiement.EtatPaiementGetDTO;
import com.projet.equipement.entity.EtatPaiement;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface EtatPaiementMapper {



    EtatPaiementGetDTO toDto(EtatPaiement etatPaiement);


}
