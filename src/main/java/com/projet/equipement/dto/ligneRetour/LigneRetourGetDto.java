package com.projet.equipement.dto.ligneRetour;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.retour.RetourGetDto;
import com.projet.equipement.dto.vente.VenteGetDto;
import com.projet.equipement.entity.EtatRetour;
import com.projet.equipement.entity.TypeRetour;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LigneRetourGetDto {

    private Long id ;

    private RetourGetDto retour;

    private LigneVenteGetDto ligneVente;

    private Integer quantite;


    private LocalDateTime dateCreation;

}
