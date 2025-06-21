package com.projet.equipement.dto.retour;

import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.dto.vente.VenteLightDto;
import com.projet.equipement.entity.EtatRetour;
import com.projet.equipement.entity.TypeRetour;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RetourGetDto {

    private Long id ;

    private VenteLightDto vente;

    private TypeRetour type;

    private EtatRetour etat;

    private List<LigneRetourGetDto> ligneRetours;

    private LocalDateTime dateCreation;

}
