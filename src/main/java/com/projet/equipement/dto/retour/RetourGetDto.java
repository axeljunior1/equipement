package com.projet.equipement.dto.retour;

import com.projet.equipement.dto.etatDto.EtatRetourGetDto;
import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.typeretour.TypeRetourGetDto;
import com.projet.equipement.dto.vente.VenteLightDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RetourGetDto {

    private Long id ;

    private VenteLightDto vente;

    private TypeRetourGetDto typeRetour;

    private EtatRetourGetDto etat;

    private List<LigneRetourGetDto> ligneRetours;

    private LocalDateTime dateCreation;

}
