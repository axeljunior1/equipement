package com.projet.equipement.services;

import com.projet.equipement.entity.EtatPaiement;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EtatPaiementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatPaiementService {

    private final EtatPaiementRepository etatPaiementRepository;

    public EtatPaiementService(EtatPaiementRepository etatPaiementRepository) {
        this.etatPaiementRepository = etatPaiementRepository;
    }

    public EtatPaiement findById(Long id) {
        return etatPaiementRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("EtatPaiement", id));
    }
    
    
    public EtatPaiement findByLibelle(String libelle) {
        return etatPaiementRepository.findByLibelle(libelle).orElseThrow(()-> new EntityNotFoundException("EtatPaiement", libelle));
    }
    
    public List<EtatPaiement> findAll() {
        return etatPaiementRepository.findAll();
    }
}
