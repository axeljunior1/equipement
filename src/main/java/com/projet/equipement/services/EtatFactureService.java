package com.projet.equipement.services;

import com.projet.equipement.entity.EtatFacture;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EtatFactureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatFactureService {

    private final EtatFactureRepository etatFactureRepository;

    public EtatFactureService(EtatFactureRepository etatFactureRepository) {
        this.etatFactureRepository = etatFactureRepository;
    }

    public EtatFacture findById(Long id) {
        return etatFactureRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("EtatFacture", id));
    }
    
    
    public EtatFacture findByLibelle(String libelle) {
        return etatFactureRepository.findByLibelle(libelle).orElseThrow(()-> new EntityNotFoundException("EtatFacture", libelle));
    }
    
    public List<EtatFacture> findAll() {
        return etatFactureRepository.findAll();
    }
}
