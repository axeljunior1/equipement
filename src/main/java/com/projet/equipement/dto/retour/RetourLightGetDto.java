package com.projet.equipement.dto.retour;

import com.projet.equipement.dto.ligneRetour.LigneRetourGetDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RetourLightGetDto {

    private Long id ;

    private Long venteId;

    private Long typeId;

    private String typeLibelle;

    private Long etatId;

    private String etatLibelle;

    private Long clientId;

    private String clientNom;

    private Long employeId;

    private String employeNom;

    private List<LigneRetourGetDto> ligneRetours;

    private LocalDateTime dateCreation;

}
