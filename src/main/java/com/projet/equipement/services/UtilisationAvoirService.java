package com.projet.equipement.services;


import com.projet.equipement.dto.utilisationAvoir.UtilisationAvoirPostDto;
import com.projet.equipement.entity.UtilisationAvoir;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.AvoirRepository;
import com.projet.equipement.repository.UtilisationAvoirRepository;
import com.projet.equipement.repository.VenteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UtilisationAvoirService {


    private final UtilisationAvoirRepository utilisationAvoirRepository;
    private final VenteRepository venteRepository;
    private final AvoirRepository avoirRepository;

    public UtilisationAvoirService(UtilisationAvoirRepository utilisationAvoirRepository, VenteRepository venteRepository, AvoirRepository avoirRepository) {
        this.utilisationAvoirRepository = utilisationAvoirRepository;
        this.venteRepository = venteRepository;
        this.avoirRepository = avoirRepository;
    }

    public UtilisationAvoir findById(Long id) {
        return utilisationAvoirRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("UtilisationAvoir", id)
                );
    }

    public void save(UtilisationAvoirPostDto utilisationAvoirPostDto) {

        Long venteId = utilisationAvoirPostDto.getVenteId();

        Long avoirId = utilisationAvoirPostDto.getAvoirId();

        UtilisationAvoir utilisationAvoir = UtilisationAvoir.builder()
                .vente(venteRepository.findById(venteId).orElseThrow(() -> new EntityNotFoundException("VENTE", venteId)))
                .avoir(avoirRepository.findById(avoirId).orElseThrow(
                        () -> new EntityNotFoundException("Avoir", avoirId)
                ))
                .createdAt(LocalDateTime.now())
                .montantUtilise(utilisationAvoirPostDto.getMontantUtilise())
                .build();

        utilisationAvoirRepository.save(utilisationAvoir);
    }


    public List<UtilisationAvoir> findByAvoirId(Long idAvoir) {

        return utilisationAvoirRepository.findByAvoir_Id(idAvoir);
    }
}
