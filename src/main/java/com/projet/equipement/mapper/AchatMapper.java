package com.projet.equipement.mapper;

import com.projet.equipement.dto.achat.AchatPostDto;
import com.projet.equipement.dto.achat.AchatUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Achat;
import com.projet.equipement.services.ClientService;
import com.projet.equipement.services.EmployeService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AchatMapper {

    public void updateAchatFromDto(AchatUpdateDto achatUpdateDto, Achat achat, Employe employe) {
        if (achatUpdateDto.getMontantTotal() != null) achat.setMontantTotal(achatUpdateDto.getMontantTotal());
        if (achatUpdateDto.getActif() != null) achat.setActif(achatUpdateDto.getActif());
        if (employe != null) {
            achat.setEmploye(employe);
        }
    }

    public Achat postAchatDto(AchatPostDto achatPostDto, Employe employe) {
        Achat achat = Achat.builder()
                .montantTotal(achatPostDto.getMontantTotal())
                .employe(employe)
                .actif(true)
                .dateCreation(LocalDateTime.now())
                .build();
        return achat;
    }
}
