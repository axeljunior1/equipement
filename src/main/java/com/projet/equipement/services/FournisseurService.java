package com.projet.equipement.services;


import com.projet.equipement.entity.Fournisseur;
import com.projet.equipement.exceptions.EntityNotFoundException;
import com.projet.equipement.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FournisseurService {
    @Autowired
    private FournisseurRepository fournisseurRepository;

    public List<Fournisseur> findAll(){
        return fournisseurRepository.findAll();
    }
    public  Fournisseur findById(Long id){
        return  fournisseurRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fournisseur", id));

    }
    public Fournisseur save(Fournisseur fournisseur){
        return fournisseurRepository.save(fournisseur);
    }
    public void deleteById(Long id){
        fournisseurRepository.deleteById(id);
    }

}
