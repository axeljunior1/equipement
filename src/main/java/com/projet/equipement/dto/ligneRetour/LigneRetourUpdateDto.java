package com.projet.equipement.dto.ligneRetour;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class LigneRetourUpdateDto {

    private Long retourId;

    private Long ligneVenteId;

    private Integer quantite;

    private LocalDateTime dateCreation ;

}
