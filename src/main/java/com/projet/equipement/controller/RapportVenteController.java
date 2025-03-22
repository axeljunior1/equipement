package com.projet.equipement.controller;

import com.projet.equipement.dto.PeriodeDTO;
import com.projet.equipement.dto.rapport.vente.RapportVenteView;
import com.projet.equipement.services.RapportVenteService;
import com.projet.equipement.utils.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rapport-ventes")
public class RapportVenteController {


    private final RapportVenteService rapportVenteService;

    public RapportVenteController(RapportVenteService rapportVenteService) {
        this.rapportVenteService = rapportVenteService;
    }

    @PostMapping()
    public Page<RapportVenteView> rapportVenteDTOS(@RequestBody PeriodeDTO periodeDTO, Pageable pageable){
        System.out.println(periodeDTO);
        List<RapportVenteView> rapportVenteViews = rapportVenteService.listVueRapportVente(periodeDTO.getStart(), periodeDTO.getEnd());
        return PaginationUtil.toPage(rapportVenteViews, pageable);
    }

}
