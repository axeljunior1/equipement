package com.projet.equipement.services;

import com.projet.equipement.dto.avoir.AvoirPostDto;
import com.projet.equipement.entity.*;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.AvoirRepository;
import com.projet.equipement.repository.EtatAvoirRepository;
import com.projet.equipement.repository.RetourRepository;
import org.springframework.stereotype.Service;

@Service
public class AvoirService {


    private final AvoirRepository avoirRepository;
    private final ClientService clientService;
    private final VenteService venteService;
    private final EtatAvoirRepository etatAvoirRepository;
    private final RetourRepository retourRepository;

    public AvoirService(AvoirRepository avoirRepository, ClientService clientService, VenteService venteService, EtatAvoirRepository etatAvoirRepository, RetourRepository retourRepository) {
        this.avoirRepository = avoirRepository;
        this.clientService = clientService;
        this.venteService = venteService;
        this.etatAvoirRepository = etatAvoirRepository;
        this.retourRepository = retourRepository;
    }

    public void saveAvoir(AvoirPostDto avoirPostDto) {

        Avoir avoir = new Avoir();

        Client client = clientService.findById(avoirPostDto.getClientId());
        avoir.setClient(client);

        Retour r = retourRepository.findById(avoirPostDto.getRetourId()).orElseThrow(() -> new EntityNotFoundException("Retour", avoirPostDto.getRetourId()));
        avoir.setRetour(r);

        Vente vente = venteService.findById(avoirPostDto.getVenteOrigineId());
        avoir.setVenteOrigine(vente);
        avoir.setMontantInitial(avoirPostDto.getMontantInitial());


        EtatAvoir etat = etatAvoirRepository.findByLibelle("ACTIF").orElseThrow(() -> new EntityNotFoundException("etat_avoir", " ACTIF"));
        avoir.setEtat(etat);

        avoir.setCreatedAt(avoirPostDto.getCreatedAt());

        avoirRepository.save(avoir);

    }

    public Avoir findAvoirById(Long id) {
        return avoirRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Avoir", id));
    }

    public Avoir save(Avoir avoir) {
        return avoirRepository.save(avoir);
    }

}
