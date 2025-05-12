package com.projet.equipement.services;


import com.projet.equipement.dto.rapport.RapportVenteView;
import com.projet.equipement.repository.RapportVenteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RapportVenteService {


    private final RapportVenteRepository rapportVenteRepository;

    public RapportVenteService(RapportVenteRepository rapportVenteRepository) {
        this.rapportVenteRepository = rapportVenteRepository;
    }

    public List<RapportVenteView> listVueRapportVente(LocalDateTime start, LocalDateTime end) {
        return rapportVenteRepository.rapportVente(start, end);
    }

}
