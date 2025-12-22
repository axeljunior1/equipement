package com.projet.equipement.controller;

import com.projet.equipement.entity.EtatRetour;
import com.projet.equipement.entity.EtatVente;
import com.projet.equipement.services.EtatRetourService;
import com.projet.equipement.services.EtatVenteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/etat-vente")
public class EtatVenteController {


    private final EtatVenteService etatVenteService;

    public EtatVenteController(EtatVenteService etatVenteService) {
        this.etatVenteService = etatVenteService;
    }

    @GetMapping
    public Page<EtatVente> findAll(Pageable pageable) {
        return etatVenteService.findAll(pageable);
    }
}
