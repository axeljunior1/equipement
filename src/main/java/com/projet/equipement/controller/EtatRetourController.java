package com.projet.equipement.controller;

import com.projet.equipement.entity.EtatRetour;
import com.projet.equipement.services.EtatRetourService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/etat-retours")
public class EtatRetourController {

    private final EtatRetourService etatRetourService;

    public EtatRetourController(EtatRetourService etatRetourService) {
        this.etatRetourService = etatRetourService;
    }

    @GetMapping
    public Page<EtatRetour> findAll(Pageable pageable) {
        return etatRetourService.findAll(pageable);
    }
}
