package com.projet.equipement.dto.ligneRetour;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class LigneRetourGetDto {

    private Long id ;

    private Long retourId;

    private LigneVenteGetDto ligneVente;

    private Integer quantite;

    private BigDecimal prix;

    private LocalDateTime dateCreation;

}
