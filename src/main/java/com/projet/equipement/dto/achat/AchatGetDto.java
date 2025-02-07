package com.projet.equipement.dto.achat;

import com.projet.equipement.entity.Achat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AchatGetDto {


    private Long id ;

    private Integer montantTotal;

    private Long employeId;

    private String  employeNom;

    private boolean  actif;


    private LocalDateTime dateCreation;





    public AchatGetDto(Achat achat) {
        this.id = achat.getId();
        this.employeId = achat.getEmploye().getId();
        this.montantTotal = achat.getMontantTotal();
        this.dateCreation = achat.getDateCreation();
        this.employeNom = achat.getEmploye().getNom();
        this.actif = achat.getActif();
    }
}
