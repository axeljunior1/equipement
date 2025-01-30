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

    public void updateVenteFromDto(VenteUpdateDto venteUpdateDto, Vente vente, ClientService clientService, EmployeService employeService) {
        if (venteUpdateDto.getMontantTotal() != null) vente.setMontantTotal(venteUpdateDto.getMontantTotal());
        if (venteUpdateDto.getClientId() != null) {
            Client client = clientService.findById(Long.valueOf(venteUpdateDto.getClientId())) ;
            vente.setClient(client);
        }
        if (venteUpdateDto.getEmployeId() != null) {
            Employe employe = employeService.findById(Long.valueOf(venteUpdateDto.getEmployeId())) ;
            vente.setEmploye(employe);
        }
        if(venteUpdateDto.getDateDerniereMiseAjour() != null)vente.setDateDerniereMiseAJour(venteUpdateDto.getDateDerniereMiseAjour());
    }

    public Vente postVenteDto(VentePostDto ventePostDto, ClientService clientService, EmployeService employeService) {
        Vente vente = new Vente();
        vente.setMontantTotal(ventePostDto.getMontantTotal());
        vente.setClient(clientService.findById(ventePostDto.getClientId()));
        vente.setEmploye(employeService.findById(ventePostDto.getEmployeId()));
        vente.setDateDerniereMiseAJour(ventePostDto.getDateDerniereMiseAjour());
        return vente;
    }
}
