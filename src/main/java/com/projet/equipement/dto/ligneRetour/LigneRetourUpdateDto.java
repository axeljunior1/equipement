package com.projet.equipement.dto.ligneRetour;

import com.projet.equipement.dto.ligneVente.LigneVenteGetDto;
import com.projet.equipement.dto.retour.RetourGetDto;
import jakarta.validation.constraints.NotNull;
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
