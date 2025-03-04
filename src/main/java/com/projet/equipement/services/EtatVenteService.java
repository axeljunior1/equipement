package com.projet.equipement.services;

import com.projet.equipement.entity.EtatVente;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EtatVenteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatVenteService {

    private final EtatVenteRepository etatVenteRepository;

    public EtatVenteService(EtatVenteRepository etatVenteRepository) {
        this.etatVenteRepository = etatVenteRepository;
    }

    public EtatVente findById(Long id) {
        return etatVenteRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("EtatVente", id));
    }


    public EtatVente findByLibelle(String libelle) {
        return etatVenteRepository.findByLibelle(libelle).orElseThrow(()-> new EntityNotFoundException("EtatVente", libelle));
    }

    public List<EtatVente> findAll() {
        return etatVenteRepository.findAll();
    }
}
