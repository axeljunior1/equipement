package com.projet.equipement.mapper;

import com.projet.equipement.dto.vente.VentePostDto;
import com.projet.equipement.dto.vente.VenteUpdateDto;
import com.projet.equipement.entity.Client;
import com.projet.equipement.entity.Employe;
import com.projet.equipement.entity.Vente;
import com.projet.equipement.services.ClientService;
import com.projet.equipement.services.EmployeService;
import org.springframework.stereotype.Component;

@Component
public class VenteMapper {

    public void updateVenteFromDto(VenteUpdateDto venteUpdateDto, Vente vente, Client client, Employe employe) {
        if (venteUpdateDto.getMontantTotal() != null) vente.setMontantTotal(venteUpdateDto.getMontantTotal());
        if(venteUpdateDto.getDateDerniereMiseAjour() != null)vente.setDateDerniereMiseAJour(venteUpdateDto.getDateDerniereMiseAjour());
        if (client != null) vente.setClient(client);
        if (employe != null) vente.setEmploye(employe);
    }

    public Vente postVenteDto(VentePostDto ventePostDto, Client client, Employe employe) {
        Vente vente = new Vente();
        vente.setMontantTotal(ventePostDto.getMontantTotal());
        vente.setClient(client);
        vente.setEmploye(employe);
        vente.setActif(true);
        vente.setDateDerniereMiseAJour(ventePostDto.getDateDerniereMiseAjour());
        return vente;
    }
}
