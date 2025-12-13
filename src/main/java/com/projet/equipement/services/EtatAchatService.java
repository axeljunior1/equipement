package com.projet.equipement.services;

import com.projet.equipement.entity.EtatAchat;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.EtatAchatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtatAchatService {

    private final EtatAchatRepository etatAchatRepository;

    public EtatAchatService(EtatAchatRepository etatAchatRepository) {
        this.etatAchatRepository = etatAchatRepository;
    }

    public EtatAchat findById(Long id) {
        return etatAchatRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("EtatAchat", id));
    }


    public EtatAchat findByLibelle(String libelle) {
        return etatAchatRepository.findByLibelle(libelle).orElseThrow(()-> new EntityNotFoundException("EtatAchat", libelle));
    }

    public List<EtatAchat> findAll() {
        return etatAchatRepository.findAll();
    }
}
